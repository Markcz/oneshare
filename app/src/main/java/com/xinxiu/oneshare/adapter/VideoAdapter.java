package com.xinxiu.oneshare.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.xinxiu.oneshare.R;
import com.xinxiu.oneshare.model.VideoModel;
import com.xinxiu.oneshare.utils.GlideRoundTransform;

import java.util.ArrayList;

/**
 * Created by chenzhen on 2018/1/8.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<VideoModel> models;

    public VideoAdapter(Context context, ArrayList<VideoModel> mVideoModels) {
        mContext = context;
        models = mVideoModels;
    }

    private ImageAdapter.OnItemViewClickListener onItemViewClickListener;

    public ImageAdapter.OnItemViewClickListener getOnItemViewClickListener() {
        return onItemViewClickListener;
    }

    public void setOnItemViewClickListener(ImageAdapter.OnItemViewClickListener onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.multi_image_recycler_view_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VideoAdapter.ViewHolder holder, final int position) {
        Glide.with(mContext)
                .load(models.get(position).getImg_data())
                .thumbnail(0.5f)
                .transform(new CenterCrop(mContext), new GlideRoundTransform(mContext))
                .into(holder.iv_multi_thumb);
        long time = models.get(position).getImg_date_modify() * 1000;
        long size = models.get(position).getImg_size();
        holder.tv_multi_date.setText(DateUtils.formatDateTime(mContext, time, DateUtils.FORMAT_SHOW_DATE));
        holder.tv_multi_size.setText(Formatter.formatShortFileSize(mContext, size));
        holder.iv_multi_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getOnItemViewClickListener() != null){
                    getOnItemViewClickListener().onShareClick(view,position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_multi_thumb, iv_multi_share;
        public TextView tv_multi_date, tv_multi_size;
        public CheckBox multi_recycler_view_item_check_box;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_multi_thumb = itemView.findViewById(R.id.iv_multi_thumb);
            iv_multi_share = itemView.findViewById(R.id.iv_multi_share);
            tv_multi_date = itemView.findViewById(R.id.tv_multi_date);
            tv_multi_size = itemView.findViewById(R.id.tv_multi_size);
            multi_recycler_view_item_check_box = itemView.findViewById(R.id.multi_recycler_view_item_check_box);
        }

    }


    public interface OnItemViewClickListener {
        void onItemViewClick(View view, int position);

        void onItemViewLongClick(View view, int position);

        void onCheckedChangeTitle(int size);

        void onShareClick(View view, int position);
    }
}
