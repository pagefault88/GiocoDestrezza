package com.giocodestrezza.training.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import com.giocodestrezza.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class ChoseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose);
    }


    @Override
    protected void onStart(){
        super.onStart();
        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(this);
        Tracker tracker = googleAnalytics.newTracker(R.xml.analytic);
        tracker.enableAdvertisingIdCollection(true);
        tracker.setScreenName("com.giocodestrezza.training.activity.ChoseActivity");
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }

    public void onAccelerometerBtnClick(View v){
        Intent intent = new Intent(ChoseActivity.this,AccelerometerTrainingActivity.class);
        startActivity(intent);
    }
    public void onCompassBtnClick(View v){
        Intent intent = new Intent(ChoseActivity.this,CompassTrainingActivity.class);
        startActivity(intent);
    }

    public void onProximityBtnClick(View v){
        Intent intent = new Intent(ChoseActivity.this,PrroximityTrainingActivity.class);
        startActivity(intent);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chose, menu);
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
