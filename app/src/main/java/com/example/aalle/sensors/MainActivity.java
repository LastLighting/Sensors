package com.example.aalle.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView aX;
    TextView aY;
    TextView aZ;
    TextView mX;
    TextView mY;
    TextView mZ;
    TextView proximity;
    TextView light;
    SensorManager sensorManager;
    Sensor aSensor;
    Sensor pSensor;
    Sensor mSensor;
    Sensor lSensor;
    Display display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aX = (TextView) findViewById(R.id.accelX);
        aY = (TextView) findViewById(R.id.accelY);
        aZ = (TextView) findViewById(R.id.accelZ);
        mX = (TextView) findViewById(R.id.magnX);
        mY = (TextView) findViewById(R.id.magnY);
        mZ = (TextView) findViewById(R.id.magnZ);
        proximity = (TextView) findViewById(R.id.textView18);
        light = (TextView) findViewById(R.id.textView19);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        aSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        pSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        lSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
    }

    @Override
    public void onStart() {
        super.onStart();
        sensorManager.registerListener(this, aSensor, sensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, mSensor, sensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, pSensor, sensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, lSensor, sensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this, aSensor);
        sensorManager.unregisterListener(this, mSensor);
        sensorManager.unregisterListener(this, pSensor);
        sensorManager.unregisterListener(this, lSensor);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            aX.setText(Float.toString(event.values[0]));
            aY.setText(Float.toString(event.values[1]));
            aZ.setText(Float.toString(event.values[2]));
            if (event.values[0] > 0 && light.getX()>0){
                light.setX( light.getX()-event.values[0]);
            }
            if (event.values[0] < 0 && light.getX()<display.getWidth()-light.getWidth()){
                light.setX( light.getX()-event.values[0]);
            }
            if (event.values[1] < 0 && light.getY()>0){
                light.setY( light.getY()+event.values[1]);
            }
            if (event.values[1] > 0 && light.getY()<display.getHeight()-light.getHeight()-170){
                light.setY( light.getY()+event.values[1]);
            }
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mX.setText(Float.toString(event.values[0]));
            mY.setText(Float.toString(event.values[1]));
            mZ.setText(Float.toString(event.values[2]));
        }
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            proximity.setText(Float.toString(event.values[0]));
        }
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            light.setText(Float.toString(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
