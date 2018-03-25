package com.rndchina.mygank.music;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.DeviceUtils;
import com.rndchina.mygank.R;
import com.rndchina.mygank.base.App;
import com.rndchina.mygank.music.db.DownFileStore;
import com.rndchina.mygank.music.db.PlaylistInfo;
import com.rndchina.mygank.music.model.Playlist;
import com.rndchina.mygank.music.recent.TopTracksLoader;
import com.rndchina.mygank.utils.IConstants;
import com.rndchina.mygank.utils.MusicUtils;
import com.rndchina.mygank.utils.PermissionHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by PC on 2018/1/30.
 */
public class MyMusicLocalFragment extends BaseMucicFragment {

    @BindView(R.id.tv_local_count)
    TextView mTvLocalCount;
    @BindView(R.id.ll_local)
    LinearLayout mLlLocal;
    @BindView(R.id.tv_recent_count)
    TextView mTvRecentCount;
    @BindView(R.id.ll_recent)
    LinearLayout mLlRecent;
    @BindView(R.id.tv_dld_count)
    TextView mTvDldCount;
    @BindView(R.id.ll_download)
    LinearLayout mLlDownload;
    @BindView(R.id.tv_list_count)
    TextView mTvListCount;
    @BindView(R.id.ll_myartist)
    LinearLayout mLlMyartist;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private PlaylistInfo playlistInfo; //playlist 管理类
    int localMusicCount = 0, recentMusicCount = 0, downLoadCount = 0, artistsCount = 0;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_music, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        playlistInfo = PlaylistInfo.getInstance(mContext);
        checkPermission();
        reloadAdapter();

    }

    private void checkPermission() {

        if (DeviceUtils.getSDKVersion() >= Build.VERSION_CODES.LOLLIPOP && ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            PermissionHelper.requestStorage(new PermissionHelper.OnPermissionGrantedListener() {
                @Override
                public void onPermissionGranted() {
                    reloadAdapter();
                }
            });
        }

    }

    public void reloadAdapter() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... unused) {
                ArrayList results = new ArrayList();
                setMusicInfo();
                ArrayList<Playlist> playlists = playlistInfo.getPlaylist();
                ArrayList<Playlist> netPlaylists = playlistInfo.getNetPlaylist();
                results.add(mContext.getResources().getString(R.string.created_playlists));
                results.addAll(playlists);
                if (netPlaylists != null) {
                    results.add("收藏的歌单");
                    results.addAll(netPlaylists);
                }

//                if(mAdapter == null){
//                    mAdapter = new MainFragmentAdapter(mContext);
//                }
//                mAdapter.updateResults(results, playlists, netPlaylists);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (mContext == null)
                    return;
                mTvLocalCount.setText("("+localMusicCount+")");
                mTvRecentCount.setText("("+recentMusicCount+")");
                mTvDldCount.setText("("+downLoadCount+")");
                mTvListCount.setText("("+artistsCount+")");

//                mAdapter.notifyDataSetChanged();
//                swipeRefresh.setRefreshing(false);
            }
        }.execute();

    }

    private void setMusicInfo() {

        if (DeviceUtils.getSDKVersion() >= Build.VERSION_CODES.LOLLIPOP && ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            loadCount(false);
        } else {
            loadCount(true);
        }


    }

    private void loadCount(boolean has) {
        if (has) {
            try {
                localMusicCount = MusicUtils.queryMusic(mContext, IConstants.START_FROM_LOCAL).size();
                recentMusicCount = TopTracksLoader.getCount(App.context, TopTracksLoader.QueryType.RecentSongs);
                downLoadCount = DownFileStore.getInstance(mContext).getDownLoadedListAll().size();
                artistsCount = MusicUtils.queryArtist(mContext).size();
            } catch (Exception e) {
                e.printStackTrace();
            }




        }
    }

    @OnClick({R.id.ll_local, R.id.ll_recent, R.id.ll_download, R.id.ll_myartist})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_local:
                Intent intent = new Intent(mContext, MusicTabActivity.class);
                intent.putExtra("page_number", 0);
                startActivity(intent);
                break;
            case R.id.ll_recent:
                break;
            case R.id.ll_download:
                break;
            case R.id.ll_myartist:
                Intent intent1 = new Intent(mContext, MusicTabActivity.class);
                intent1.putExtra("page_number", 1);
                startActivity(intent1);
                break;
        }
    }
}
