package com.rndchina.mygank.music;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.rndchina.mygank.R;
import com.rndchina.mygank.base.BaseMusicActivity;
import com.rndchina.mygank.music.fragment.AlbumFragment;
import com.rndchina.mygank.music.fragment.ArtistFragment;
import com.rndchina.mygank.music.fragment.FolderFragment;
import com.rndchina.mygank.music.fragment.MusicFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicTabActivity extends BaseMusicActivity {
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.bottom_container)
    FrameLayout mBottomContainer;
    private View mView;

    private List<Fragment> mFragmentList = new ArrayList<>();
    String[] title = {"单曲", "歌手", "专辑", "文件夹"};
    private int page = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_tab);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null) {
            page = getIntent().getIntExtra("page_number", 0);
        }
        initToolbar();
        initFragment();
        setupViewPager(mViewpager);
        mViewpager.setOffscreenPageLimit(3);
        mTabs.setupWithViewPager(mViewpager);
        mTabs.setTabTextColors(R.color.primary_text, R.color.colorPrimary);
        mTabs.setSelectedTabIndicatorColor(Color.parseColor("#3fc2e3"));
        mViewpager.setCurrentItem(page);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
    }


    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new MusicFragment());
        mFragmentList.add(new ArtistFragment());
        mFragmentList.add(new AlbumFragment());
        mFragmentList.add(new FolderFragment());

    }

    private void setupViewPager(ViewPager viewpager) {
        MusicTabAdapter musicTabAdapter = new MusicTabAdapter(getSupportFragmentManager());

        viewpager.setAdapter(musicTabAdapter);
    }


    private class MusicTabAdapter extends FragmentPagerAdapter {
        public MusicTabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }
}
