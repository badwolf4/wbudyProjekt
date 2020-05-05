package com.gohool.wbudyprojekt.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Accelerometer {

    //tworzymy wlasny interfejs do interpretacji wynikow ktore odczytujemy z sensora
    public interface Listener
    {
        void onAccelerationChanged(float ax, float ay, float az);
    }

    private Listener listener;

    public void setListener(Listener l)
    {
        listener = l;
    }

    private SensorManager manager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    public Accelerometer(Context context)
    {
        //pobieramy sensor od sensor managera
        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        assert manager != null;
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //domyslny listener implementowany przy uzyciu sensora
        //odwolujemy sie w nim do metody z naszego wlasnego listenera, zebysmy mogli w miejscu gdzie
        //fo uzywamy implementowac wygodnie swoje sztuczki co robic z danymi
        sensorEventListener = new SensorEventListener() {
            //metoda wywolywana przy zarejestrowaniu sensorem jakichs zmian
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(listener!=null)
                {
                    //pobiera 3 wartosci, przyspieszenie po osiach x, y, z
                    //no i przesylamy do naszego nasluchiwacza do interpretacji
                    listener.onAccelerationChanged(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    //rejestrowanie sensora, czyli wlaczenie
    public void register()
    {
        manager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_FASTEST);
    }

    //wylaczenie sensora
    public void unRegister()
    {
        manager.unregisterListener(sensorEventListener);
    }
}
