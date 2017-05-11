﻿
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import io.innofang.autosms.utils.event.PermissionListener;

/**
 * Author: Inno Fang
 * Description: Request Runtime Permission
 */


public class RequestPermissions {

    public static final int PERMISSION_REQUEST_CODE = 1 << 5;

    private static PermissionListener mListener;

    public static void requestRuntimePermission(String[] permissions,
                                                PermissionListener listener) {
        Activity topActivity = ActivityCollector.getTopActivity();
        if (null == topActivity) {
            return;
        }
        mListener = listener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(topActivity, permission)
                    != PackageManager.PERMISSION_GRANTED) {

                permissionList.add(permission);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(topActivity,
                    permissionList.toArray(new String[permissionList.size()]),
                    PERMISSION_REQUEST_CODE);
        } else {
            mListener.onGranted();
        }
    }


    /**
     * 对运行时权限请求结果进行处理
     *
     * @param requestCode  请求码
     * @param permissions  申请的运行时权限
     * @param grantResults 运行时权限申请结果
     */
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        }
                    }
                    if (deniedPermissions.isEmpty()) {
                        mListener.onGranted();
                    } else {
                        mListener.onDenied(deniedPermissions);
                    }
                }
                break;
        }
    }

}
