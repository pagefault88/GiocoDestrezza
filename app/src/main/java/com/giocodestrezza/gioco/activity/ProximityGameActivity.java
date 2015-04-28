package com.giocodestrezza.gioco.activity;

import android.app.DialogFragment;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.giocodestrezza.R;
import com.giocodestrezza.gioco.UserData;
import com.giocodestrezza.gioco.dialog.BackButtonDialog;
import com.giocodestrezza.gioco.dialog.TimeOutDialog;
import com.giocodestrezza.gioco.dialog.WinDialog;
import com.giocodestrezza.gioco.Util;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class ProximityGameActivity extends ActionBarActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor proximitySens;

    private TextView timer_tv;

    private Integer levelScore;
    private long timeElapsed;

    private CountDownTimer timer;
    private boolean onEvent;
    private float distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity_game);

        timer_tv = (TextView)findViewById(R.id.proximity_time_tv);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        proximitySens = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        levelScore = 0;
        timeElapsed = 0;
        onEvent = false;

        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer_tv.setText("Tempo Residuo: " + millisUntilFinished/1000 + " secondi");
                timeElapsed = millisUntilFinished/1000;
            }

            @Override
            public void onFinish() {
                DialogFragment dialogFragment = new TimeOutDialog();
                dialogFragment.show(getFragmentManager(),"TIME_ELAPSED_PROXIMITY");
            }
        };

    }


    @Override
    protected void onStart(){
        super.onStart();
        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(this);
        Tracker tracker = googleAnalytics.newTracker(R.xml.analytic);
        tracker.enableAdvertisingIdCollection(true);
        tracker.setScreenName("com.giocodestrezza.gioco.activity.ProximityGameActivity");
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(this,proximitySens,SensorManager.SENSOR_DELAY_NORMAL);
        timer.start();
    }

    @Override
    public void onPause(){
        super.onPause();
        timer.cancel();
        sensorManager.unregisterListener(this);
    }

    public void onBackPressed(){
        DialogFragment dialogFragment = BackButtonDialog.newInstance(this.getClass());
        dialogFragment.show(getFragmentManager(),"BACK_BUTTON_PROXIMITY");
    }

    @Override
    public void onAccuracyChanged(Sensor s,int a){

    }

    @Override
    public void  onSensorChanged(SensorEvent event){


        if(!onEvent){
            distance = event.values[0];
            if(distance == 0){
                onEvent = false;
                timer.cancel();

                levelScore = Util.scoreCalc(timeElapsed);
                UserData.proximityGameScore = levelScore;
                UserData.addScore(levelScore);

                DialogFragment dialogFragment = WinDialog.newInstance(RecapActivity.class,levelScore);
                dialogFragment.setCancelable(false);
                dialogFragment.show(getFragmentManager(),"WIN_PROXIMITY");
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
        getMenuInflater().inflate(R.menu.menu_proximity_game, menu);
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
