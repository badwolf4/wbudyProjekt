package com.gohool.wbudyprojekt.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class LightSensor {

    public interface Listener
    {
        void onLightChanged(float light);
    }

    private Listener listener;
    public void setListener(Listener l)
    {
        listener = l;
    }

    private SensorManager manager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    public LightSensor(Context context)
    {
        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        assert manager != null;
        sensor = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(listener!=null)
                {
                    //pobiera wartosc mocy swiatla
                    listener.onLightChanged(sensorEvent.values[0]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    public void register()
    {
        manager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister()
    {
        manager.unregisterListener(sensorEventListener);
    }
}
