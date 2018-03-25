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
import com.rndchina.mygank.base.App;
import com.rndchina.mygank.music.BaseMucicFragment;
import com.rndchina.mygank.music.FolderListAdapter;
import com.rndchina.mygank.music.model.FolderInfo;
import com.rndchina.mygank.music.model.MusicInfo;
import com.rndchina.mygank.music.util.Comparator.FolderComparator;
import com.rndchina.mygank.music.util.Comparator.FolderCountComparator;
import com.rndchina.mygank.music.util.HandlerUtil;
import com.rndchina.mygank.music.util.PlayMusic;
import com.rndchina.mygank.music.util.PreferencesUtility;
import com.rndchina.mygank.utils.IConstants;
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
public class FolderFragment extends BaseMucicFragment {

    @BindView(R.id.tv_nonetwork)
    TextView mTvNonetwork;
    @BindView(R.id.avi)
    AVLoadingIndicatorView mAvi;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.dialog_text)
    TextView mDialogText;
    private boolean isAZSort = true;
    List<FolderInfo> mFolderInfos = new ArrayList<>();
    private PreferencesUtility mPreferences;
    private FolderListAdapter mFolderListAdapter;
    private HashMap<String, Integer> positionMap = new HashMap<>();
    private TextView headerTextView;
    PlayMusic playMusic;
    Handler handler;
    private View mView;


    public FolderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_folder, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPreferences = PreferencesUtility.getInstance(mContext);
        isAZSort = mPreferences.getSongSortOrder().equals(SortOrder.FolderSortOrder.FOLDER_A_Z);
        handler =  HandlerUtil.getInstance(mContext);
        initAdapter();

        initListener();
        reloadAdapter();    }

    private void initListener() {

    }

    private void initAdapter() {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mFolderListAdapter = new FolderListAdapter(R.layout.music_common_item,mFolderInfos);
        mRecyclerview.setAdapter(mFolderListAdapter);

    }

    public void reloadAdapter() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... unused) {
                isAZSort = mPreferences.getFoloerSortOrder().equals(SortOrder.FolderSortOrder.FOLDER_A_Z);
                Log.e("sort", "foler" + isAZSort);
                List<FolderInfo> folderList = MusicUtils.queryFolder(mContext);
                for (int i = 0; i < folderList.size(); i++) {
                    List<MusicInfo> albumList = MusicUtils.queryMusic(App.context, folderList.get(i).folder_path, IConstants.START_FROM_FOLDER);
                    folderList.get(i).folder_count = albumList.size();
                }
                if (isAZSort) {
                    Collections.sort(folderList, new FolderComparator());
                    for (int i = 0; i < folderList.size(); i++) {
                        if (positionMap.get(folderList.get(i).folder_sort) == null)
                            positionMap.put(folderList.get(i).folder_sort, i);
                    }
                } else {
                    Collections.sort(folderList, new FolderCountComparator());
                }
                mFolderInfos = folderList;
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
                mFolderListAdapter.setNewData(mFolderInfos);

            }
        }.execute();
    }
}
