package com.xinxiu.oneshare.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.xinxiu.oneshare.R;
import com.xinxiu.oneshare.activity.PreviewActivity;
import com.xinxiu.oneshare.adapter.ImageAdapter;
import com.xinxiu.oneshare.config.Config;
import com.xinxiu.oneshare.model.ImageModel;
import com.xinxiu.oneshare.utils.MainSpacesItemDecoration;
import com.xinxiu.oneshare.utils.ShareUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenzhen on 2018/1/4.
 */

public class ImageFragment extends Fragment {

    private ArrayList<ImageModel> mImageModels;
    private ImageAdapter mImageAdapter;


    private RecyclerView recyclerView;
    private ActionMode mActionMode;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 显示菜单
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        MainSpacesItemDecoration decoration = new MainSpacesItemDecoration(10);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(layoutManager);
        getActivity().getSupportLoaderManager().restartLoader(Config.ID_ALL, null, mLoaderCallback);
        return view;
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {

        private final String[] IMAGE_COLUMNS = {

                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.DATE_ADDED
        };


        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            CursorLoader loader = null;
            if (id == Config.ID_ALL) {
                String selection = IMAGE_COLUMNS[3] + ">0 AND " + IMAGE_COLUMNS[4] + "=? OR " + IMAGE_COLUMNS[4] + "=? OR " + IMAGE_COLUMNS[4] + "=?";
                String[] selectionArgs = new String[]{"image/jpeg", "image/png", "image/jpg"};
                loader = new CursorLoader(
                        getActivity(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_COLUMNS,
                        selection,
                        selectionArgs,
                        IMAGE_COLUMNS[5] + " DESC"
                );
            } else if (id == Config.ID_WX) {
                /*String selection = IMAGE_COLUMNS[2] + " LIKE ? AND " + IMAGE_COLUMNS[3] + ">0 AND " + IMAGE_COLUMNS[4] + "=? OR " + IMAGE_COLUMNS[4] + "=? OR " + IMAGE_COLUMNS[4] + "=?";
                String [] selectionArgs = new String[]{"'%" + Config.WX_SAVE_DIR() + "%'","image/jpeg","image/png","image/jpg"};*/

                String selection = IMAGE_COLUMNS[2] + " LIKE ? AND " + IMAGE_COLUMNS[3] + ">0";
                String[] selectionArgs = new String[]{"%" + Config.WX_SAVE_DIR() + "%"};
                loader = new CursorLoader(
                        getActivity(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_COLUMNS,
                        selection,
                        selectionArgs,
                        IMAGE_COLUMNS[5] + " DESC"
                );
            }
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            mImageModels = new ArrayList<>();
            while (data.moveToNext()) {
                ImageModel model = new ImageModel();
                model.setImg_name(data.getString(data.getColumnIndexOrThrow(IMAGE_COLUMNS[1])));
                model.setImg_data(data.getString(data.getColumnIndexOrThrow(IMAGE_COLUMNS[2])));
                model.setImg_size(data.getLong(data.getColumnIndexOrThrow(IMAGE_COLUMNS[3])));
                model.setImg_modify(data.getLong(data.getColumnIndexOrThrow(IMAGE_COLUMNS[5])));
                mImageModels.add(model);
            }
            mImageAdapter = new ImageAdapter(getActivity(), mImageModels);

            mImageAdapter.setOnItemViewClickListener(new ImageAdapter.OnItemViewClickListener() {
                @Override
                public void onItemViewClick(View view, int position) {
                    Intent intent = new Intent(getActivity(), PreviewActivity.class);
                    intent.putExtra("path", mImageModels.get(position).getImg_data());
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view, "PHOTO");
                        startActivity(intent, optionsCompat.toBundle());
                    } else {
                        startActivity(intent);
                    }

                }

                @Override
                public void onItemViewLongClick(View view, int position) {
                    // TODO: 2018/1/6  长按 选择多张图片
                    ((AppCompatActivity) getActivity()).startSupportActionMode(mCallBack);

                }

                @Override
                public void onShareClick(View view, int position) {

                    // TODO: 2018/1/6 分享
                    showSharePopupWindow(position);
                    //ShareUtils.shareImageToQQQzone(getActivity(),mImageModels.get(position).getImg_data());

                }

                @Override
                public void onCheckedChangeTitle(int size) {
                    if (mActionMode != null){
                        mActionMode.setTitle(size + "/9");
                    }
                }
            });
            recyclerView.setAdapter(mImageAdapter);

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };


