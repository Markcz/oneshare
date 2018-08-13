package com.xinxiu.oneshare.config;

import android.os.Environment;

import java.io.File;

/**
 * Created by chenzhen on 2018/1/4.
 */

public class Config {


    public final static int ID_ALL = 0;
    public final static int ID_WX = 1;



    public final static String TAG = "TAG";
    public final static int TAG_IMAGE = 0;
    public final static int TAG_VIDEO = 1;
    public final static int TAG_ME = 2;



    // 微信 图片视频保存目录
    public static String WX_SAVE_DIR(){
        if (isSdCardExist()){}{
            String wxdir = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "tencent" + File.separator + "MicroMsg" + File.separator + "WeiXin";
            File file = new File(wxdir);
            if (!file.exists()){
                file.mkdirs();
            }
            return file.getAbsolutePath();
        }
    }

    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }





}
