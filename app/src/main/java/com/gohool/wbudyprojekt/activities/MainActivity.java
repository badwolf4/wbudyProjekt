package com.gohool.wbudyprojekt.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gohool.wbudyprojekt.sensors.LightSensor;
import com.gohool.wbudyprojekt.R;

public class MainActivity extends AppCompatActivity {

    private Button button;

    private LightSensor lightSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.start_button);
        //obsluga przycisku na ekranie
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        lightSensor = new LightSensor(getBaseContext());
        lightSensor.setListener(new LightSensor.Listener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onLightChanged(float light) {

                if(light > 500)
                {
                    //ustawianie koloru tla aplikacji i tla przycisku
                    getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                    button.setBackgroundColor(Color.WHITE);
                }
                else
                {
                    getWindow().getDecorView().setBackgroundColor(Color.BLACK);
                    button.setBackgroundColor(Color.rgb(98,0,238));
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
