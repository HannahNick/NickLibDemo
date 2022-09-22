package com.nick.nicklibdemo.util;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;


import com.nick.nicklib.constants.Constants;

import java.io.File;
import java.util.List;

/**
 * Created by Nick on 2017/9/8.
 */

public class ApkUtil {
    /**
     * 安装apk文件的方法
     * @param context 上下文
     * @param file apk文件所在的路径和文件名
     */
    public static void installApk(Context context, File file) {
        if (file==null){
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        String type = "application/vnd.android.package-archive";
        intent.setDataAndType(Uri.fromFile(file), type);
        context.startActivity(intent);
    }
    /**
     * 获取版本号
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本名
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "1.0.0";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 机型
     *
     * @return
     */
    public static String getModel() {
        return Build.MODEL;
    }


    /**
     * 获取指定包名的应用程序是否在运行(无论前台还是后台)
     *
     * @return
     */
    private static boolean getCurrentTask(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> appProcessInfos = activityManager.getRunningTasks(50);
        for (RunningTaskInfo process : appProcessInfos) {

            if (process.baseActivity.getPackageName().equals(context.getPackageName())
                    || process.topActivity.getPackageName().equals(context.getPackageName())) {

                return true;
            }
        }
        return false;
    }

    /**
     * 查看是否有安装权限，适配8.0
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean checkHasInstallPermission(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean hasInstall = activity.getPackageManager().canRequestPackageInstalls();
            return hasInstall;
        }
        return true;
    }

    /**
     * 申请安装应用权限
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void startInstallPermissionSettingActivity(Activity activity) {
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:"+activity.getPackageName()));
        activity.startActivityForResult(intent, Constants.GET_UNKNOWN_APP_SOURCES_REQUEST_CODE);
    }
}
