package com.giocodestrezza.main;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;



import com.giocodestrezza.R;
import com.giocodestrezza.classifica.activity.ClassificaActivity;
import com.giocodestrezza.gioco.dialog.NameRequestDialog;
import com.giocodestrezza.training.activity.ChoseActivity;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart(){
        super.onStart();
        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(this);
        Tracker tracker = googleAnalytics.newTracker(R.xml.analytic);
        tracker.enableAdvertisingIdCollection(true);
        tracker.setScreenName("com.giocodestrezza.main.MainActivity");
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }

    public void clickBtnStart(View v){
        DialogFragment dialog  = new NameRequestDialog();
        dialog.show(getFragmentManager(),"NAME_INS");
    }

    public void clickBtnTraining(View v){
        Intent intent = new Intent(MainActivity.this, ChoseActivity.class);
        startActivity(intent);
    }

    public void clickBtnClassifica(View v){
        Intent intent= new Intent(MainActivity.this, ClassificaActivity.class);
        startActivity(intent);
    }

    public  void onBackPressed(){
        DialogFragment dialogFragment = new ExitDialog();
        dialogFragment.show(getFragmentManager(),"EXIT_DIAL");
    }
/*
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    */
}
