package com.gohool.wbudyprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button;

    private LightSensor lightSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.start_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),MenuActivity.class);
                startActivity(intent);
            }
        });

        lightSensor = new LightSensor(getBaseContext());
        lightSensor.setListener(new LightSensor.Listener() {
            @Override
            public void onLightChanged(float light) {

                if(light > 500)
                {
                    getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                }
                else
                {
                    getWindow().getDecorView().setBackgroundColor(Color.DKGRAY);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        lightSensor.register();

    }

    @Override
    protected void onPause() {
        super.onPause();
        lightSensor.unregister();
    }
}
