package com.gohool.wbudyprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MenuActivity extends AppCompatActivity {

    private Gyroscope gyroscope;
    private Intent intent;



    @Override
    protected void onResume() {
        super.onResume();
        gyroscope.register();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final Intent intent;

        gyroscope = new Gyroscope(getBaseContext());
        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                //showInfo(rx,ry,rz);

//                if(rz > 1.0f)
//                {
//                    getWindow().getDecorView().setBackgroundColor(Color.RED);
//                }
//                if(rz < -1.0f)
//                {
//                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
//                }

                if(rx > 1.0f)
                {
                    //getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                    newIntent(1);
                }
//                if(rx < -1.0f)
//                {
//                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
//                }

                if(ry > 1.0f)
                {
                    //getWindow().getDecorView().setBackgroundColor(Color.CYAN);
                    newIntent(2);
                }
                if(ry < -1.0f)
                {
                    //getWindow().getDecorView().setBackgroundColor(Color.DKGRAY);
                    newIntent(3);
                }
            }
        });


    }

    void newIntent(int w)
    {
        switch (w){
            case 1:
            {
                intent = new Intent(getBaseContext(),MagneticDetectorActivity.class);
                break;
            }
            case 2:
            {
                intent = new Intent(getBaseContext(),ProximityActivity.class);
                break;
            }
            case 3:
            {
                intent = new Intent(getBaseContext(),SoundByShakeActivity.class);
                break;
            }

        }

        startActivity(intent);
    }



    @Override
    protected void onPause() {
        super.onPause();
        gyroscope.unregister();

    }



}
