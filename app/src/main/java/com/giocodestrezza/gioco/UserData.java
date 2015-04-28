package com.giocodestrezza.gioco;

/**
 * Created by anton_000 on 01/04/2015.
 */
public class UserData {

    public static String username;
    public static Integer score;
    public static Integer accelerometerGameScore;
    public static Integer compassGameScore;
    public static Integer proximityGameScore;


    public static void initUserData(String u){
        username = u;
        score = 0;
        accelerometerGameScore = 0;
        compassGameScore = 0;
        proximityGameScore = 0;
    }

    public static void addScore(Integer s){
        score = score + s;
    }

    public static void deleteData(){
        username = null;
        score = null;
        accelerometerGameScore = null;
        compassGameScore = null;
        proximityGameScore = null;
    }

    
}
