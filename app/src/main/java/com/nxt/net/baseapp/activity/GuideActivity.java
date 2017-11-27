package com.nxt.net.baseapp.activity;import android.os.Bundle;import com.cauc.mavenj.materialintro.app.IntroActivity;import com.cauc.mavenj.materialintro.slide.SimpleSlide;import com.nxt.net.baseapp.R;/** * @author Jan Maven on 2017/9/26. * Email:cyjiang_11@163.com * Description: */public class GuideActivity extends IntroActivity {    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setButtonBackVisible(false);        setButtonNextVisible(false);        setButtonCtaVisible(true);        setButtonCtaTintMode(BUTTON_CTA_TINT_MODE_BACKGROUND);        addSlide(new SimpleSlide.Builder()                .title(R.string.title_canteen_intro1)                .description(R.string.description_canteen_intro1)                .image(R.drawable.art_canteen_intro1)                .background(R.color.color_canteen)                .backgroundDark(R.color.color_dark_canteen)                .layout(R.layout.slide_canteen)                .build());        addSlide(new SimpleSlide.Builder()                .title(R.string.title_canteen_intro2)                .description(R.string.description_canteen_intro2)                .image(R.drawable.s_0_1)                .background(R.color.color_canteen)                .backgroundDark(R.color.color_dark_canteen)                .layout(R.layout.slide_canteen)                .build());        addSlide(new SimpleSlide.Builder()                .title(R.string.title_canteen_intro3)                .description(R.string.description_canteen_intro3)                .image(R.drawable.art_canteen_intro3)                .background(R.color.color_canteen)                .backgroundDark(R.color.color_dark_canteen)                .layout(R.layout.slide_canteen)                .build());//        autoplay(2500, INFINITE);    }}