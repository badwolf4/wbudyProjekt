package com.gohool.wbudyprojekt.sensors;

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

    public Proximity(Context context)
    {
        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        assert manager != null;
        sensor = manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(listener!=null)
                {
                    //zwraca wartosc 0 jesli obiekt wykryto, 5 jesli nie wykryto
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
