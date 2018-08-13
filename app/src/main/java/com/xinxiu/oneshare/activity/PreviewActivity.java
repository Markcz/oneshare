package com.xinxiu.oneshare.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import com.xinxiu.oneshare.R;
import com.xinxiu.oneshare.adapter.ViewPagerAdapter;
import com.xinxiu.oneshare.utils.ShareUtils;

import java.util.ArrayList;


public class PreviewActivity extends AppCompatActivity {

    private ImageView iv_preview;
    private ViewPager view_pager;

    private String path;
    public ViewPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        initViews();
        initData();
    }



    private void initViews() {

        iv_preview = (ImageView) findViewById(R.id.iv_preview);
        view_pager = (ViewPager) findViewById(R.id.view_pager);

    }
    private void initData() {

         /*Glide.with(this)
                .load(getIntent().getStringExtra("path"))
                .into(iv_preview);*/

        path = getIntent().getStringExtra("path");
        iv_preview.setImageBitmap(getCalculateBitmap(path));



    }


    private Bitmap getCalculateBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = calculateSampleSize(options);
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    private int calculateSampleSize(BitmapFactory.Options options) {
        int inSampleSize = 4;

        if (inSampleSize <= 0 ){
            inSampleSize = 1;
        }

        options.inJustDecodeBounds = false;
        return inSampleSize;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_preview,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.preview_share){
            //ShareUtils.shareToQQ(this,path);
            //ShareUtils.shareImageToWXFriend(this,path);
            //ShareUtils.shareToWXTimeLine(this,path);
            /*
            ArrayList<String> paths = new ArrayList<>();
            for (int i = 0 ; i < 9; i++ ){
                paths.add(path);
            }

            ShareUtils.shareToWXTimeLineForMoreImage(this,paths,"哈");*/

            //ShareUtils.shareToWXFavorite(this,path,ShareUtils.TAG_IMAGE);
            //ShareUtils.shareToQQQzone(this,path);
            //ShareUtils.shareToQQQzone(this,"");

            ArrayList<String> paths = new ArrayList<>();
            for (int i = 0 ; i < 9; i++ ){
                paths.add(path);
            }
            ShareUtils.shareMoreImageToWXTimeLine(this,paths,"哈");

        }
        return super.onOptionsItemSelected(item);
    }
}
