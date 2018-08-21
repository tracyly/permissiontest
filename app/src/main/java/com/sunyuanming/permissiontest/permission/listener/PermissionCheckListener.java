package com.sunyuanming.permissiontest.permission.listener;


/**
 * @author sunyuanming
 * 权限回调接口

 */
public interface PermissionCheckListener {
    void grantedPermission(boolean isGrant);//授予权限 true为新授予，false为已有权限
}