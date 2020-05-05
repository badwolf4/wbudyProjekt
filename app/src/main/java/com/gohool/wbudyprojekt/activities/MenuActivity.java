package com.gohool.wbudyprojekt.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.gohool.wbudyprojekt.sensors.Gyroscope;
import com.gohool.wbudyprojekt.sensors.LightSensor;
import com.gohool.wbudyprojekt.R;

public class MenuActivity extends AppCompatActivity {

    private Gyroscope gyroscope;
    private LightSensor lightSensor;

    private Intent intent;

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;



    @Override
    protected void onResume() {
        super.onResume();
        gyroscope.register();
        lightSensor.register();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        textView1 = findViewById(R.id.krokomierz_view);
        textView2 = findViewById(R.id.dystans_view);
        textView3 = findViewById(R.id.kompas_view);

        gyroscope = new Gyroscope(getBaseContext());
        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                //przy obrocie ekranu w dol
                if(rx > 1.0f)
                {
                    //przekierujemy do funkcji ktora tworzy nowy intent i przechodzi do nastepnego activity
                    newIntent(1);
                }
                //przy obrocie eranu wprawo
                if(ry > 1.0f)
                {
                    newIntent(2);
                }
                //przy obrocie eranu wlewo
                if(ry < -1.0f)
                {
                    newIntent(3);
                }
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
        textView1.setTextColor(Color.BLACK);
        textView2.setTextColor(Color.BLACK);
        textView3.setTextColor(Color.BLACK);
    }

    void darkTheme()
    {
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        textView1.setTextColor(Color.rgb(98,0,238));
        textView2.setTextColor(Color.rgb(98,0,238));
        textView3.setTextColor(Color.rgb(98,0,238));
    }

    void newIntent(int w)
    {
        //inicjujemy activity przekazujac class tego activity do ktorego chcemy przejsc
        switch (w){
            case 1:
            {
                intent = new Intent(getBaseContext(), MagneticDetectorActivity.class);
                break;
            }
            case 2:
            {
                intent = new Intent(getBaseContext(), ProximityActivity.class);
                break;
            }
            case 3:
            {
                intent = new Intent(getBaseContext(), SoundByShakeActivity.class);
                break;
            }

        }

        startActivity(intent);
    }



    @Override
    protected void onPause() {
        super.onPause();
        gyroscope.unregister();
        lightSensor.unregister();

    }



}
