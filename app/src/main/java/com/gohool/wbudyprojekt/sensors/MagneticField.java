package com.gohool.wbudyprojekt.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MagneticField {
    public interface Listener
    {
        void onMagneticFieldChanged(float sx, float sy, float sz);
    }
    private Listener listener;
    public void setListener(Listener l)
    {
        listener = l;
    }

    private SensorManager manager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    public MagneticField(Context context)
    {
        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        assert manager != null;
        sensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(listener!=null)
                {
                    //pobiera magnetyczne pole po osiach x, y, z
                    listener.onMagneticFieldChanged(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    public void register()
    {
        manager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_GAME);
    }

    public void unregister()
    {
        manager.unregisterListener(sensorEventListener);
    }
}
