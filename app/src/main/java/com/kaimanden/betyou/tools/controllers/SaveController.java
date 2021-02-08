package com.kaimanden.betyou.tools.controllers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;

public class SaveController {

    private static final String FICHERO_SHARED = "settings";

    private static SaveController instance;
    private SaveController(Context ctx){
        sharedPref = ctx.getSharedPreferences( FICHERO_SHARED, Context.MODE_PRIVATE);
    }

    private SharedPreferences sharedPref;

    public static SaveController getInstance(Context ctx){
        if (instance == null){
            instance = new SaveController(ctx);
        }
        return instance;
    }

    public void save(String key, String dato){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, dato);
        editor.commit();
    }

    public void save(String key, List dato){
        SharedPreferences.Editor editor = sharedPref.edit();

        HashSet set = Sets.newHashSet(dato);
        editor.putStringSet(key, set);
        editor.commit();
    }

    public String getString(String key){
        return sharedPref.getString(key, "");
    }

    public List getList(String key){
        return (List) sharedPref.getStringSet(key, new HashSet<>());
    }



}
