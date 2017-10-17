package com.nxt.net.baseapp;import android.app.Activity;import android.content.Intent;import android.graphics.Color;import android.graphics.Typeface;import android.os.Bundle;import android.view.View;import android.view.animation.Animation;import android.view.animation.TranslateAnimation;import android.widget.TextView;import com.cauc.mavenj.widget.EasySplashScreen;import com.nxt.net.baseapp.mvp.ui.activity.LoginActivity;/** * @author Jan Maven on 2017/9/26. * Email:cyjiang_11@163.com * Description: */public class SplashActivity extends Activity {    private boolean isLogin = false;    Intent intent;    private Class<?> tagetActivity = null;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        if (!isLogin) {            tagetActivity = LoginActivity.class;//            TagetActivity = LoginActivity1.class;        } else {            tagetActivity = MainActivity.class;        }        EasySplashScreen config = new EasySplashScreen(SplashActivity.this)                .withFullScreen()                .withTargetActivity(tagetActivity)                .withSplashTimeOut(2000)                .withBackgroundResource(R.drawable.splash)                .withHeaderText("")                .withBeforeLogoText("")                .withLogo(R.drawable.logo)                .withTitle(R.drawable.iv_title)                .withDesc(R.drawable.iv_desc)                .withAfterLogoText("")                .withFooterText(getString(R.string.copright));        //set your own animations        myCustomTextViewAnimation(config.getFooterTextView());        //customize all TextViews        Typeface pacificoFont = Typeface.createFromAsset(getAssets(), "Pacifico.ttf");        config.getAfterLogoTextView().setTypeface(pacificoFont);        config.getHeaderTextView().setTextColor(Color.GREEN);        config.getBeforeLogoTextView().setTextColor(Color.WHITE);        config.getAfterLogoTextView().setTextColor(Color.WHITE);        config.getFooterTextView().setTextColor(Color.WHITE);        //create the view        View easySplashScreenView = config.create();        setContentView(easySplashScreenView);    }    private void myCustomTextViewAnimation(TextView tv) {        Animation animation = new TranslateAnimation(0, 0, 480, 0);        animation.setDuration(1200);        tv.startAnimation(animation);    }}