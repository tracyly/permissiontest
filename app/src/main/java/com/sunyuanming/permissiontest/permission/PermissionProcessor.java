package com.sunyuanming.permissiontest.permission;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.support.annotation.NonNull;
import com.sunyuanming.permissiontest.permission.listener.PermissionResultListener;
import com.sunyuanming.permissiontest.util.Ln;

import java.util.List;

/**
 * @author sunyuanming
 * 权限处理器
 */

public class PermissionProcessor {

    public static final int PERMISSION_REQUEST_CODE = 3000;

    private Object activityOrFragment = null;
    private PermissionCallback permissionCallback = null;
    private Activity activity = null;

    public PermissionProcessor() {
    }

    /**
     * 获得对应的activity
     *
     * @param activityOrFragment
     */
    private void getActivity(Object activityOrFragment) {
        if (activityOrFragment instanceof Activity) {
            activity = (Activity) activityOrFragment;
        } else if (activityOrFragment instanceof Fragment) {
            activity = ((Fragment) activityOrFragment).getActivity();
        } else if (activityOrFragment instanceof android.support.v4.app.Fragment) {
            activity = ((android.support.v4.app.Fragment) activityOrFragment).getActivity();
        } else {
            throw new RuntimeException("activity or fragment should be set");
        }
    }


    /**
     * 销毁这个处理器
     */
    public void destroy() {
        activity = null;
        permissionCallback = null;
        activityOrFragment = null;
    }

    /**
     * 检查这个这个处理器有没有被销毁
     *
     * @return
     */
    private boolean checkDestroy() {
        return activityOrFragment == null || (activity != null && activity.isFinishing());
    }


    private void checkAndRequestPermission() {
    }
    /**
     * 检查和请求蓝牙权限
     */
    public void checkAndRequestBlueToothPermission(Object activityOrFragment, PermissionCallback permissionCallback) {
        checkAndRequestPermission(activityOrFragment, permissionCallback, "请给予相机权限，才能正常使用APP功能", Manifest.permission.BLUETOOTH);
    }
    /**
     * 检查和请求相机权限
     */
    public void checkAndRequestCameraPermission(Object activityOrFragment, PermissionCallback permissionCallback) {
        checkAndRequestPermission(activityOrFragment, permissionCallback, "请给予相机权限，才能正常使用APP功能", Manifest.permission.CAMERA);
    }

    /**
     * 检查和请求存储权限
     */
    public void checkAndRequestStoragePermission(Object activityOrFragment, PermissionCallback permissionCallback) {
        checkAndRequestPermission(activityOrFragment, permissionCallback, "请给予存储权限，才能正常使用APP功能", Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 检查和请求读取通讯录权限
     */
    public void checkAndRequestReadContactsPermission(Object activityOrFragment, PermissionCallback permissionCallback) {
        checkAndRequestPermission(activityOrFragment, permissionCallback, "没有读取通讯录权限", Manifest.permission.READ_CONTACTS);
    }

    /**
     * 检查定位权限
     */
    public void checkAndRequestLocationPermission(Object activityOrFragment, PermissionCallback permissionCallback) {
        checkAndRequestPermission(activityOrFragment, permissionCallback, "没有定位权限", Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    /**
     * 检查权限，如果没有权限就请求权限
     */
    public void checkAndRequestPermission(Object activityOrFragment, PermissionCallback permissionCallback, String denyStr, String... permissions) {
        this.activityOrFragment = activityOrFragment;
        this.permissionCallback = permissionCallback;
        if (activityOrFragment == null) {
            Ln.e("activityOrFragment is null");
            return;
        }
        if (permissionCallback == null) {
            Ln.e("permissionCallback is null");
            return;
        }

        getActivity(activityOrFragment);

        if (activity == null) {
            Ln.e("activity is null");
            return;
        }

        if (checkDestroy()) {
            return;
        }

        if (EasyPermissions.hasPermissions(activity, permissions)) {
            permissionCallback.onPermissionsGranted();
            permissionCallback.onPermissionsAllGranted();
        } else {
            EasyPermissions.requestPermissions(activityOrFragment, denyStr, PERMISSION_REQUEST_CODE, permissions);
        }
    }


    /**
     * 继续处理权限相关剩余事情
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     * @return #true 代表这个是权限处理  #false代表这个是非权限处理的code
     */
    public boolean continueProcessPermission(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PERMISSION_REQUEST_CODE != requestCode) {
            return false;
        }

        if (checkDestroy()) {
            return true;
        }


        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, activityOrFragment, new PermissionResultListener() {
            @Override
            public void onPermissionsGranted(int requestCode, List<String> perms) {
                if (permissionCallback != null) {
                    permissionCallback.onPermissionsGranted();
                }
            }

            @Override
            public void onPermissionsDenied(int requestCode, List<String> perms) {
                if (permissionCallback != null) {
                    permissionCallback.onPermissionsDenied();
                }

            }

            @Override
            public void onPermissionsAllGranted() {
                if (permissionCallback != null) {
                    permissionCallback.onPermissionsAllGranted();
                }
            }

            @Override
            public void onCloseActivity() {

            }

            @Override
            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                Ln.e("onRequestPermissionsResult");
            }
        });
        return true;
    }

    /**
     * 权限处理的回调
     */
    public interface PermissionCallback {
        /**
         * 权限被授予
         */
        void onPermissionsGranted();

        /**
         * 权限被拒绝
         */
        void onPermissionsDenied();

        /**
         * 所有权限请求成功
         */
        void onPermissionsAllGranted();
    }
}
