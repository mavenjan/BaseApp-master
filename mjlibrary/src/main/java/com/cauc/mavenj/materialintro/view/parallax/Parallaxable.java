package com.cauc.mavenj.materialintro.view.parallax;

import android.support.annotation.FloatRange;

/**
 * @author Maven Jan
 */

public interface Parallaxable {
    /**
     * setOffset
     * @param offset offset
     */
    void setOffset(@FloatRange(from = -1.0, to = 1.0) float offset);
}
