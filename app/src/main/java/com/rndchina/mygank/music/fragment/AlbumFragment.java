package com.rndchina.mygank.music.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rndchina.mygank.R;
import com.rndchina.mygank.music.AlbumListAdapter;
import com.rndchina.mygank.music.BaseMucicFragment;
import com.rndchina.mygank.music.model.AlbumInfo;
import com.rndchina.mygank.music.util.Comparator.AlbumComparator;
import com.rndchina.mygank.music.util.HandlerUtil;
import com.rndchina.mygank.music.util.PlayMusic;
import com.rndchina.mygank.music.util.PreferencesUtility;
import com.rndchina.mygank.utils.MusicUtils;
import com.rndchina.mygank.utils.SortOrder;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends BaseMucicFragment {

    @BindView(R.id.tv_nonetwork)
    TextView mTvNonetwork;
    @BindView(R.id.avi)
    AVLoadingIndicatorView mAvi;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.dialog_text)
    TextView mDialogText;
    private boolean isAZSort = true;
    List<AlbumInfo> mAlbumInfos = new ArrayList<>();
    private PreferencesUtility mPreferences;
    private AlbumListAdapter mAlbumListAdapter;
    private HashMap<String, Integer> positionMap = new HashMap<>();
    private TextView headerTextView;
    PlayMusic playMusic;
    Handler handler;
    private View mView;


    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_album, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPreferences = PreferencesUtility.getInstance(mContext);
        isAZSort = mPreferences.getSongSortOrder().equals(SortOrder.AlbumSortOrder.ALBUM_A_Z);
        handler =  HandlerUtil.getInstance(mContext);
        initAdapter();

        initListener();
        reloadAdapter();    }

    private void initListener() {

    }

    private void initAdapter() {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mAlbumListAdapter = new AlbumListAdapter(R.layout.music_common_item,mAlbumInfos);
        mRecyclerview.setAdapter(mAlbumListAdapter);

    }

    public void reloadAdapter() {
        if (mAlbumListAdapter == null) {
            return;
        }
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... unused) {
                isAZSort = mPreferences.getAlbumSortOrder().equals(SortOrder.AlbumSortOrder.ALBUM_A_Z);
                Log.e("sort", isAZSort + "");
                List<AlbumInfo> albumList = MusicUtils.queryAlbums(mContext);
                if (isAZSort) {
                    Collections.sort(albumList, new AlbumComparator());
                    for (int i = 0; i < albumList.size(); i++) {
                        if (positionMap.get(albumList.get(i).album_sort) == null)
                            positionMap.put(albumList.get(i).album_sort, i);
                    }
                }
                mAlbumInfos = albumList;
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
//                if (isAZSort) {
//                    recyclerView.addOnScrollListener(scrollListener);
//                } else {
//                    sideBar.setVisibility(View.INVISIBLE);
//                    recyclerView.removeOnScrollListener(scrollListener);
//                }
//                mAdapter.notifyDataSetChanged();
                mAlbumListAdapter.setNewData(mAlbumInfos);

            }
        }.execute();

    }
}
