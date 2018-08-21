package com.sunyuanming.permissiontest.permission.listener;


import android.support.v4.app.ActivityCompat;

import java.util.List;

/**
 * @author sunyuanming
 * 权限回调接口

 */
public interface PermissionResultListener extends ActivityCompat.OnRequestPermissionsResultCallback{
    void onPermissionsGranted(int requestCode, List<String> perms);

    void onPermissionsDenied(int requestCode, List<String> perms);

    void onPermissionsAllGranted();

    void onCloseActivity();
}