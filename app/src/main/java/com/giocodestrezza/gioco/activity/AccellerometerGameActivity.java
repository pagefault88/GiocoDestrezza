package com.giocodestrezza.gioco.activity;

import android.app.DialogFragment;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
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


public class AccellerometerGameActivity extends ActionBarActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float lastAcc = 0.0f;
    private float acceleration = 0.0f;
    private float totAcc = 0.0f;
    private boolean onEvent = false;

    private long timeElapsed;
    private long timeElapsedMills;

    private Integer levelScore;

    private TextView timerTv;
    public static CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accellerometer_game);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastAcc = SensorManager.GRAVITY_EARTH;
        acceleration =SensorManager.GRAVITY_EARTH;

        timerTv = (TextView)findViewById(R.id.acc_activity_time_elapsed_tv);

        levelScore = 0;

        countDownTimer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTv.setText("Tempo Residuo: " + millisUntilFinished/1000 + " secondi");
                timeElapsed = millisUntilFinished/1000;
                timeElapsedMills = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                Log.i("ACCELLEROMETER_ACT","Tempo Scaduto");
                DialogFragment dialogFragment = new TimeOutDialog();
                dialogFragment.show(getFragmentManager(),"TIME_OUT_DIAL");
            }
        };
    }

    @Override
    protected void onStart(){
        super.onStart();
        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(this);
        Tracker tracker = googleAnalytics.newTracker(R.xml.analytic);
        tracker.enableAdvertisingIdCollection(true);
        tracker.setScreenName("com.giocodestrezza.gioco.activity.AccelerometerGameActivity");
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }


    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        countDownTimer.start();

    }

    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
        countDownTimer.cancel();
    }

    public void onBackPressed(){

        DialogFragment dialogFragment = BackButtonDialog.newInstance(this.getClass());
        dialogFragment.show(getFragmentManager(),"BACK_BTN_DIAL");
    }

    @Override
    public void onAccuracyChanged(Sensor s,int i){

    }

    @Override
    public void onSensorChanged(SensorEvent event){

        if(!onEvent){
            float x  = event.values[0];
            float y  = event.values[1];
            float z  = event.values[2];

            lastAcc = acceleration;
            acceleration = x*x + y*y + z*z;
            float  diff =  acceleration - lastAcc;
            totAcc = diff*acceleration;

            if(totAcc>150000){
                onEvent = true;
                countDownTimer.cancel();
                //levelScore = calcScore(timeElapsed);
                levelScore = Util.scoreCalc(timeElapsed);

                UserData.accelerometerGameScore = levelScore;
                UserData.addScore(levelScore);

                DialogFragment dialogFragment = WinDialog.newInstance(CompassGameActivity.class,levelScore);
                dialogFragment.setCancelable(false);
                dialogFragment.show(getFragmentManager(),"WIN_DIAL");



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
        getMenuInflater().inflate(R.menu.menu_accellerometer_game, menu);
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
