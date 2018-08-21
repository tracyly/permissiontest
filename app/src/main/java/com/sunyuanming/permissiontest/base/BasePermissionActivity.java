package com.sunyuanming.permissiontest.base;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.sunyuanming.permissiontest.permission.PermissionProcessor;


/**
 * @author sunyuanming
 * 权限activity基类
 */
public abstract class BasePermissionActivity extends AppCompatActivity{
    /**
     * 权限处理器
     */
    private PermissionProcessor permissionProcessor = new PermissionProcessor();

    /**
     * @return 获得权限处理器
     */
    public PermissionProcessor getPermissionProcessor() {
        return permissionProcessor;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionProcessor != null) {
            permissionProcessor.continueProcessPermission(requestCode, permissions, grantResults);
        }
    }

    protected <T> T $(int id)
    {
       T t= (T)findViewById(id);
       return t;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (permissionProcessor != null) {
            permissionProcessor.destroy();
        }
    }
}
