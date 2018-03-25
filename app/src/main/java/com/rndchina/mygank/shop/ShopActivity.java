package com.rndchina.mygank.shop;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rndchina.mygank.R;
import com.rndchina.mygank.base.BaseActivity;
import com.rndchina.mygank.shop.frament.CategoryFragment;
import com.rndchina.mygank.shop.frament.ShopCarFragment;
import com.rndchina.mygank.shop.frament.ShopHomeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopActivity extends BaseActivity {

    @BindView(R.id.vp_shop)
    ViewPager mVpShop;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;
    private View mView;
    private List<Fragment> mList;
    private final int NAVIGATION_HOME = 0;
    private final int NAVIGATION_READ = 1;
    private final int NAVIGATION_ME = 2;


    @Override
    protected void initOptions() {
        ButterKnife.bind(this);
        initFragment();
        ShopMainAdapter shopMainAdapter = new ShopMainAdapter(getSupportFragmentManager());
        mVpShop.setAdapter(shopMainAdapter);
        mVpShop.setOffscreenPageLimit(3);
        initListener();

    }

    private void initListener() {
        mNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case  R.id.navigation_home:
                        mVpShop.setCurrentItem(NAVIGATION_HOME);
                        return true;
                    case  R.id.navigation_read:
                        mVpShop.setCurrentItem(NAVIGATION_READ);
                        return true;
                    case  R.id.navigation_me:
                        mVpShop.setCurrentItem(NAVIGATION_ME);
                        return true;
                }
                return false;
            }
        });

        mVpShop.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case NAVIGATION_HOME :
                        mNavigation.setSelectedItemId(R.id.navigation_home);
                        break;
                    case NAVIGATION_READ :
                        mNavigation.setSelectedItemId(R.id.navigation_read);
                        break;
                    case NAVIGATION_ME :
                        mNavigation.setSelectedItemId(R.id.navigation_me);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initFragment() {
    mList = new ArrayList<>();
        mList.add(new ShopHomeFragment());
        mList.add(new CategoryFragment());
        mList.add(new ShopCarFragment());
    }

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_shop, null);
        return mView;
    }

    @Override
    protected String initToolbarTitle() {
        return "商城";
    }

    @Override
    protected void updateOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_save).setVisible(false);
        menu.findItem(R.id.action_download).setVisible(false);
        menu.findItem(R.id.action_share).setVisible(false);
    }


    private class ShopMainAdapter extends FragmentPagerAdapter{
        public ShopMainAdapter(FragmentManager fm) {
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
