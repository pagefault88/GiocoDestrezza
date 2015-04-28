package com.giocodestrezza.dbUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by anton_000 on 02/04/2015.
 */
public class DbManager extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "classifica.db";
    private static final String SQL_CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " +ClassifierContract.ClassifierEntry.TABLE_NAME + "(" +
    ClassifierContract.ClassifierEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
    ClassifierContract.ClassifierEntry.COLUMN_NAME_USER + " TEXT NOT NULL," +
    ClassifierContract.ClassifierEntry.COLUMN_NAME_SCORE + " INTEGER NOT NULL)";

    public DbManager(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }



}
