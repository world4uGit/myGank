package com.rndchina.mygank.shop;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.rndchina.mygank.R;
import com.rndchina.mygank.base.BaseActivity;
import com.rndchina.mygank.shop.frament.ProductCommentFragment;
import com.rndchina.mygank.shop.frament.ProductDetailsFragment;
import com.rndchina.mygank.shop.frament.ProductIntroduceFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetialsActivity extends BaseActivity {
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.add2shopcar)
    TextView mAdd2shopcar;
    private View mView;

    public int mProductId;
    private int mProductCount;


    List<Fragment> mFragmentList ;
    String[] title = {"商品", "详情", "评价"};



    @Override
    protected void initOptions() {
        ButterKnife.bind(this);
        initData();
        initFragment();
        initViewPager();
        mTabs.setupWithViewPager(mViewpager);
        mTabs.setTabTextColors(R.color.colorPrimary, R.color.jd_welcome_bg);
        mTabs.setSelectedTabIndicatorColor(Color.parseColor("#3fc2e3"));
        mViewpager.setCurrentItem(0);


    }

    private void initData() {
        Intent inten = getIntent();
        mProductId = inten.getIntExtra("productId", 0);
    }

    private void initViewPager() {
        ProductTabAdapter productTabAdapter = new ProductTabAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(productTabAdapter);

    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new ProductIntroduceFragment());
        mFragmentList.add(new ProductDetailsFragment());
        mFragmentList.add(new ProductCommentFragment());
    }

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_product_detials, null);
        return mView;
    }

    @Override
    protected void updateOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_save).setVisible(false);
        menu.findItem(R.id.action_download).setVisible(false);
        menu.findItem(R.id.action_share).setVisible(false);
    }

    @Override
    protected String initToolbarTitle() {

        return "商品详情";
    }

    private class ProductTabAdapter extends FragmentPagerAdapter{
        public ProductTabAdapter(FragmentManager fm) {
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
