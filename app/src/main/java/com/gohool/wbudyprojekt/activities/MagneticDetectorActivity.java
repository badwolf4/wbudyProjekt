package com.gohool.wbudyprojekt.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.gohool.wbudyprojekt.sensors.LightSensor;
import com.gohool.wbudyprojekt.sensors.MagneticField;
import com.gohool.wbudyprojekt.R;

public class MagneticDetectorActivity extends AppCompatActivity {
    private TextView isDetectedTextView;

    private MagneticField magneticField;
    private LightSensor lightSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetic_detector);
        isDetectedTextView = findViewById(R.id.isDetected);

        magneticField = new MagneticField(getBaseContext());
        //ustawiamy wlasny listener dla magnetic field
        magneticField.setListener(new MagneticField.Listener() {
            @Override
            public void onMagneticFieldChanged(float sx, float sy, float sz) {
                float azimuth = Math.round(sx);
                float pitch = Math.round(sy);
                float roll = Math.round(sz);

                double tesla = Math.sqrt(Math.pow(azimuth,2) + Math.pow(pitch,2) + Math.pow(roll,2));

                showInfo(Math.round(tesla));

            }
        });

        //ustawiamy wlasny listener dla light sensor
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
    }

    void darkTheme()
    {
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);
    }

    void showInfo(double tesla)
    {
        //magneticTextView.setText(Double.toString(tesla));
        if(tesla>=90)
        {
            isDetectedTextView.setText(R.string.magnetic_detected);
            isDetectedTextView.setTextColor(Color.GREEN);
        }
        else
        {
            isDetectedTextView.setText(R.string.magnetic_not_detected);
            isDetectedTextView.setTextColor(Color.RED);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //rejestrujemy obowiazkowo tu sensory
        magneticField.register();
        lightSensor.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //odrejestrujemy obowiazkowo tu sensory zeby zostaly wylaczone po wyjsciu z aplikacji
        magneticField.unregister();
        lightSensor.unregister();
    }
}
