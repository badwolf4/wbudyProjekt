package com.gohool.wbudyprojekt;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.sip.SipSession;

public class Gyroscope {

    public interface Listener
    {
        void onRotation(float rx, float ry, float rz);
    }

    private Listener listener;

    public void setListener(Listener l)
    {
        listener = l;
    }

    private SensorManager manager;
    private Sensor sensor;
    private SensorEventListener sensorEventListenerlistener;

    Gyroscope(Context context)
    {
        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorEventListenerlistener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(listener!=null)
                {
                    listener.onRotation(sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    public void register()
    {
        manager.registerListener(sensorEventListenerlistener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister()
    {
        manager.unregisterListener(sensorEventListenerlistener);
    }
}
