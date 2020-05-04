package com.gohool.wbudyprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;

public class SoundByShakeActivity extends AppCompatActivity {

    private Accelerometer accelerometer;

    private float acelVal;
    private float acelLast;
    private float shake;
    private boolean shaked;
    private float first;
    private  boolean firstState;

    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_by_shake);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shaked = false;

        shake = 0.00f;
        first= 0.0f;
        firstState=true;

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        accelerometer = new Accelerometer(getBaseContext());
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

                if(shake > 12 && !shaked)
                {
                    shaked = true;
                    //Toast.makeText(getBaseContext(),"Shaked", Toast.LENGTH_LONG).show();
                    vibrator.vibrate(1000);
                }

                if(delta <= (first + 0.05) && delta>=(first - 0.05))
                    shaked = false;


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        accelerometer.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        accelerometer.unRegister();
    }
}
