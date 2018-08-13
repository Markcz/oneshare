package com.xinxiu.oneshare.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by chenzhen on 2017/9/29.
 */

public class MainSpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public MainSpacesItemDecoration(int space) {
        this.space=space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        /*outRect.left=space;
        outRect.right=space;
        outRect.bottom=space;*/
        outRect.top = space;
    }
}
