package com.rndchina.mygank.music;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.rndchina.mygank.R;
import com.rndchina.mygank.base.BaseMusicActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicActivity extends BaseMusicActivity {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private View mView;

    private RadioGroup radioGroup;
    private List<Fragment> fragments;
    boolean fromViewPager;
    boolean fromRadioGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.bind(this);

        initRadioGroup();
        initToolbar();
        initFragments();
        initListener();


        MusicAdapter musicAdapter = new MusicAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(musicAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setCurrentItem(0);
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                switch (position) {
//                    case 0 :
//                        mViewPager.setCurrentItem(position);
//                        break;
//                    case 1 :
//                        navigation.setSelectedItemId(R.id.navigation_read);
//                        break;
//                    case 2 :
//                        navigation.setSelectedItemId(R.id.navigation_me);
//                        break;
//                }
                if (fromRadioGroup) {
                    fromRadioGroup = false;
                    return;
                }
                fromViewPager = true;
                ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new MyMusicLocalFragment());
        fragments.add(new MyMusicNetFragment());
        fragments.add(new MyMusicSocialFragment());
    }

    private void initRadioGroup() {
        radioGroup = (RadioGroup) LayoutInflater.from(this).inflate(R.layout.music_main_toolbar_head, null);
        radioGroup.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (fromViewPager) {
                    fromViewPager = false;
                    return;
                }
                fromRadioGroup = true;
                switch (i) {
                    case R.id.net_music:
                        mViewPager.setCurrentItem(0, true);
                        break;
                    case R.id.local_music:
                        mViewPager.setCurrentItem(1, true);
                        break;
                    case R.id.social:
                        mViewPager.setCurrentItem(2, true);
                        break;
                    default:
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);
        return true;
    }

    public void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        //设置显示返回箭头和customView
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        actionBar.setCustomView(radioGroup, layoutParams);
    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // TODO: add setContentView(...) invocation
//
//    }

    private class MusicAdapter extends FragmentPagerAdapter {
        public MusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
