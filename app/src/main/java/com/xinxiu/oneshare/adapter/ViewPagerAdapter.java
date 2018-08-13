package com.xinxiu.oneshare.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xinxiu.oneshare.model.ImageModel;

import java.util.ArrayList;

/**
 * Created by chenzhen on 2018/1/5.
 */

public class ViewPagerAdapter extends PagerAdapter{

    public   ArrayList<ImageModel> mModels;
    public   Context mContext;

    public ViewPagerAdapter(Context context, ArrayList<ImageModel> models) {
        this.mContext = context;
        this.mModels = models;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        Glide.with(mContext)
                .load(mModels.get(position).getImg_data())
                .thumbnail(0.5f)
                .into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public int getCount() {
        return mModels.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
