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

public class PrroximityTrainingActivity extends ActionBarActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor proximity;
    private boolean onEvent;
    private float distance;
    private TextView result_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prroximity_training);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        result_tv = (TextView)findViewById(R.id.proximity_result_tv);
        onEvent = false;
    }

    @Override
    protected void onStart(){
        super.onStart();
        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(this);
        Tracker tracker = googleAnalytics.newTracker(R.xml.analytic);
        tracker.enableAdvertisingIdCollection(true);
        tracker.setScreenName("com.giocodestrezza.training.activity.PrroximityTrainingActivity");
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(this,proximity,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor s,int a){

    }

    @Override
    public void onSensorChanged(SensorEvent event){

        if(!onEvent){
            onEvent = true;
            distance = event.values[0];
            if(distance == 0){
                result_tv.setText(R.string.ok_msg);
             }else{
                result_tv.setText(R.string.piu_vicino_msg);
            }
            onEvent = false;
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prroximity_training, menu);
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
}
