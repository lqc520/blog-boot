package cn.lqcnb.blog.utils;

import android.graphics.Bitmap;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class NetUtils {
    private static final String TAG ="NetUtils" ;
    public static Bitmap bitmap=null;
    public static String data=null;
    public static Bitmap getBitmap(String url)
    {

        OkHttpUtils.get().url(url).build().execute(new BitmapCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e(TAG, "onError: Bitmap网页请求错误"+e.getMessage());
            }

            @Override
            public void onResponse(Bitmap response) {
                bitmap=response;
            }
        });
        return bitmap;
    }
    public static String getString(String url)
    {

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e(TAG, "onError: String网页请求错误"+e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                data=response;
            }
        });
        return data;
    }


}
