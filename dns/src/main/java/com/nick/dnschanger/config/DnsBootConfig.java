package com.nick.dnschanger.config;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.Network;
import android.widget.Toast;

import androidx.annotation.Keep;

import com.nick.dnschanger.BuildConfig;
import com.nick.dnschanger.LogFactory;
import com.nick.dnschanger.R;
import com.nick.dnschanger.config.bean.DNSProperties;
import com.nick.dnschanger.database.DatabaseHelper;
import com.nick.dnschanger.database.entities.IPPortPair;
import com.nick.dnschanger.services.DNSVpnService;
import com.nick.dnschanger.util.DataSavingSentryEventProcessor;
import com.nick.dnschanger.util.Preferences;
import com.nick.dnschanger.util.PreferencesAccessor;
import com.nick.dnschanger.util.Util;
import com.frostnerd.general.Utils;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import io.sentry.Integration;
import io.sentry.Sentry;
import io.sentry.android.core.PhoneStateBreadcrumbsIntegration;
import io.sentry.android.core.SentryAndroid;
import io.sentry.android.core.SentryAndroidOptions;
import io.sentry.android.core.SystemEventsBreadcrumbsIntegration;
import io.sentry.android.core.TempSensorBreadcrumbsIntegration;
import io.sentry.protocol.User;

public class DnsBootConfig {

