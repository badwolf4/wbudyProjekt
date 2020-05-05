package com.gohool.wbudyprojekt.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.gohool.wbudyprojekt.sensors.LightSensor;
import com.gohool.wbudyprojekt.sensors.Proximity;
import com.gohool.wbudyprojekt.R;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ProximityActivity extends AppCompatActivity {

    private Proximity proximity;
    private LightSensor lightSensor;

    private TextView proximityView;

    private boolean flashLightState;

    private CameraManager cameraManager;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        proximityView = findViewById(R.id.proximityTextView);
        proximity = new Proximity(getBaseContext());
        proximityView.setText(R.string.wlacz_latarke);
        proximityView.setTextColor(Color.RED);

        flashLightState = false;
        proximity.setListener(new Proximity.Listener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onProximityChanged(float d) {
        //interpretacja wynikow odczytanych z proximity
                if(d==0){
                    cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    if(!flashLightState)
                    {
                        //wlaczanie latarki
                        try {
                            assert cameraManager != null;
                            String cameraId = cameraManager.getCameraIdList()[0];
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                //dokladnie tu wlaczamy
                                cameraManager.setTorchMode(cameraId,true);

                                flashLightState = true;
                                proximityView.setText(R.string.wylacz_latarke);
                                proximityView.setTextColor(Color.GREEN);
                            }
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        //wylaczenie latarki
                        try {
                            assert cameraManager != null;
                            String cameraId = cameraManager.getCameraIdList()[0];
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                //dokladnie tu wylaczamy
                                cameraManager.setTorchMode(cameraId,false);

                                flashLightState = false;
                                proximityView.setText(R.string.wlacz_latarke);
                                proximityView.setTextColor(Color.RED);
                            }
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        });

        lightSensor = new LightSensor(getBaseContext());
        //interpretacja wynikow odczytane z light sensor
        lightSensor.setListener(new LightSensor.Listener() {
            @Override
            public void onLightChanged(float light) {
                if(light>500)
                    lightTheme();
                else
                    darkTheme();
            }
        });
    }

    void lightTheme()
    {
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
    }

    void darkTheme()
    {
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void onResume() {
        super.onResume();
        proximity.register();
        lightSensor.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        proximity.unregister();
        //dodatkowo wylaczamy latarke przy wyjsciu z aplikacji, czy tez tego activity
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId,false);
                flashLightState = true;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        lightSensor.unregister();
    }
}
