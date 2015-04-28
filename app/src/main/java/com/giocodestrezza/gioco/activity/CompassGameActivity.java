package com.giocodestrezza.gioco.activity;

import android.app.DialogFragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.giocodestrezza.R;
import com.giocodestrezza.gioco.UserData;
import com.giocodestrezza.gioco.Util;
import com.giocodestrezza.gioco.dialog.BackButtonDialog;
import com.giocodestrezza.gioco.dialog.TimeOutDialog;
import com.giocodestrezza.gioco.dialog.WinDialog;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class CompassGameActivity extends ActionBarActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor orientation;
    private TextView timer_tv;
    private CountDownTimer timer;

    private boolean onEvent = false;

    private long timeElapsed;

    private Integer levelScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass_game);

        timer_tv = (TextView)findViewById(R.id.compass_time_tv);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        orientation = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        levelScore = 0;

        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer_tv.setText("Tempo Residuo: " + millisUntilFinished/1000 + " secondi");
                timeElapsed = millisUntilFinished/1000;
            }

            @Override
            public void onFinish() {
                DialogFragment dialogFragment = new TimeOutDialog();
                dialogFragment.show(getFragmentManager(),"TIME_OUT_COMPASS");
            }
        };
    }

    @Override
    protected void onStart(){
        super.onStart();
        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(this);
        Tracker tracker = googleAnalytics.newTracker(R.xml.analytic);
        tracker.enableAdvertisingIdCollection(true);
        tracker.setScreenName("com.giocodestrezza.gioco.activity.CompassGameActivity");
        tracker.send(new HitBuilders.AppViewBuilder().build());


    }

    @Override
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(this,orientation,SensorManager.SENSOR_DELAY_NORMAL);
        timer.start();
    }

    @Override
    public  void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
        timer.cancel();
    }

    public void onBackPressed(){
        DialogFragment dialogFragment = BackButtonDialog.newInstance(this.getClass());
        dialogFragment.show(getFragmentManager(),"BACK_BUTTON_COMPASS");
    }
    @Override
    public void onAccuracyChanged(Sensor s,int a){

    }

    @Override
    public void onSensorChanged(SensorEvent event){

        float azimuth_angle;
        if(!onEvent) {
            azimuth_angle = event.values[0];

            if(azimuth_angle < 10){
                onEvent = true;
                timer.cancel();

                levelScore = Util.scoreCalc(timeElapsed);
                UserData.compassGameScore = levelScore;
                UserData.addScore(levelScore);



                DialogFragment dialogFragment = WinDialog.newInstance(ProximityGameActivity.class,levelScore);
                dialogFragment.setCancelable(false);
                dialogFragment.show(getFragmentManager(),"WIN_COMPASS");
            }
        }
    }

/*
    private Integer calcScore(long time){
        return (int)(long)time*10;
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compass_game, menu);
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