    private static final String LOG_TAG = "[DNSCHANGER-APPLICATION]";
    private final Thread.UncaughtExceptionHandler customHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            maybeReportSentry(e);
            try {
                Thread.sleep(750);
            } catch (InterruptedException ignored) {}
            if (showErrorDialog(e)) {
                System.exit(2);
            }
            if (defaultHandler != null) defaultHandler.uncaughtException(t, e);
        }
    };
    private Thread.UncaughtExceptionHandler defaultHandler;
    @Keep
    private DatabaseHelper helper;
    private Preferences mPreferences;
    private Boolean sentryInitialized = false, sentryInitializing = false, sentryDisabled = false;
    // Sometimes you just have to say f it. Don't ask me why, but Context is sometimes null in MainFragment
    public static Context mContext;

    private boolean showErrorDialog(Throwable exception) {
        if(exception instanceof SQLiteException || (exception.getCause() != null && exception instanceof SQLiteException))return true;
        return exception.getMessage() != null && exception.getMessage().toLowerCase().contains("cannot create interface");
    }

    private DnsBootConfig(){

    }

    public static DnsBootConfig getInstance(){
        return Holder.DNS_BOOT_CONFIG;
    }

    public void init(Context context) {
        mContext = context;
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(customHandler);
        LogFactory.writeMessage(context, LOG_TAG, "Application created");
        helper = DatabaseHelper.getInstance(context);
        mPreferences = Preferences.getInstance(context);
        setupSentry();
        applyCurrentNetWork();
        Preferences.getInstance(context).put("advanced_settings", true, true);
        Preferences.getInstance(context).put("dns_over_tcp", true, true);
        Preferences.getInstance(context).put("rules_activated", true, true);
        Preferences.getInstance(context).put("setting_ipv4_enabled", true, true);
        Preferences.getInstance(context).put("setting_show_notification", true, true);
        Preferences.getInstance(context).put("automation_priv_mode_set", true, true);
        Preferences.getInstance(context).put("automation_priv_mode", true, true);
        Preferences.getInstance(context).put("rated", true, true);
        Preferences.getInstance(context).put("nebulo_shown", true, true);
        Preferences.getInstance(context).put("show_used_dns", true, true);
    }

    public void maybeReportSentry(Throwable ex) {
        if(sentryInitialized && !sentryDisabled) {
            Sentry.captureException(ex);
        }
    }

    // Creates the SentryClient
    // Absolutely no identifiable data is transmitted (Thus it is not subject to GDPR)
    // The Sentry instance does not store IP addresses
    // Absolutely no tracking is possible.
    // Can be turned off in the settings.
    public void setupSentry() {
        if(!sentryInitialized && !sentryInitializing && BuildConfig.SENTRY_ENABLED && !BuildConfig.SENTRY_DSN.equals("dummy")) {
            sentryInitializing = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        boolean enabled = !mPreferences.getBoolean("disable_crash_reporting", false);
                        if(enabled) {
                            String hostname = InetAddress.getLocalHost().getHostName();
                            if(hostname.toLowerCase().contains("mars-sandbox")){
                                sentryDisabled = true;
                                return;
                            }
                            SentryAndroid.init(mContext, new Sentry.OptionsConfiguration<SentryAndroidOptions>() {
                                @Override
                                public void configure(SentryAndroidOptions options) {
                                    options.setDsn(BuildConfig.SENTRY_DSN);
                                    setupSentryForDatasaving(options);
                                }
                            });
                            User user = new User();
                            user.setUsername("anon-" + BuildConfig.VERSION_CODE);
                            Sentry.setUser(user);
                            Sentry.setTag("dist", BuildConfig.VERSION_CODE + "");
                            Sentry.setTag(
                                    "app.installer_package",
                                    mContext.getPackageManager().getInstallerPackageName(mContext.getPackageName())
                            );
                            Sentry.setTag("richdata", "false");
                            sentryInitialized = true;
                        }
                    } catch (Throwable ignored) {

                    } finally {
                        sentryInitializing = false;
                    }
                }
            }).start();
        }
    }

    private void setupSentryForDatasaving(SentryAndroidOptions options) {
        List<Integration> toBeRemoved = new ArrayList<>();
        for (Integration integration : options.getIntegrations()) {
            if (integration instanceof PhoneStateBreadcrumbsIntegration ||
                    integration instanceof SystemEventsBreadcrumbsIntegration ||
                    integration instanceof TempSensorBreadcrumbsIntegration
            ) toBeRemoved.add(integration);
        }
        for (Integration integration : toBeRemoved) {
            options.getIntegrations().remove(integration);
        }
        options.addEventProcessor(new DataSavingSentryEventProcessor());
    }

    public void tearDownSentry() {
        Sentry.close();
        sentryInitialized = false;
    }

    public Thread.UncaughtExceptionHandler getExceptionHandler() {
        return customHandler;
    }

    /**
     * 使用当前网络IP
     */
    private void applyCurrentNetWork(){
        List<DNSProperties> dnsProperties = new ArrayList<>();
        ConnectivityManager mgr = Utils.requireNonNull((ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE));
        boolean vpnRunning = Util.isServiceThreadRunning();
        for(Network ntw: mgr.getAllNetworks()){
            DNSProperties dnsProperty = new DNSProperties(mgr.getLinkProperties(ntw));
            if(dnsProperty.getIpv4Servers().size() == 0 && dnsProperty.getIpv6Servers().size() == 0)continue;
            if(!vpnRunning || !dnsProperty.getNetworkName().equals("tun0"))dnsProperties.add(dnsProperty);
        }
        if (dnsProperties.size()==0){
            Toast.makeText(mContext, "DNSConfig Error please check your netWork And restart", Toast.LENGTH_LONG).show();
            return;
        }
        setDNSServersOf(dnsProperties.get(0));
    }

    private void setDNSServersOf(DNSProperties properties){
        boolean ipv4Enabled = PreferencesAccessor.isIPv4Enabled(mContext),
                ipv6Enabled = PreferencesAccessor.isIPv6Enabled(mContext);
        if(ipv6Enabled && properties.getIpv6Servers().size() != 0){
            PreferencesAccessor.Type.DNS1_V6.saveDNSPair(mContext, properties.getIpv6Servers().get(0));
            if(properties.getIpv6Servers().size() >= 2){
                PreferencesAccessor.Type.DNS2_V6.saveDNSPair(mContext, properties.getIpv6Servers().get(1));
            }else {
                PreferencesAccessor.Type.DNS2_V6.saveDNSPair(mContext, IPPortPair.getEmptyPair());
            }
        }else if(ipv6Enabled) {
            PreferencesAccessor.Type.DNS2_V6.saveDNSPair(mContext, IPPortPair.getEmptyPair());
        }

        if(ipv4Enabled && properties.getIpv4Servers().size() != 0){
            PreferencesAccessor.Type.DNS1.saveDNSPair(mContext, properties.getIpv4Servers().get(0));
            if(properties.getIpv4Servers().size() >= 2){
                PreferencesAccessor.Type.DNS2.saveDNSPair(mContext, properties.getIpv4Servers().get(1));
            }else {
                PreferencesAccessor.Type.DNS2.saveDNSPair(mContext, IPPortPair.getEmptyPair());
            }
        }else if(ipv4Enabled) {
            PreferencesAccessor.Type.DNS2.saveDNSPair(mContext, IPPortPair.getEmptyPair());
        }
        if(Util.isServiceRunning(mContext)){
            mContext.startService(DNSVpnService.getUpdateServersIntent(mContext, true, false));
        }
    }


    private static class Holder{
        private static final DnsBootConfig DNS_BOOT_CONFIG = new DnsBootConfig();
    }

}
