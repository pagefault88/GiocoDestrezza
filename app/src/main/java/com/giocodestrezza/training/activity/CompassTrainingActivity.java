package com.giocodestrezza.training.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.giocodestrezza.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class CompassTrainingActivity extends ActionBarActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor compass;
    float gradi = 0.0f;
    ImageView compassImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass_training);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        compass = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        compassImg = (ImageView)findViewById(R.id.compasss_iv);

    }

    @Override
    protected void onStart(){
        super.onStart();
        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(this);
        Tracker tracker = googleAnalytics.newTracker(R.xml.analytic);
        tracker.enableAdvertisingIdCollection(true);
        tracker.setScreenName("com.giocodestrezza.training.activity.CompassTrainingActivity");
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(this,compass,SensorManager.SENSOR_DELAY_NORMAL);
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

        Toast toast = Toast.makeText(getApplicationContext(),"Ok.Posizionato", Toast.LENGTH_SHORT);
        float degree = Math.round(event.values[0]);

        RotateAnimation rotateAnimation = new RotateAnimation(gradi,-degree, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);

        compassImg.startAnimation(rotateAnimation);

        if(degree < 10){

            toast.show();
        }

        gradi = -degree;
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compass_training, menu);
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
