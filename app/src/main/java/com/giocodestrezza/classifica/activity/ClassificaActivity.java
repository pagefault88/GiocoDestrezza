package com.giocodestrezza.classifica.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.giocodestrezza.R;
import com.giocodestrezza.dbUtil.ClassifierContract;
import com.giocodestrezza.dbUtil.DbManager;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class ClassificaActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifica);
    }

    @Override
    protected void onStart(){
        super.onStart();
        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(this);
        Tracker tracker = googleAnalytics.newTracker(R.xml.analytic);
        tracker.enableAdvertisingIdCollection(true);
        tracker.setScreenName("com.giocodestrezza.classifica.activity.ClassificaActivity");
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    protected void onResume(){
        super.onResume();
        populateList();
    }

    private Cursor readDB(){
        DbManager  dbManager = new DbManager(getBaseContext());
        SQLiteDatabase db = dbManager.getReadableDatabase();

        String[] columns = {
                ClassifierContract.ClassifierEntry.COLUMN_NAME_ID,
                ClassifierContract.ClassifierEntry.COLUMN_NAME_USER,
                ClassifierContract.ClassifierEntry.COLUMN_NAME_SCORE,
        };

        String sortOrder = ClassifierContract.ClassifierEntry.COLUMN_NAME_SCORE + " DESC";

        Cursor cursor = db.query(
                ClassifierContract.ClassifierEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder
            );
        return cursor;
    }

    private void deleteDB(){
        DbManager dbManager = new DbManager(getBaseContext());
        SQLiteDatabase db = dbManager.getWritableDatabase();
        Log.i("CLASS_ACT","Flushhh");
        db.execSQL("delete from " + ClassifierContract.ClassifierEntry.TABLE_NAME);
    }

    private void populateList(){
        Cursor cursor = readDB();
        String[] fromFieldNames =  new String[]{ClassifierContract.ClassifierEntry.COLUMN_NAME_USER, ClassifierContract.ClassifierEntry.COLUMN_NAME_SCORE};
        int toView[] = new int[]{R.id.username_row_tv,R.id.score_row_tv};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getBaseContext(),R.layout.simple_row,cursor,fromFieldNames,toView,0);
        ListView listView = (ListView)findViewById(R.id.classifica_lw);
        listView.setAdapter(simpleCursorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_classifica, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id){
            case R.id.action_flush:
                deleteDB();
                populateList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
