package com.bilibili.magicasakura.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * @author xyczero617@gmail.com
 */

interface DrawableInflateDelegate {
    /**
     * inflateDrawable
     * @param context
     * @param parser
     * @param attrs
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    Drawable inflateDrawable(Context context, XmlPullParser parser, AttributeSet attrs) throws IOException, XmlPullParserException;
}
