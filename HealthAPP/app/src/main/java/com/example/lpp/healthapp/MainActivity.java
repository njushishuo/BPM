package com.example.lpp.healthapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sm;
    private TextView mtextViewx,mtextViewy,mtextViewz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtextViewx=findViewById(R.id.x);
        mtextViewy=findViewById(R.id.y);
        mtextViewz=findViewById(R.id.z);
        //创建一个SensorManager来获取系统的传感器服务
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // 计步统计
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER), SensorManager.SENSOR_DELAY_NORMAL);
        // 单次计步
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR), SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float X_lateral = sensorEvent.values[0];
            float Y_longitudinal = sensorEvent.values[1];
            float Z_vertical = sensorEvent.values[2];
            mtextViewx.setText(X_lateral + "");
            mtextViewy.setText(Y_longitudinal + "");
            mtextViewz.setText(Z_vertical + "");
        }else if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            float X_lateral = sensorEvent.values[0];
            float Y_longitudinal = sensorEvent.values[1];
            float Z_vertical = sensorEvent.values[2];
            mtextViewx.setText("x轴的磁场强度\n"+ X_lateral );
            mtextViewy.setText("y轴的磁场强度\n"+ Y_longitudinal );
            mtextViewz.setText("z轴的磁场强度\n"+ Z_vertical );
        }else if(sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION){
            float X_lateral = sensorEvent.values[0];
            float Y_longitudinal = sensorEvent.values[1];
            float Z_vertical = sensorEvent.values[2];
            mtextViewx.setText("绕z轴转过的角度\n"+ X_lateral );
            mtextViewy.setText("绕x轴转过的角度\n"+ Y_longitudinal );
            mtextViewz.setText("绕y轴转过的角度\n"+ Z_vertical );
        }else if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT){
            float X = sensorEvent.values[0];
            mtextViewx.setText("光强度为为"+ X );
        }else if(sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY){
            float X = sensorEvent.values[0];
            mtextViewx.setText("距离为"+ X );
        }else if(sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            float X = sensorEvent.values[0];
            mtextViewx.setText("COUNTER："+ X );
        } else if(sensorEvent.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            float X = sensorEvent.values[0];
            mtextViewx.setText("DECTOR："+ X );
        }

    }

    @Override
    public void onPause() {
        sm.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
