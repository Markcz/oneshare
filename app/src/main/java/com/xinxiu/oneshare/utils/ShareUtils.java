package com.xinxiu.oneshare.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by chenzhen on 2018/1/4.
 */

public class ShareUtils {



    /**************************  所有  ***************************/

    /**
     * 文字分享
     * @param context
     */
    public static void shareText(Context context,String text){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "标题");
        intent.putExtra(Intent.EXTRA_TEXT, "描述信息" + "这里你可以追加一个url连接");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "分享到"));

    }

    public static void shareImage(Context context,String path){

    }




    /**************************  WX  ***************************/


    /**
     * 分享图片到微信好友
     * @param context
     * @param path 图片路径
     */
    public static void shareImageToWXFriend(Context context, String path){
        Uri imageUri = Uri.fromFile(new File(path));
        Intent shareIntent = new Intent();
        //发送图片给好友。
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        shareIntent.setComponent(comp);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享图片"));
    }


    public static void shareVideoToWXFriend(Context context, String path){
        Uri videoUri = Uri.fromFile(new File(path));
        Intent shareIntent = new Intent();
        //发送图片给好友。
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        shareIntent.setComponent(comp);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, videoUri);
        shareIntent.setType("video/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享视频"));
    }


    /**
     * 分享到微信朋友圈
     * @param context
     * @param path 图片路径
     */
    public static void shareImageToWXTimeLine(Context context,String path){
        Uri imageUri = Uri.fromFile(new File(path));
        Intent shareIntent = new Intent();
        //发送图片到朋友圈
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        shareIntent.setComponent(comp);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享图片"));
    }

    public static void shareVideoToWXTimeLine(Context context,String path){
        Uri imageUri = Uri.fromFile(new File(path));
        Intent shareIntent = new Intent();
        //发送视频到朋友圈
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        shareIntent.setComponent(comp);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("video/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享视频"));
    }


    /**
     * 分享多张图片到朋友圈
     * @param context
     * @param paths
     * @param title
     */
    public static void shareMoreImageToWXTimeLine(Context context, ArrayList<String> paths,String title){
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm",
                "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");
        intent.putExtra("Kdescription", title);
        ArrayList<Uri> imageUris = new ArrayList<>();
        for (String path : paths) {
            imageUris.add(Uri.fromFile(new File(path)));
        }
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        context.startActivity(intent);
    }



    //com.tencent.mm.ui.tools.AddFavoriteUI
    public static void shareImageToWXFavorite(Context context,String path,String TAG){

        Uri imageUri = Uri.fromFile(new File(path));
        Intent shareIntent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.AddFavoriteUI");
        shareIntent.setComponent(comp);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType(TAG);
        context.startActivity(Intent.createChooser(shareIntent, "WeChar Favorite"));

    }





    /**************************  QQ  ***************************/


    /**
     * 分享图片到QQ
     * @param context
     * @param path 图片路径
     */
    public static void shareImageToQQ(Context context,String path){
        Uri imageUri = Uri.fromFile(new File(path));
        Intent shareIntent = new Intent();
        //发送图片到qq
        ComponentName comp = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
        shareIntent.setComponent(comp);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享图片"));
    }

    /**
     * 分享视频到QQ
     * @param context
     * @param path
     */
    public static void shareVideoToQQ(Context context,String path){
        Uri imageUri = Uri.fromFile(new File(path));
        Intent shareIntent = new Intent();
        //发送图片到qq
        ComponentName comp = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
        shareIntent.setComponent(comp);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("video/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享视频"));
    }

    /**---------------  QQ收藏 -----------------**/


    public final static String TAG_IMAGE = "image/*";
    public final static String TAG_VIDEO = "video/*";

    public static void shareImageToQQFavorite(Context context,String path,int TAG){

    }

    public static void shareImageToQQQzone(Context context,String path){
        Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送的属性
        intent.setType("image/plain"); // 分享发送的数据类型
        intent.setPackage("com.qzone");
        Uri imageUri = Uri.fromFile(new File(path));
        intent.putExtra(Intent.EXTRA_TEXT, "jkdfaljfkl"); // 分享的内容
        context.startActivity(Intent.createChooser(intent, ""));// 目标应用选择对话框的标题;
    }











    /**************************  Weibo ***************************/
    //com.sina.weibo.composerinde.ComposerDispatchActivity


    public static void shareImageToWeibo(Context context,String path){
        Uri imageUri = Uri.fromFile(new File(path));
        Intent shareIntent = new Intent();
        //发送图片到qq
        ComponentName comp = new ComponentName("com.sina.weibo", "com.sina.weibo.composerinde.ComposerDispatchActivity");
        shareIntent.setComponent(comp);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享"));
    }


}
