package com.giocodestrezza.training.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.giocodestrezza.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class AccelerometerTrainingActivity extends ActionBarActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float lastAcc = 0.0f;
    private float acceleration = 0.0f;
    private float totAcc = 0.0f;
    private boolean onEvent = false;
    private TextView accRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer_training);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        lastAcc = SensorManager.GRAVITY_EARTH;
        acceleration = SensorManager.GRAVITY_EARTH;

        accRes = (TextView)findViewById(R.id.acc_training_res_tv);

    }

    @Override
    protected void onStart(){
        super.onStart();
        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(this);
        Tracker tracker = googleAnalytics.newTracker(R.xml.analytic);
        tracker.enableAdvertisingIdCollection(true);
        tracker.setScreenName("com.giocodestrezza.training.activity.AccelerometerTrainingActivity");
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public  void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor s,int a){

    }

    @Override
    public void onSensorChanged(SensorEvent event){



        if(!onEvent) {
            onEvent = true;

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            lastAcc = acceleration;
            acceleration = x * x + y * y + z * z;
            float diff = acceleration - lastAcc;
            totAcc = diff * acceleration;


                if(totAcc > 150000){
                    accRes.setText(R.string.ok_msg);
                }else {
                    accRes.setText(R.string.piu_forte_msg);
                }

            }

            onEvent = false;
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_accelerometer_training, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */


