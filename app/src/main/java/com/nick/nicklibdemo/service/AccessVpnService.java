package com.nick.nicklibdemo.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import com.blankj.utilcode.util.LogUtils;

import java.util.List;

/**
 * 目前测试华为mate10 索尼都拿不到window里的view，所以目前没啥用这个类
 */
public class AccessVpnService extends AccessibilityService {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.i("无障碍服务启动了");
    }

    @Override
    protected void onServiceConnected() {
        AccessibilityServiceInfo info = getServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.notificationTimeout = 100;
        info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS |
                AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS;
        String[] packageNames = info.packageNames;
        setServiceInfo(info);
        super.onServiceConnected();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        if (AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED == eventType && "com.android.vpndialogs".equals(event.getPackageName())){
            List<CharSequence> text = event.getText();
            CharSequence confirmButtonName = text.get(text.size() - 1);
            List<AccessibilityWindowInfo> windows = getWindows();
            for (AccessibilityWindowInfo window : windows) {
                LogUtils.i(window);
            }
            AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();

            if (rootInActiveWindow == null || !"com.android.vpndialogs".equals(rootInActiveWindow.getPackageName().toString())) {
                return;
            }
            List<AccessibilityNodeInfo> list = rootInActiveWindow.findAccessibilityNodeInfosByText("确定");
            if(list==null||list.size()==0){
                LogUtils.i("找不到确定服务");
            }else{
                list.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }


    }

    @Override
    public void onInterrupt() {

    }
}
