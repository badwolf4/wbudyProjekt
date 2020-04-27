package com.gohool.wbudyprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class KompasActivity extends AppCompatActivity {

    private ImageView image;
    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];
    private float azimuth = 0f;
    private float correctAzimus = 0f;

    MagneticField magneticField;
    Accelerometer accelerometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kompas);
        getWindow().getDecorView().setBackgroundColor(Color.DKGRAY);

        image = (ImageView) findViewById(R.id.kompasImage);

        magneticField = new MagneticField(getBaseContext());

        magneticField.setListener(new MagneticField.Listener() {
            @Override
            public void onMagneticFieldChanged(float sx, float sy, float sz) {
                final float alpha = 0.97f;
                //synchronized(this){
                mGeomagnetic[0] = alpha*mGeomagnetic[0]+(1-alpha)*sx;
                mGeomagnetic[1] = alpha*mGeomagnetic[1]+(1-alpha)*sy;
                mGeomagnetic[2] = alpha*mGeomagnetic[2]+(1-alpha)*sz;

                onSensorsValuesChanged();
            }
        });

        accelerometer = new Accelerometer(getBaseContext());

        accelerometer.setListener(new Accelerometer.Listener() {
            @Override
            public void onAccelerationChanged(float ax, float ay, float az) {
                final float alpha = 0.97f;
                //synchronized(this){
                mGravity[0] = alpha*mGravity[0]+(1-alpha)*ax;
                mGravity[1] = alpha*mGravity[1]+(1-alpha)*ay;
                mGravity[2] = alpha*mGravity[2]+(1-alpha)*az;

                onSensorsValuesChanged();
            }
        });


    }

    //moja funkcja wywolywana po kazdej zmianie zarejestrowana przez czujniki

    private void onSensorsValuesChanged()
    {
        float r[] = new float[9];
        float i[] = new float[9];

        boolean success = SensorManager.getRotationMatrix(r,i,mGravity,mGeomagnetic);

        if(success)
        {
            float orientation[] = new float[3];
            SensorManager.getOrientation(r,orientation);
            azimuth = (float)Math.toDegrees(orientation[0]);
            azimuth = (azimuth+360)%360;

            Animation anim = new RotateAnimation(-correctAzimus, -azimuth, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF,0.5f);
            correctAzimus = azimuth;
            anim.setDuration(500);
            anim.setRepeatCount(0);
            anim.setFillAfter(true);

            image.startAnimation(anim);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        accelerometer.register();
        magneticField.register();

    }

    @Override
    protected void onPause() {
        super.onPause();
        accelerometer.unRegister();
        magneticField.unregister();
    }
}
