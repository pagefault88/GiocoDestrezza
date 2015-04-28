package com.giocodestrezza.dbUtil;

import android.provider.BaseColumns;

/**
 * Created by anton_000 on 02/04/2015.
 */
public class ClassifierContract {

    ClassifierContract(){

    }

    public static abstract class ClassifierEntry implements BaseColumns{

        public static final String TABLE_NAME = "classifica";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_USER = "username";
        public static final String COLUMN_NAME_SCORE = "score";
    }
}
