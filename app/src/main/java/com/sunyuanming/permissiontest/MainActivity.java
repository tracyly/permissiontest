package com.sunyuanming.permissiontest;

import android.bluetooth.BluetoothAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sunyuanming.permissiontest.base.BasePermissionActivity;
import com.sunyuanming.permissiontest.permission.PermissionProcessor;
import com.sunyuanming.permissiontest.util.Ln;


/**
 * @author sunyuanming
 * 权限申请测试类
 */
public class MainActivity extends BasePermissionActivity implements View.OnClickListener{

    private Button blueToothBtn,locationBtn,storageBtn;

    private BluetoothAdapter bluetoothAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blueToothBtn=$(R.id.bluetooth_btn);
        locationBtn=$(R.id.location_btn);
        storageBtn=$(R.id.storage_btn);
        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        blueToothBtn.setOnClickListener(this);
        locationBtn.setOnClickListener(this);
        storageBtn.setOnClickListener(this);
    }

    PermissionProcessor.PermissionCallback permissionCallback=new PermissionProcessor.PermissionCallback() {
        @Override
        public void onPermissionsGranted() {
            Ln.e("权限被授予");

        }

        @Override
        public void onPermissionsDenied() {
         Ln.e("权限被拒绝");

        }

        @Override
        public void onPermissionsAllGranted() {

        }
    };
    @Override
    public void onClick(View v){
        switch (v.getId())
        {
            case R.id.bluetooth_btn:
                if(bluetoothAdapter!=null&&!bluetoothAdapter.isEnabled())
                {
                    bluetoothAdapter.enable();
                }
                break;
            case R.id.storage_btn:
                getPermissionProcessor().checkAndRequestStoragePermission(this,permissionCallback);
                break;
            case R.id.location_btn:
                getPermissionProcessor().checkAndRequestLocationPermission(this,permissionCallback);
                break;
        }
    }
}
