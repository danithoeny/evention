package com.leoneer.evention;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrefConfig {

    private static final String PLIST_KEY = "plist_key";
    private static final String TLIST_KEY = "tlist_key";
    private static final String ELIST_KEY = "elist_key";

    public static void writePlayerListInPref(Context context, ArrayList<Player> pList){
        Gson pgson = new Gson();
        String pjsonString = pgson.toJson(pList);

        SharedPreferences ppref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = ppref.edit();
        editor.putString(PLIST_KEY,pjsonString);
        editor.apply();
    }

    public static ArrayList<Player> readPlayerListFromPref (Context context){
        SharedPreferences ppref = PreferenceManager.getDefaultSharedPreferences(context);
        String pjsonString = ppref.getString(PLIST_KEY,"");
        Gson pgson = new Gson();
        Type ptype = new TypeToken<ArrayList<Player>>() {}.getType();
        ArrayList<Player> pList = pgson.fromJson(pjsonString, ptype);
        return pList;
    }

   public static void writeTeamListInPref(Context context, ArrayList<Team> tList){
        Gson gson = new Gson();
        String jsonString = gson.toJson(tList);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(TLIST_KEY,jsonString);
        editor.apply();
    }

    public static ArrayList<Team> readTeamListFromPref (Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(TLIST_KEY,"");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Team>>() {}.getType();
        ArrayList<Team> tList = gson.fromJson(jsonString, type);
        return tList;
    }

    public static void writeEventListInPref(Context context, ArrayList<Event> eList){
        Gson egson = new Gson();
        String ejsonString = egson.toJson(eList);

        SharedPreferences epref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = epref.edit();
        editor.putString(ELIST_KEY,ejsonString);
        editor.apply();
    }

    public static ArrayList<Event> readEventListFromPref (Context context){
        SharedPreferences epref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = epref.getString(ELIST_KEY,"");
        Gson egson = new Gson();
        Type etype = new TypeToken<ArrayList<Event>>() {}.getType();
        ArrayList<Event> eList = egson.fromJson(jsonString, etype);
        return eList;
    }

}
