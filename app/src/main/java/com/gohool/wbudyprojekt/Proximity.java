package com.gohool.wbudyprojekt;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Proximity {

    public interface Listener
    {
        void onProximityChanged(float d);
    }

    private Listener listener;
    private SensorManager manager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    public void setListener(Listener l)
    {
        listener = l;
    }

    Proximity(Context context)
    {
        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(listener!=null)
                {
                    listener.onProximityChanged(sensorEvent.values[0]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    public void register()
    {
        manager.registerListener(sensorEventListener, sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister()
    {
        manager.unregisterListener(sensorEventListener);
    }
}
