package com.rndchina.mygank.me;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.leon.lib.settingview.LSettingItem;
import com.rndchina.mygank.R;
import com.rndchina.mygank.collect.CollectActivity;
import com.rndchina.mygank.me.model.VideoModel;
import com.rndchina.mygank.music.MusicActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {


    @BindView(R.id.setting_version)
    LSettingItem mSettingVersion;
    @BindView(R.id.setting_save)
    LSettingItem mSettingSave;
    @BindView(R.id.me_list_vedio)
    RecyclerView mRecyclerView;
    @BindView(R.id.setting_music)
    LSettingItem mSettingMusic;
    private Context mContext;
    private List<VideoModel> mList;
    private MeAdapter meAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initData();
        meAdapter = new MeAdapter(R.layout.item_video, mList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(meAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        initListener();

    }

    private void initListener() {
        mSettingVersion.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                ToastUtils.showShort("当前已经是最新版本");
            }
        });
        mSettingSave.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                startActivity(new Intent(mContext, CollectActivity.class));
            }
        });
        mSettingMusic.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                startActivity(new Intent(mContext, MusicActivity.class));
            }
        });
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add(new VideoModel(R.drawable.classes_01, "http://www.kgc.cn/android/29965.shtml"));
        mList.add(new VideoModel(R.drawable.classes_02, "http://www.kgc.cn/android/29917.shtml"));
        mList.add(new VideoModel(R.drawable.classes_03, "http://www.kgc.cn/android/29673.shtml"));
        mList.add(new VideoModel(R.drawable.classes_04, "http://www.kgc.cn/android/29661.shtml"));
        mList.add(new VideoModel(R.drawable.classes_05, "http://www.kgc.cn/android/29545.shtml"));
        mList.add(new VideoModel(R.drawable.classes_06, "http://www.kgc.cn/android/29317.shtml"));
        mList.add(new VideoModel(R.drawable.classes_07, "http://www.kgc.cn/android/29105.shtml"));
        mList.add(new VideoModel(R.drawable.classes_08, "http://www.kgc.cn/android/28881.shtml"));
        mList.add(new VideoModel(R.drawable.classes_09, "http://www.kgc.cn/android/28737.shtml"));
        mList.add(new VideoModel(R.drawable.classes_10, "http://www.kgc.cn/android/28337.shtml"));
        mList.add(new VideoModel(R.drawable.classes_11, "http://www.kgc.cn/android/27899.shtml"));
        mList.add(new VideoModel(R.drawable.classes_12, "http://www.kgc.cn/android/27785.shtml"));
        mList.add(new VideoModel(R.drawable.classes_13, "http://www.kgc.cn/android/22900.shtml"));
    }

}
