package com.gohool.wbudyprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class KrokomierzActivity extends AppCompatActivity {

    private TextView stepsTextView;
    private Button startButton;
    private Button stopButton;
    private Button resetButton;

    private Accelerometer accelerometer;

    private int steps = 0;
    private double previousMagnitude=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krokomierz);
        init();
        setButtonsOnClick();

        accelerometer = new Accelerometer(getBaseContext());
        accelerometer.setListener(new Accelerometer.Listener() {
            @Override
            public void onAccelerationChanged(float ax, float ay, float az) {
                double magnitude = Math.sqrt(Math.pow(ax,2) + Math.pow(ay,2) + Math.pow(az,2));
                double magnitudeDelta = magnitude - previousMagnitude;
                previousMagnitude = magnitude;
                if(magnitudeDelta > 6)
                {
                    steps++;
                }
                showInfo();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    void showInfo()
    {
        stepsTextView.setText(Integer.toString(steps));
    }

    private void init()
    {
        stepsTextView = findViewById(R.id.steps);
        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);
        resetButton = findViewById(R.id.reset_button);

    }

    private void setButtonsOnClick()
    {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"Start button pressed",Toast.LENGTH_LONG).show();
                accelerometer.register();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"Stop button pressed",Toast.LENGTH_LONG).show();
                accelerometer.unRegister();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"Reset button pressed",Toast.LENGTH_LONG).show();
                accelerometer.unRegister();
                steps = 0;
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
