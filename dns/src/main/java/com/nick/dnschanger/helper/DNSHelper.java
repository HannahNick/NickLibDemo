package com.nick.dnschanger.helper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.VpnService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.frostnerd.design.dialogs.LoadingDialog;
import com.nick.dnschanger.R;
import com.nick.dnschanger.activities.MainActivity;
import com.nick.dnschanger.config.DnsBootConfig;
import com.nick.dnschanger.database.DatabaseHelper;
import com.nick.dnschanger.database.entities.DNSRule;
import com.nick.dnschanger.database.entities.IPPortPair;
import com.nick.dnschanger.services.DNSVpnService;
import com.nick.dnschanger.util.DNSQueryUtil;
import com.nick.dnschanger.util.PreferencesAccessor;
import com.nick.dnschanger.util.ThemeHandler;
import com.nick.dnschanger.util.Util;

import org.minidns.record.Data;
import org.minidns.record.Record;

import java.util.ArrayList;
import java.util.List;

public class DNSHelper {

    private boolean vpnRunning = false;

    private static final int REQUEST_CODE = 0x09;

    private DNSHelper(){

    }

    public static DNSHelper getInstance(){
        return Holder.DNS_HELPER;
    }

    public boolean toggleVpn(Activity context){
        if (vpnRunning){
            stopVpn();
        }else {
            Intent i;
            try {
                i = VpnService.prepare(context);
            } catch (NullPointerException ex) {
                i = null; // I have no idea why this sometimes occurs.
            }
            if (i != null){
                context.startActivityForResult(i,REQUEST_CODE);
            }else {
                startVpn(context);
            }
        }
        return vpnRunning;
    }

    private void startVpn(final Context ctx) {
        if(PreferencesAccessor.checkConnectivityOnStart(ctx)){
            final LoadingDialog dialog = new LoadingDialog(ctx, R.string.checking_connectivity, R.string.dialog_connectivity_description);
            dialog.show();
            checkDNSReachability(new DNSReachabilityCallback() {
                @Override
                public void checkFinished(@NonNull List<IPPortPair> unreachable, @NonNull List<IPPortPair> reachable) {
                    dialog.dismiss();
                    if(unreachable.size() == 0){
                        ((MainActivity)ctx).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                start();
                            }
                        });
                    }else{
                        String _text = ctx.getString(R.string.no_connectivity_warning_text);
                        StringBuilder builder = new StringBuilder();
                        _text = _text.replace("[x]", unreachable.size() + reachable.size() + "");
                        _text = _text.replace("[y]", unreachable.size() + "");
                        boolean customPorts = PreferencesAccessor.areCustomPortsEnabled(ctx);
                        for(IPPortPair p: unreachable) {
                            if(p == null)continue;
                            builder.append("- ").append(p.formatForTextfield(customPorts)).append("\n");
                        }
                        _text = _text.replace("[servers]", builder.toString());
                        final String text = _text;
                        ((MainActivity)ctx).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialog.Builder(ctx, ThemeHandler.getDialogTheme(ctx))
                                        .setTitle(R.string.warning).setCancelable(true).setPositiveButton(R.string.start, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                start();
                                            }
                                        }).setNegativeButton(R.string.cancel, null).setMessage(text).show();
                            }
                        });
                    }
                }

                private void start(){
                    Intent i = DNSVpnService.getStartVPNIntent(ctx);
                    Util.startService(ctx, i);
                    vpnRunning = true;
                }
            });
        }else{
            Intent i = DNSVpnService.getStartVPNIntent(ctx);
            Util.startService(ctx, i);
            vpnRunning = true;
        }
    }

    public void stopVpn() {
        if (vpnRunning){
            Intent i = DNSVpnService.getDestroyIntent(DnsBootConfig.mContext);
            DnsBootConfig.mContext.startService(i);
            vpnRunning = false;
        }
    }

    public void updateVpn(){
        if (vpnRunning){
            DnsBootConfig.mContext.startService(DNSVpnService.getUpdateServersIntent(DnsBootConfig.mContext, true, false));
        }
    }

    public void handleCallBack(int requestCode, int resultCode){
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (!vpnRunning){
                startVpn(DnsBootConfig.mContext);
            }else{
                stopVpn();
            }
        }
    }

    public void checkDNSReachability(final DNSReachabilityCallback callback){
        List<IPPortPair> servers = PreferencesAccessor.getAllDNSPairs(DnsBootConfig.mContext, true);
        callback.setServers(servers.size());
        for(final IPPortPair pair: servers){
            DNSQueryUtil.runAsyncDNSQuery(pair, "frostnerd.com", PreferencesAccessor.sendDNSOverTCP(DnsBootConfig.mContext), Record.TYPE.A,
                    Record.CLASS.IN, new Util.DNSQueryResultListener() {
                        @Override
                        public void onSuccess(List<Record<? extends Data>> response) {
                            callback.checkProgress(pair, true);
                        }

                        @Override
                        public void onError(@Nullable Exception e) {
                            callback.checkProgress(pair, false);
                        }
                    }, 1);
        }
    }


    public void importHost(String host,String target){
        DNSRule dnsRule = DatabaseHelper.getInstance(DnsBootConfig.mContext).getDNSRule(host, false);
        if (dnsRule==null){
            DatabaseHelper.getInstance(DnsBootConfig.mContext).createDNSRule(host, target, false, false);
        }else {
            DatabaseHelper.getInstance(DnsBootConfig.mContext).editDNSRule(host,false,target);
        }

    }

    public void clearHostCache(){
        DatabaseHelper.getInstance(DnsBootConfig.mContext).deleteAll(DNSRule.class);
    }


    public static abstract class DNSReachabilityCallback{
        @NonNull private final List<IPPortPair> unreachable = new ArrayList<>();
        @NonNull private final List<IPPortPair> reachable = new ArrayList<>();
        private int servers;

        public abstract void checkFinished(@NonNull List<IPPortPair> unreachable, @NonNull List<IPPortPair> reachable);

        public final void checkProgress(@NonNull IPPortPair server, boolean reachable){
            if(server == null || server.isEmpty())return;
            if(!reachable)unreachable.add(server);
            else this.reachable.add(server);
            if(this.unreachable.size() + this.reachable.size() >= servers)checkFinished(this.unreachable, this.reachable);
        }

        void setServers(int servers){
            this.servers = servers;
        }
    }

    private static class Holder{
        private static final DNSHelper DNS_HELPER = new DNSHelper();
    }
}
