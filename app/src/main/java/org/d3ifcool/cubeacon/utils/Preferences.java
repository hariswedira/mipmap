package org.d3ifcool.cubeacon.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public static void save(Context ctx, String name, String value){
        SharedPreferences s = ctx.getSharedPreferences("mipmap",Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = s.edit();
        edt.putString(name,value);
        edt.apply();
    }

    public static String read(Context ctx, String name, String defaultValue){
        SharedPreferences s = ctx.getSharedPreferences("mipmap",Context.MODE_PRIVATE);
        return s.getString(name,defaultValue);
    }

}
