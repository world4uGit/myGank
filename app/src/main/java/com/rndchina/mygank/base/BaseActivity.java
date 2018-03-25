package com.rndchina.mygank.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.rndchina.mygank.R;
import com.wang.avi.AVLoadingIndicatorView;

public abstract class BaseActivity extends AppCompatActivity {

    public Toolbar mToolbar;
    public LinearLayout mRootLayout;
    public AVLoadingIndicatorView mAviLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mRootLayout = (LinearLayout) findViewById(R.id.root_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAviLoading = (AVLoadingIndicatorView) findViewById(R.id.avi_loading);
        //显示具体的布局界面，由子类显示
        mRootLayout.addView(initContentView());
        //执行子类的操作
        initOptions();
        //初始化Toolbar
        initToolbar();
    }

    /**
     * 具体的业务逻辑，由子类实现
     */
    protected abstract void initOptions();


    /**
     * 具体的布局
     */
    protected abstract View initContentView();

    /**
     * 对toolbar进行设置
     */
    public void initToolbar(){
        mToolbar.setTitle(initToolbarTitle());
        setSupportActionBar(mToolbar);
        //设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base_toolbar,menu);
        updateOptionsMenu(menu);
        return true;
    }

    /**
     * 子类可以根据需要动态的更改菜单
     * @param menu
     */
    protected void  updateOptionsMenu(Menu menu){

    }

    protected abstract String initToolbarTitle();

    /**
     * 展示加载动画
     */
    public void startLoading(){
        mAviLoading.smoothToShow();
    }

    /**
     * 隐藏加载动画
     */
    public void stopLoading(){
        mAviLoading.smoothToHide();
    }

    /**
     * 打开分享界面
     *
     * @param type
     */
    public void startShareIntent(String type, String content) {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);
        share_intent.setType(type);
        share_intent.putExtra(Intent.EXTRA_TEXT, content);
        share_intent = Intent.createChooser(share_intent, "分享");
        startActivity(share_intent);
    }
}
