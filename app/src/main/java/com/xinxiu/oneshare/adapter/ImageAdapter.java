package com.xinxiu.oneshare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.xinxiu.oneshare.R;
import com.xinxiu.oneshare.model.ImageModel;
import com.xinxiu.oneshare.utils.GlideRoundTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenzhen on 2018/1/4.
 */

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public ArrayList<ImageModel> mImageModels;
    public Context mContext;
    public boolean isSelectable = false;
    public ArrayList<ImageModel> selectedModels;
    public Map<Integer, Boolean> map ;

    public final int IS_SELECTED_MAX = 9;


    private OnItemViewClickListener onItemViewClickListener;

    public OnItemViewClickListener getOnItemViewClickListener() {
        return onItemViewClickListener;
    }

    public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }


    public ImageAdapter(Context mContext, ArrayList<ImageModel> mImageModels) {
        this.mContext = mContext;
        this.mImageModels = mImageModels;
        initMap();
    }

    private void initMap() {
        map = new HashMap<>();
        for (int i = 0; i < mImageModels.size(); i++) {
            map.put(i, false);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.multi_image_recycler_view_item, parent, false);
        return new MultiViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        MultiViewHolder multiViewHolder = (MultiViewHolder) holder;
        Glide.with(mContext)
                .load(mImageModels.get(position).getImg_data())
                .transform(new CenterCrop(mContext), new GlideRoundTransform(mContext))
                .thumbnail(0.1f)
                .into(multiViewHolder.iv_multi_thumb);

        multiViewHolder.iv_multi_thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isSelectable) {

                } else {
                    if (getOnItemViewClickListener() != null) {
                        getOnItemViewClickListener().onItemViewClick(view, position);
                    }
                }

            }
        });
        multiViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (getOnItemViewClickListener() != null) {
                    getOnItemViewClickListener().onItemViewLongClick(view, position);
                    isSelectable = true;
                    notifyDataSetChanged();
                    selectedModels = new ArrayList<>();
                    initMap();
                }
                return true;
            }
        });
        long time = mImageModels.get(position).getImg_date_modify() * 1000;
        long size = mImageModels.get(position).getImg_size();
        multiViewHolder.tv_multi_date.setText(DateUtils.formatDateTime(mContext, time, DateUtils.FORMAT_SHOW_DATE));
        multiViewHolder.tv_multi_size.setText(Formatter.formatShortFileSize(mContext, size));
        multiViewHolder.iv_multi_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getOnItemViewClickListener() != null) {
                    getOnItemViewClickListener().onShareClick(view, position);
                }
            }
        });
        multiViewHolder.multi_recycler_view_item_check_box.setVisibility(isSelectable ? View.VISIBLE : View.GONE);
        multiViewHolder.multi_recycler_view_item_check_box.setOnCheckedChangeListener(null);//先设置一次CheckBox的选中监听器，传入参数null
        multiViewHolder.multi_recycler_view_item_check_box.setChecked(map.get(position));//用数组中的值设置CheckBox的选中状态

        //再设置一次CheckBox的选中监听器，当CheckBox的选中状态发生改变时，把改变后的状态储存在数组中
        multiViewHolder.multi_recycler_view_item_check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                CheckBox checkBox = (CheckBox) compoundButton;
                if (checkBox.isChecked()) {
                    selectedModels.add(mImageModels.get(position));
                } else {
                    if (selectedModels.contains(mImageModels.get(position))) {
                        selectedModels.remove(mImageModels.get(position));
                    }
                }

                Log.e("TAG-p", selectedModels.size() + "");


                map.remove(position);
                map.put(position, b);

                if (checkIsMax()) {
                    selectedModels.remove(selectedModels.size() - 1);
                    Toast.makeText(mContext, "限制9张", Toast.LENGTH_SHORT).show();
                    Log.e("TAG-p", selectedModels.size() + "");
                    checkBox.setChecked(false);
                }

                Log.e("TAG-map", map.size() + "");

                if (getOnItemViewClickListener() != null){
                    getOnItemViewClickListener().onCheckedChangeTitle(selectedModels.size());
                }

            }
        });
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_multi_thumb, iv_multi_share;
        public TextView tv_multi_date, tv_multi_size;
        public CheckBox multi_recycler_view_item_check_box;

        public MultiViewHolder(View itemView) {
            super(itemView);
            iv_multi_thumb = itemView.findViewById(R.id.iv_multi_thumb);
            iv_multi_share = itemView.findViewById(R.id.iv_multi_share);
            tv_multi_date = itemView.findViewById(R.id.tv_multi_date);
            tv_multi_size = itemView.findViewById(R.id.tv_multi_size);
            multi_recycler_view_item_check_box = itemView.findViewById(R.id.multi_recycler_view_item_check_box);
        }


    }


    @Override
    public int getItemCount() {
        return mImageModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    private boolean checkIsMax() {
        return selectedModels.size() > IS_SELECTED_MAX;
    }


    public interface OnItemViewClickListener {
        void onItemViewClick(View view, int position);

        void onItemViewLongClick(View view, int position);

        void onCheckedChangeTitle(int size);

        void onShareClick(View view, int position);
    }
}
