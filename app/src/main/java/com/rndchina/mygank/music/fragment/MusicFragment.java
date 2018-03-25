package com.rndchina.mygank.music.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rndchina.mygank.R;
import com.rndchina.mygank.base.BaseView;
import com.rndchina.mygank.music.BaseMucicFragment;
import com.rndchina.mygank.music.MusicListAdapter;
import com.rndchina.mygank.music.model.MusicInfo;
import com.rndchina.mygank.music.util.Comparator.MusicComparator;
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
public class MusicFragment extends BaseMucicFragment implements BaseView {


    @BindView(R.id.tv_nonetwork)
    TextView mTvNonetwork;
    @BindView(R.id.avi)
    AVLoadingIndicatorView mAvi;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.dialog_text)
    TextView mDialogText;


    private boolean isAZSort = true;
    List<MusicInfo> mMusicInfoList = new ArrayList<>();
    private PreferencesUtility mPreferences;
    private MusicListAdapter mMusicListAdapter;
    private HashMap<String, Integer> positionMap = new HashMap<>();
    private View headView;
    private TextView headerTextView;
    PlayMusic playMusic;
    Handler handler;
    private View mView;


    public MusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_music, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPreferences = PreferencesUtility.getInstance(mContext);
        isAZSort = mPreferences.getSongSortOrder().equals(SortOrder.SongSortOrder.SONG_A_Z);
        handler =  HandlerUtil.getInstance(mContext);
        initAdapter();

        initListener();
        reloadAdapter();

    }

    private void initListener() {
        mMusicListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(playMusic != null)
                    handler.removeCallbacks(playMusic);
                if(position > -1){
                    playMusic = new PlayMusic(position,mMusicInfoList);
                    handler.postDelayed(playMusic,70);
//                    playMusic.run();
                    mMusicListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initAdapter() {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mMusicListAdapter = new MusicListAdapter(R.layout.item_music_list,mMusicInfoList);
        mRecyclerview.setAdapter(mMusicListAdapter);
        headView = LayoutInflater.from(mContext).inflate(R.layout.music_headaer, (ViewGroup) mRecyclerview.getParent(), false);

        headerTextView = (TextView) headView.findViewById(R.id.play_all_number);
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("头部被点击");
                if(playMusic != null)
                    handler.removeCallbacks(playMusic);
                if(mMusicListAdapter.getItemCount() > 0){
                    playMusic = new PlayMusic(0,mMusicInfoList);
                    handler.postDelayed(playMusic,70);
                }
            }
        });
        mMusicListAdapter.addHeaderView(headView);

    }

    private void loadView() {
        if(mView==null&&mContext!=null) {


            reloadAdapter();

        }



    }

    public void reloadAdapter() {
        if (mMusicListAdapter == null) {
            return;
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... unused) {
                isAZSort = mPreferences.getSongSortOrder().equals(SortOrder.SongSortOrder.SONG_A_Z);
                ArrayList<MusicInfo> songList = (ArrayList) MusicUtils.queryMusic(mContext, IConstants.START_FROM_LOCAL);
                // 名称排序时，重新排序并加入位置信息
                if (isAZSort) {
                    Collections.sort(songList, new MusicComparator());
                    for (int i = 0; i < songList.size(); i++) {
                        if (positionMap.get(songList.get(i).sort) == null)
                            positionMap.put(songList.get(i).sort, i);
                    }
                }
                mMusicInfoList = songList;
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mMusicListAdapter.setNewData(mMusicInfoList);
//                View headView = View.inflate(mContext,R.layout.music_headaer,null);
//                TextView textView = (TextView) headView.findViewById(R.id.play_all_number);
//                headView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        ToastUtils.showShort("头部被点击");
//                    }
//                });
                if(mMusicInfoList!=null&&mMusicInfoList.size()>0) {
                    headerTextView.setText("("+mMusicInfoList.size()+")");
                }
//                if (isAZSort) {
//                    mRecyclerview.addOnScrollListener(scrollListener);
//                } else {
//                    sideBar.setVisibility(View.INVISIBLE);
//                    recyclerView.removeOnScrollListener(scrollListener);
//                }
//                Log.e("MusicFragment","load t");
//                if (isFirstLoad) {
//                    Log.e("MusicFragment","load");
//                    frameLayout.removeAllViews();
//                    //framelayout 创建了新的实例
//                    ViewGroup p = (ViewGroup) view.getParent();
//                    if (p != null) {
//                        p.removeAllViewsInLayout();
//                    }
//                    frameLayout.addView(view);
//                    isFirstLoad = false;
//                }
            }
        }.execute();
    }

//    class PlayMusic implements Runnable{
//        int position;
//        List<MusicInfo> mList = new ArrayList<>();
//        public PlayMusic(int position,List<MusicInfo> mList){
//            this.position = position;
//            this.mList = mList;
//        }
//
//        @Override
//        public void run() {
//            long[] list = new long[mList.size()];
//            HashMap<Long, MusicInfo> infos = new HashMap();
//            for (int i = 0; i < mList.size(); i++) {
//                MusicInfo info = mList.get(i);
//                list[i] = info.songId;
//                info.islocal = true;
//                info.albumData = MusicUtils.getAlbumArtUri(info.albumId) + "";
//                infos.put(list[i], mList.get(i));
//            }
//            if (position > -1)
//                MusicPlayer.playAll(infos, list, position, false);
//        }
//    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        if(isVisibleToUser){
//            reloadAdapter();
//        }

    }

    @Override
    public void showLoading() {
        mAvi.smoothToShow();

    }

    @Override
    public void hideLoading() {

        mAvi.smoothToHide();

    }

    @Override
    public void stopRefush() {

    }

    @Override
    public void startLodingMore() {

    }

    @Override
    public void stopLodingMore() {

    }

    @Override
    public void loadMoreErr() {

    }

    @Override
    public void loadComplete() {

    }
}
