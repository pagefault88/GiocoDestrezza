package com.giocodestrezza.gioco.activity;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.giocodestrezza.R;
import com.giocodestrezza.dbUtil.ClassifierContract;
import com.giocodestrezza.dbUtil.DbManager;
import com.giocodestrezza.gioco.UserData;
import com.giocodestrezza.gioco.dialog.BackButtonDialog;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class RecapActivity extends ActionBarActivity {

    private TextView name_field,accScore_field,compScore_field,proxScore_field,totScore_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recap);

        name_field = (TextView)findViewById(R.id.name_field_recap);
        accScore_field = (TextView)findViewById(R.id.acc_score_recap);
        compScore_field = (TextView)findViewById(R.id.comp_score_recap);
        proxScore_field = (TextView)findViewById(R.id.prox_score_recap);
        totScore_field = (TextView)findViewById(R.id.tot_score_recap);

        name_field.setText(UserData.username);
        accScore_field.setText(UserData.accelerometerGameScore.toString());
        compScore_field.setText(UserData.compassGameScore.toString());
        proxScore_field.setText(UserData.proximityGameScore.toString());
        totScore_field.setText(UserData.score.toString());
    }

    @Override
    protected void onStart(){
        super.onStart();
        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(this);
        Tracker tracker = googleAnalytics.newTracker(R.xml.analytic);
        tracker.enableAdvertisingIdCollection(true);
        tracker.setScreenName("com.giocodestrezza.gioco.activity.RecapActivity");
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }

    public void onBackPressed(){
        DialogFragment dialogFragment = BackButtonDialog.newInstance(this.getClass());
        dialogFragment.show(getFragmentManager(),"BACK_BUTTON_PROXIMITY");
    }


    public void clickBtn(View v){
        insertDB();
        UserData.deleteData();
        finish();
        //Intent intent = new Intent(RecapActivity.this, MainActivity.class);
        //startActivity(intent);
    }


    private long insertDB(){

        long newId;
        DbManager dbManager = new DbManager(getBaseContext());
        SQLiteDatabase db = dbManager.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ClassifierContract.ClassifierEntry.COLUMN_NAME_USER,UserData.username);
        values.put(ClassifierContract.ClassifierEntry.COLUMN_NAME_SCORE,UserData.score);

        newId = db.insert(ClassifierContract.ClassifierEntry.TABLE_NAME,
                ClassifierContract.ClassifierEntry.COLUMN_NAME_ID,
                values);

        return newId;
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recap, menu);
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
