package com.gohool.wbudyprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ProximityActivity extends AppCompatActivity {

    Proximity proximity;

    TextView proximityView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        proximityView = findViewById(R.id.proximityTextView);
        proximity = new Proximity(getBaseContext());

        proximity.setListener(new Proximity.Listener() {
            @Override
            public void onProximityChanged(float d) {
                showInfo(d);
            }
        });
    }

    void showInfo(float d)
    {
        proximityView.setText(Float.toString(d));
    }

    @Override
    protected void onResume() {
        super.onResume();
        proximity.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        proximity.unregister();
    }
}
