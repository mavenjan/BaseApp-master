package com.cauc.mavenj.app;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;
import android.util.Log;

import com.cauc.mavenj.utils.SharePrefHelper;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONObject;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/*
 *自定义Application
 * 用于初始化各种数据以及服务
 *  */

public class MyApplication extends Application {
    private static final String EXTRA_FONT = "fonts/mini_simple.ttf";
    private static final String TAG = "MyApplication";
    private static final String PREF_NAME = "creativelocker.pref";

    public static String VERSION_NAME = "";

    private static Typeface extraFont = null;

    //记录当前栈里所有activity
    private List<Activity> activities = new ArrayList<Activity>();
    //记录需要一次性关闭的页面
    private List<Activity> activitys = new ArrayList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SharePrefHelper.getInstance(this);

    }

    private void initTypeface() {
        if (extraFont != null) {
            return;
        }
        extraFont = Typeface.createFromAsset(getAssets(), EXTRA_FONT);
    }

    public static Typeface getExtraFont() {
        return extraFont;
    }

    /**
     * 应用实例
     **/
    private static MyApplication instance;

    /**
     * 获得实例
     *
     * @return
     */
    public static MyApplication getInstance() {
        return instance;
    }

    /**
     * 新建了一个activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            this.activities.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /*
    *给临时Activitys
    * 添加activity
    * */
    public void addTemActivity(Activity activity) {
        activitys.add(activity);
    }

    public void finishTemActivity(Activity activity) {
        if (activity != null) {
            this.activitys.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /*
    * 退出指定的Activity
    * */
    public void exitActivitys() {
        for (Activity activity : activitys) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 应用退出，结束所有的activity
     */
    public void exit() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }

    public void cleanspf() {
    }
}
