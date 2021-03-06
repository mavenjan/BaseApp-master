package com.cauc.mavenj.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Adapter基类，实际使用只需关注Model和View
 *
 * @author koneloong koneloong@gmail.com
 *         Created on 2015/8/5 20:56.
 * @version 1.0
 * @since 1.0
 */
abstract class BaseMjAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    protected List<T> dataList;

    public BaseMjAdapter(Context context, List<T> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * getView
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
