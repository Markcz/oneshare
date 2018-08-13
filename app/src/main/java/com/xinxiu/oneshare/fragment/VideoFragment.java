package com.xinxiu.oneshare.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.xinxiu.oneshare.R;
import com.xinxiu.oneshare.adapter.ImageAdapter;
import com.xinxiu.oneshare.adapter.VideoAdapter;
import com.xinxiu.oneshare.config.Config;
import com.xinxiu.oneshare.model.VideoModel;
import com.xinxiu.oneshare.utils.MainSpacesItemDecoration;
import com.xinxiu.oneshare.utils.ShareUtils;
import java.util.ArrayList;

/**
 * Created by chenzhen on 2018/1/4.
 */

public class VideoFragment extends Fragment {

    private RecyclerView video_recycler_view;


    private ArrayList<VideoModel> mVideoModels;
    private VideoAdapter mVideoAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        video_recycler_view = view.findViewById(R.id.video_recycler_view);
        MainSpacesItemDecoration decoration = new MainSpacesItemDecoration(10);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        video_recycler_view.addItemDecoration(decoration);
        video_recycler_view.setLayoutManager(layoutManager);
        getActivity().getSupportLoaderManager().restartLoader(Config.ID_ALL, null, mCallBacks);
        return view;
    }


    private LoaderManager.LoaderCallbacks<Cursor> mCallBacks = new LoaderManager.LoaderCallbacks<Cursor>() {

        private final String[] IMAGE_COLUMNS = {

                MediaStore.Video.Media._ID, // 0
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.SIZE,// 3
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.MIME_TYPE,//5
                MediaStore.Video.Media.DATE_ADDED
        };


        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            CursorLoader loader = null;
            if (id == Config.ID_ALL) {
                String selection = IMAGE_COLUMNS[3] + ">0 AND " + IMAGE_COLUMNS[5] + "=?";
                String[] selectionArgs = new String[]{"video/mp4"};
                loader = new CursorLoader(
                        getActivity(),
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_COLUMNS,
                        selection,
                        selectionArgs,
                        IMAGE_COLUMNS[6] + " DESC"
                );
            } else if (id == Config.ID_WX) {
                /*String selection = IMAGE_COLUMNS[2] + " LIKE ? AND " + IMAGE_COLUMNS[3] + ">0 AND " + IMAGE_COLUMNS[4] + "=? OR " + IMAGE_COLUMNS[4] + "=? OR " + IMAGE_COLUMNS[4] + "=?";
                String [] selectionArgs = new String[]{"'%" + Config.WX_SAVE_DIR() + "%'","image/jpeg","image/png","image/jpg"};*/

                String selection = IMAGE_COLUMNS[2] + " LIKE ? AND " + IMAGE_COLUMNS[3] + ">0 AND " + IMAGE_COLUMNS[4] + "<? AND " + IMAGE_COLUMNS[5] + "=?";
                String[] selectionArgs = new String[]{"%" + Config.WX_SAVE_DIR() + "%","10000","video/mp4"};
                loader = new CursorLoader(
                        getActivity(),
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_COLUMNS,
                        selection,
                        selectionArgs,
                        IMAGE_COLUMNS[6] + " DESC"
                );
            }
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            mVideoModels = new ArrayList<>();
            while (data.moveToNext()){
                VideoModel model = new VideoModel();
                model.setImg_name(data.getString(data.getColumnIndexOrThrow(IMAGE_COLUMNS[1])));
                model.setImg_data(data.getString(data.getColumnIndexOrThrow(IMAGE_COLUMNS[2])));
                model.setImg_size(data.getLong(data.getColumnIndexOrThrow(IMAGE_COLUMNS[3])));
                model.setDuration(data.getLong(data.getColumnIndexOrThrow(IMAGE_COLUMNS[4])));
                model.setImg_modify(data.getLong(data.getColumnIndexOrThrow(IMAGE_COLUMNS[6])));
                mVideoModels.add(model);
            }
            mVideoAdapter = new VideoAdapter(getActivity(),mVideoModels);
            video_recycler_view.setAdapter(mVideoAdapter);
            mVideoAdapter.setOnItemViewClickListener(new ImageAdapter.OnItemViewClickListener() {
                @Override
                public void onItemViewClick(View view, int position) {

                }

                @Override
                public void onItemViewLongClick(View view, int position) {

                }

                @Override
                public void onCheckedChangeTitle(int size) {

                }

                @Override
                public void onShareClick(View view, int position) {
                    //ShareUtils.shareVideoToWXFriend(getActivity(),mVideoModels.get(position).getImg_data());
                    //ShareUtils.shareVideoToWXTimeLine(getActivity(),mVideoModels.get(position).getImg_data());
                    ShareUtils.shareVideoToWXFriend(getActivity(),mVideoModels.get(position).getImg_data());
                }
            });

        }

        @Override
        public void onLoaderReset(Loader loader) {

        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_video,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return false;
    }
}
