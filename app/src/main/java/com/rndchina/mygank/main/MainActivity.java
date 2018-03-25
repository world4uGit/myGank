package com.rndchina.mygank.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ReflectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.rndchina.mygank.R;
import com.rndchina.mygank.comic.ComicActivity;
import com.rndchina.mygank.common.Constant;
import com.rndchina.mygank.friendcircle.FriendCircleActivity;
import com.rndchina.mygank.home.HomeFragment;
import com.rndchina.mygank.main.model.DrawerModel;
import com.rndchina.mygank.me.MeFragment;
import com.rndchina.mygank.music.MyMusicNetFragment;
import com.rndchina.mygank.search.SearchActivity;
import com.rndchina.mygank.shop.ShopActivity;
import com.rndchina.mygank.utils.PermissionHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.rv_drawerlist)
    RecyclerView rvDrawerlist;
    @BindView(R.id.drawlayout)
    DrawerLayout drawlayout;

    private final int NAVIGATION_HOME = 0;
    private final int NAVIGATION_READ = 1;
    private final int NAVIGATION_ME = 2;
    private boolean isOpen = false;
    private List<Fragment> mList;
    private List<DrawerModel> mListDrawerModel;
    private DrawerListAdapter drawerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar();
        drawlayout.setScrimColor(Color.TRANSPARENT);
        drawlayout.setDrawerElevation(0);


        initFragment();
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager());
        vpMain.setAdapter(mainAdapter);
        vpMain.setOffscreenPageLimit(3);

        rvDrawerlist.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
//        rvDrawerlist.addItemDecoration(new SpaceItemDecoration(conv.dp2px(getActivity(),20)));
//        drawerListAdapter = new DrawerListAdapter(R.layout.item_main_drawerlist,initData());
        drawerListAdapter = new DrawerListAdapter(this, initData());
        rvDrawerlist.setAdapter(drawerListAdapter);
        initListener();
        initPermission();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
        return true;
    }

    private void initPermission() {
        PermissionHelper.requestAll(new PermissionHelper.OnPermissionGrantedListener() {
            @Override
            public void onPermissionGranted() {

            }
        });
    }

    private void initListener() {
        
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case  R.id.navigation_home:
                        vpMain.setCurrentItem(NAVIGATION_HOME);
                        return true;
                    case  R.id.navigation_read:
                        vpMain.setCurrentItem(NAVIGATION_READ);
                        return true;
                    case  R.id.navigation_me:
                        vpMain.setCurrentItem(NAVIGATION_ME);
                        return true;
                }
                return false;
            }
        });


        drawlayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View contentView = drawlayout.getChildAt(0);
                int offset = (int) (drawerView.getWidth()*slideOffset);
                contentView.setTranslationX(offset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case NAVIGATION_HOME :
                        navigation.setSelectedItemId(R.id.navigation_home);
                        break;
                    case NAVIGATION_READ :
                        navigation.setSelectedItemId(R.id.navigation_read);
                        break;
                    case NAVIGATION_ME :
                        navigation.setSelectedItemId(R.id.navigation_me);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        
        
        drawerListAdapter.setmOnDrawerClickListener(new DrawerListAdapter.OnDrawerClickListener() {
            @Override
            public void onClick(int position) {
                String categroy = Constant.CATEGORY_ALL;
                switch (mListDrawerModel.get(position).getTitle()) {
                    case "iOS":
                        categroy = Constant.CATEGORY_IOS;
                        showGategroyInfo(categroy);
                        break;
                    case "休息视频":
                        categroy = Constant.CATEGORY_VIDEO;
                        showGategroyInfo(categroy);
                        break;
                    case "拓展资源":
//                        categroy = Constant.CATEGORY_EXPANDRESOURCE;
//                        showGategroyInfo(categroy);
                        startActivity(new Intent(MainActivity.this, ComicActivity.class));

                        break;
                    case "前端":
                        categroy = Constant.CATEGORY_CLIENT;
                        showGategroyInfo(categroy);
                        break;
                    case "瞎推荐":
                        startActivity(new Intent(MainActivity.this, ShopActivity.class));
                        break;
                    case "App":
                        categroy = Constant.CATEGORY_APP;
                        showGategroyInfo(categroy);
                        break;
                    case "福利":
                        startActivity(new Intent(MainActivity.this, FriendCircleActivity.class));
                        break;
                }
                drawlayout.closeDrawer(GravityCompat.START);
            }
        });


    }

    private void showGategroyInfo(String categroy) {
        ToastUtils.showShort(categroy);
    }

    private List<DrawerModel> initData() {
        mListDrawerModel = new ArrayList<>();
        mListDrawerModel.add(new DrawerModel(R.drawable.drawer_icon_ios, Constant.CATEGORY_IOS));
        mListDrawerModel.add(new DrawerModel(R.drawable.drawer_icon_girl, Constant.CATEGORY_GIRL));
        mListDrawerModel.add(new DrawerModel(R.drawable.drawer_icon_client, Constant.CATEGORY_CLIENT));
        mListDrawerModel.add(new DrawerModel(R.drawable.drawer_icon_recommend, Constant.CATEGROY_RECOMMEND));
        mListDrawerModel.add(new DrawerModel(R.drawable.drawer_icon_app, Constant.CATEGORY_APP));
        mListDrawerModel.add(new DrawerModel(R.drawable.drawer_icon_resource, Constant.CATEGORY_EXPANDRESOURCE));
        mListDrawerModel.add(new DrawerModel(R.drawable.drawer_icon_video, Constant.CATEGORY_VIDEO));
        return mListDrawerModel;    }

    private void initFragment() {
        mList = new ArrayList<>();
        mList.add(new HomeFragment());
        mList.add(new MyMusicNetFragment());
        mList.add(new MeFragment());

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        try {
//            Field f = toolbar.getClass().getDeclaredField("mTitleTextView");
//            f.setAccessible(true);
//            final TextView titleTextView = (TextView) f.get(toolbar);
            final TextView titleTextView = ReflectUtils.reflect(toolbar).field("mTitleTextView").get();
            titleTextView.setClickable(true);
            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showShort("标题被点击了");
                    if(!isOpen) {
                        isOpen = true;
                        drawlayout.openDrawer(rvDrawerlist);
                    }else {
                        isOpen = false;
                        drawlayout.openDrawer(rvDrawerlist);

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private class MainAdapter extends FragmentPagerAdapter{
        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }


    }


}
