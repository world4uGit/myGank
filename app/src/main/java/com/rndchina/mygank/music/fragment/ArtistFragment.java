package com.rndchina.mygank.music.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rndchina.mygank.R;
import com.rndchina.mygank.music.ArtistListAdapter;
import com.rndchina.mygank.music.BaseMucicFragment;
import com.rndchina.mygank.music.model.ArtistInfo;
import com.rndchina.mygank.music.util.Comparator.ArtistComparator;
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

public class ArtistFragment extends BaseMucicFragment {

    @BindView(R.id.tv_nonetwork)
    TextView mTvNonetwork;
    @BindView(R.id.avi)
    AVLoadingIndicatorView mAvi;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.dialog_text)
    TextView mDialogText;
    private boolean isAZSort = true;
    List<ArtistInfo> mArtistInfos = new ArrayList<>();
    private PreferencesUtility mPreferences;
    private ArtistListAdapter mArtistListAdapter;
    private HashMap<String, Integer> positionMap = new HashMap<>();
    private TextView headerTextView;
    PlayMusic playMusic;
    Handler handler;
    private View mView;

    public ArtistFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_artist, container, false);

        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPreferences = PreferencesUtility.getInstance(mContext);
        isAZSort = mPreferences.getSongSortOrder().equals(SortOrder.ArtistSortOrder.ARTIST_A_Z);
        handler =  HandlerUtil.getInstance(mContext);
        initAdapter();

        initListener();
        reloadAdapter();    }

    private void initListener() {
        mArtistListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(playMusic != null)
                    handler.removeCallbacks(playMusic);
                if(position > -1){
//                    playMusic = new PlayMusic(position,mMusicInfoList);
//                    handler.postDelayed(playMusic,70);
//                    playMusic.run();
                    mArtistListAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void initAdapter() {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mArtistListAdapter = new ArtistListAdapter(R.layout.music_common_item,mArtistInfos);
        mRecyclerview.setAdapter(mArtistListAdapter);


    }

    public void reloadAdapter() {
        if (mArtistListAdapter == null) {
            return;
        }
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... unused) {
                isAZSort = mPreferences.getArtistSortOrder().equals(SortOrder.ArtistSortOrder.ARTIST_A_Z);
                List<ArtistInfo> artList = MusicUtils.queryArtist(mContext);
                if (isAZSort) {
                    Collections.sort(artList, new ArtistComparator());
                    for (int i = 0; i < artList.size(); i++) {
                        if (positionMap.get(artList.get(i).artist_sort) == null)
                            positionMap.put(artList.get(i).artist_sort, i);
                    }
                }
                mArtistInfos = artList;
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
//                if (isAZSort) {
//                    mRecyclerview.addOnScrollListener(scrollListener);
//                } else {
//                    sideBar.setVisibility(View.INVISIBLE);
//                    recyclerView.removeOnScrollListener(scrollListener);
//                }
//                mAdapter.notifyDataSetChanged();
                mArtistListAdapter.setNewData(mArtistInfos);
//                View headView = View.inflate(mContext,R.layout.music_headaer,null);
//                TextView textView = (TextView) headView.findViewById(R.id.play_all_number);
//                headView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        ToastUtils.showShort("头部被点击");
//                    }
//                });
            }
        }.execute();    }
}
