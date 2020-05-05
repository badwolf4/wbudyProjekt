package com.gohool.wbudyprojekt.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.TextView;

import com.gohool.wbudyprojekt.sensors.Accelerometer;
import com.gohool.wbudyprojekt.sensors.LightSensor;
import com.gohool.wbudyprojekt.R;

public class SoundByShakeActivity extends AppCompatActivity {

    private Accelerometer accelerometer;
    private LightSensor lightSensor;

    private float acelVal;
    private float acelLast;
    private float shake;
    private boolean shaked;
    private float first;
    private  boolean firstState;

    private Vibrator vibrator;
    private TextView shakeMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_by_shake);

        shakeMe = findViewById(R.id.shakeMe);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shaked = false;

        shake = 0.00f;
        first= 0.0f;
        firstState=true;

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        accelerometer = new Accelerometer(getBaseContext());
        //obsluga wstrzasniec
        accelerometer.setListener(new Accelerometer.Listener() {
            @Override
            public void onAccelerationChanged(float ax, float ay, float az) {
                acelLast = acelVal;
                acelVal = (float) Math.sqrt((double)(ax*ax) + (double)(ay*ay) + (double)(az*az));
                float delta = acelVal - acelLast;
                shake = shake * 0.9f + delta;
                if(firstState)
                    {
                        first = delta;
                        firstState = false;
                    }
                //zmienna shake tu wprowadzona dla tego zeby nie wlaczala sie wibracja czesciej niz 1 raz po wstrzasnieciu
                if(shake > 12 && !shaked)
                {
                    shaked = true;
                    //wlaczamy wibracje
                    vibrator.vibrate(1000);
                }

                //zmieniamy stan shaked po tym jak odczytane przyspieszenia stana bardzo male co
                // by oznaczalo koniec wstrzasniecia
                if(delta <= (first + 0.05) && delta>=(first - 0.05))
                    shaked = false;


            }
        });

        lightSensor = new LightSensor(getBaseContext());
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
        shakeMe.setTextColor(Color.BLACK);
    }

    void darkTheme()
    {
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        shakeMe.setTextColor(Color.rgb(98,0,238));
    }

    @Override
    protected void onResume() {
        super.onResume();
        accelerometer.register();
        lightSensor.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        accelerometer.unRegister();
        lightSensor.unregister();
    }
}
