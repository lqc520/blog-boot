package cn.lqcnb.blog.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheUtils {
    public static void saveString(Context context,String key,String value){
        SharedPreferences sp = context.getSharedPreferences("user_info", context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }
    public static String getString(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences("user_info", context.MODE_PRIVATE);
        return sp.getString(key,"");
    }
}
