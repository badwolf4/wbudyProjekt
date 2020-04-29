package com.gohool.wbudyprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MagneticDetectorActivity extends AppCompatActivity {
    private TextView magneticTextView;
    private TextView isDetectedTextView;
    private MagneticField magneticField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetic_detector);

        magneticTextView = (TextView) findViewById(R.id.magneticFieldView);
        isDetectedTextView = (TextView) findViewById(R.id.isDetected);

        magneticField = new MagneticField(getBaseContext());
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
    }

    void showInfo(double tesla)
    {
        //magneticTextView.setText(Double.toString(tesla));
        if(tesla>=90)
            isDetectedTextView.setText(R.string.magnetic_detected);
        else
            isDetectedTextView.setText(R.string.magnetic_not_detected);
    }

    @Override
    protected void onResume() {
        super.onResume();
        magneticField.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        magneticField.unregister();
    }
}
