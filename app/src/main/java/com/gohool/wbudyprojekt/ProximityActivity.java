package com.gohool.wbudyprojekt;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ProximityActivity extends AppCompatActivity {

    Proximity proximity;

    TextView proximityView;
    private static final  int CAMERA_REQUEST = 50;
    private boolean flashLightState;

    CameraManager cameraManager;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        proximityView = findViewById(R.id.proximityTextView);
        proximity = new Proximity(getBaseContext());
        proximityView.setText(R.string.wlacz_latarke);
        //showInfo(R.string.wylacz_latarke);

//        final boolean hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
//        boolean isEnabled = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
//
        flashLightState = false;
        proximity.setListener(new Proximity.Listener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onProximityChanged(float d) {


                if(d==0){
                    cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    if(!flashLightState)
                    {
                        try {
                            String cameraId = cameraManager.getCameraIdList()[0];
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                cameraManager.setTorchMode(cameraId,true);
                                flashLightState = true;
                                proximityView.setText(R.string.wylacz_latarke);
                                //showInfo(R.string.wylacz_latarke);
                            }
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        try {
                            String cameraId = cameraManager.getCameraIdList()[0];
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                cameraManager.setTorchMode(cameraId,false);
                                flashLightState = false;
                                proximityView.setText(R.string.wlacz_latarke);
                                //showInfo(R.string.wylacz_latarke);
                            }
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        });
    }

//    void showInfo(int t)
//    {
//        proximityView.setText(t);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        proximity.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        proximity.unregister();
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId,false);
                flashLightState = true;
                //showInfo(R.string.wylacz_latarke);

            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}
