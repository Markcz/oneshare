package com.xinxiu.oneshare.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xinxiu.oneshare.R;
import com.xinxiu.oneshare.config.Config;
import com.xinxiu.oneshare.fragment.ImageFragment;
import com.xinxiu.oneshare.fragment.MeFragment;
import com.xinxiu.oneshare.fragment.VideoFragment;

public class MainActivity extends AppCompatActivity {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //图片
                    setFragment(new ImageFragment(),Config.TAG_IMAGE);
                    return true;
                case R.id.navigation_dashboard:
                    //视频
                    setFragment(new VideoFragment(),Config.TAG_VIDEO);
                    return true;
                case R.id.navigation_notifications:
                    //我的
                    setFragment(new MeFragment(),Config.TAG_ME);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setFragment(new ImageFragment(),Config.TAG_IMAGE);
    }

    private void setFragment(Fragment fragment,int tag){
        Bundle args = new Bundle();
        args.putInt(Config.TAG,tag);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content,fragment);
        transaction.commitNow();
    }



}