    private void showSharePopupWindow(int position) {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.share_popup_window, null);
        GridView gridView = contentView.findViewById(R.id.grid_view);
        recyclerView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    if (mPopupWindow != null && mPopupWindow.isShowing()){
                        mPopupWindow.dismiss();
                        return true;
                    }
                }
                return false;
            }
        });
        setGridView(gridView, position);
        int W = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getActivity().getResources().getDisplayMetrics());
        int H = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 240, getActivity().getResources().getDisplayMetrics());
        mPopupWindow = new PopupWindow(contentView, W, H);
        mPopupWindow.setFocusable(false);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }

    private void setGridView(GridView gridView, final int position) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            mapList.add(map);
        }
        String[] from = new String[]{"image", "text"};
        int[] to = new int[]{R.id.icon_share, R.id.icon_name};
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), mapList, R.layout.grid_view_item, from, to);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        ShareUtils.shareImageToWXFriend(getActivity(), mImageModels.get(position).getImg_data());
                        mPopupWindow.dismiss();
                        break;
                    case 1:
                        ShareUtils.shareImageToWXTimeLine(getActivity(), mImageModels.get(position).getImg_data());
                        mPopupWindow.dismiss();
                        break;
                    case 2:
                        ShareUtils.shareImageToWXFavorite(getActivity(), mImageModels.get(position).getImg_data(), ShareUtils.TAG_IMAGE);
                        mPopupWindow.dismiss();
                        break;
                    case 3:
                        ShareUtils.shareImageToQQ(getActivity(), mImageModels.get(position).getImg_data());
                        mPopupWindow.dismiss();
                        break;
                    case 4:
                        ShareUtils.shareImageToWeibo(getActivity(), mImageModels.get(position).getImg_data());
                        mPopupWindow.dismiss();
                        break;
                }
            }
        });
    }

    private PopupWindow mPopupWindow;
    private final String[] iconName = new String[]{
            "微信", "朋友圈", "微信收藏", "QQ", "微博"
    };
    private final int[] icon = new int[]{
            R.drawable.ic_weixin,
            R.drawable.ic_time_line,
            R.drawable.ic_wx_favorate,
            R.drawable.ic_qq,
            R.drawable.ic_weibo
    };


    private MultiSelectActionModeCallBack mCallBack = new MultiSelectActionModeCallBack() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            super.onCreateActionMode(mode, menu);
            getActivity().getMenuInflater().inflate(R.menu.menu_action_mode, menu);
            mActionMode = mode;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            // TODO: 2018/1/6 设置ActionMode 标题
            mode.setTitle("0/9");
            mImageAdapter.isSelectable = true;
            return super.onPrepareActionMode(mode, menu);
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            super.onDestroyActionMode(mode);
            mImageAdapter.isSelectable = false;
            mImageAdapter.notifyDataSetChanged();

        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.multi_share) {
                // TODO: 2018/1/6 多图片分享
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();

                mode.finish();
                mImageAdapter.isSelectable = false;
                mImageAdapter.notifyDataSetChanged();


                ArrayList<String> paths = new ArrayList<>();
                for (ImageModel model : mImageAdapter.selectedModels) {
                    paths.add(model.getImg_data());
                }

                ShareUtils.shareMoreImageToWXTimeLine(getActivity(),paths,"哈");
            }
            return false;
        }
    };


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_image, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.show_all) {
            getActivity().getSupportLoaderManager().restartLoader(Config.ID_ALL, null, mLoaderCallback);
        }
        if (item.getItemId() == R.id.show_wx) {
            getActivity().getSupportLoaderManager().restartLoader(Config.ID_WX, null, mLoaderCallback);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mActionMode != null){
            mActionMode.finish();
        }
    }
}
