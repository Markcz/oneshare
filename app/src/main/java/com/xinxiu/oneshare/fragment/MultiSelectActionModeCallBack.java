package com.xinxiu.oneshare.fragment;

import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by chenzhen on 2018/1/6.
 */

public class MultiSelectActionModeCallBack implements ActionMode.Callback{
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}