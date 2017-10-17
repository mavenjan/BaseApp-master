package com.cauc.mavenj.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cauc.mavenj.R;

/**
 * @author pantrif on 2016/4/28.
 * GitUrl: https://github.com/pantrif/EasySplashScreen.git
 * Description: 可定制化的闪屏页
 */
public class EasySplashScreen {

    Activity mActivity;
    LayoutInflater mInflater;
    ImageView ivTitle;
    ImageView ivLogo;
    ImageView ivDesc;
    TextView tvHeader;
    TextView tvFooter;
    TextView tvBeforeLogo;
    TextView tvAfterLogo;
    String textHeader = null;
    String textFooter = null;
    String textBeforeLogo = null;
    String textAfterLogo = null;
    RelativeLayout rlSplashWrapper;
    Bundle bundle = null;
    private View mView;
    private int splashBackgroundColor = 0;
    private int splashBackgroundResource = 0;
    private int mTitle = 0;
    private int mLogo = 0;
    private int mDesc = 0;
    private Class<?> targetActivity = null;
    /** The time before launch target Activity - by default 2 seconds */
    private int splashTimeOut = 2000;

    public EasySplashScreen(Activity activity) {
        this.mActivity = activity;
        this.mInflater = LayoutInflater.from(activity);
        this.mView = mInflater.inflate(R.layout.mj_splash_easy, null);
        this.rlSplashWrapper = (RelativeLayout) mView.findViewById(R.id.splash_wrapper_rl);
    }

    public EasySplashScreen withFullScreen() {
        mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return this;
    }

    public EasySplashScreen withTargetActivity(Class<?> tAct) {
        this.targetActivity = tAct;
        return this;
    }

    public EasySplashScreen withSplashTimeOut(int timout) {
        this.splashTimeOut = timout;
        return this;
    }

    public EasySplashScreen withBundleExtras(Bundle bundle) {
        this.bundle = bundle;
        return this;
    }

    public EasySplashScreen withBackgroundColor(int color) {
        this.splashBackgroundColor = color;
        rlSplashWrapper.setBackgroundColor(splashBackgroundColor);
        return this;
    }

    public EasySplashScreen withBackgroundResource(int resource) {
        this.splashBackgroundResource = resource;
        rlSplashWrapper.setBackgroundResource(splashBackgroundResource);
        return this;
    }

    public EasySplashScreen withTitle(int title) {
        this.mTitle = title;
        ivTitle = (ImageView) mView.findViewById(R.id.iv_title);
        ivTitle.setImageResource(mTitle);
        return this;
    }

    public EasySplashScreen withLogo(int logo) {
        this.mLogo = logo;
        ivLogo = (ImageView) mView.findViewById(R.id.logo);
        ivLogo.setImageResource(mLogo);
        return this;
    }

    public EasySplashScreen withDesc(int desc) {
        this.mDesc = desc;
        ivDesc = (ImageView) mView.findViewById(R.id.iv_dc);
        ivDesc.setImageResource(mDesc);
        return this;
    }

    public EasySplashScreen withHeaderText(String text) {
        this.textHeader = text;
        tvHeader = (TextView) mView.findViewById(R.id.header_tv);
        tvHeader.setText(text);
        return this;
    }

    public EasySplashScreen withFooterText(String text) {
        this.textFooter = text;
        tvFooter = (TextView) mView.findViewById(R.id.footer_tv);
        tvFooter.setText(text);
        return this;
    }

    public EasySplashScreen withBeforeLogoText(String text) {
        this.textBeforeLogo = text;
        tvBeforeLogo = (TextView) mView.findViewById(R.id.before_logo_tv);
        tvBeforeLogo.setText(text);
        return this;
    }

    public EasySplashScreen withAfterLogoText(String text) {
        this.textAfterLogo = text;
        tvAfterLogo = (TextView) mView.findViewById(R.id.after_logo_tv);
        tvAfterLogo.setText(text);
        return this;
    }

    public TextView getHeaderTextView() {
        return tvHeader;
    }

    public ImageView getTitle() {
        return ivTitle;
    }

    public ImageView getLogo() {
        return ivLogo;
    }

    public ImageView getDesc() {
        return ivDesc;
    }

    public TextView getBeforeLogoTextView() {
        return tvBeforeLogo;
    }

    public TextView getAfterLogoTextView() {
        return tvAfterLogo;
    }

    public TextView getFooterTextView() {
        return tvFooter;
    }

    public View create() {
        setUpHandler();
        return mView;
    }


    private void setUpHandler() {
        if (targetActivity != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(mActivity, targetActivity);
                    if (bundle != null) {
                        i.putExtras(bundle);
                    }
                    mActivity.startActivity(i);
                    // close splash
                    mActivity.finish();
                }
            }, splashTimeOut);
        }
    }

}
