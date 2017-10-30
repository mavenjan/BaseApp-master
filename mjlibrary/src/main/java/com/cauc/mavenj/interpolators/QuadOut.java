package com.cauc.mavenj.interpolators;

import android.animation.TimeInterpolator;

/**
 * Created by danielzeller on 09.04.15.
 */
public class QuadOut implements TimeInterpolator {
    @Override
    public float getInterpolation(float t) {
        return -t*(t-2);
    }
}