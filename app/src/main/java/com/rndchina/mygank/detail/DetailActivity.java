package com.rndchina.mygank.detail;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.rndchina.mygank.R;
import com.rndchina.mygank.base.BaseActivity;
import com.rndchina.mygank.db.DbManager;
import com.rndchina.mygank.db.GankModel;
import com.rndchina.mygank.db.SaveModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity {


    @BindView(R.id.webview)
    WebView mWebView;
    private View mView;
    private String mUrl;
    private boolean mIsFromMe;
    private String imageTemp = "";
    GankModel.ResultsBean resultsBean;
    SaveModel mSaveModel;

    @Override
    protected void initOptions() {
        ButterKnife.bind(this);
        mIsFromMe = getIntent().getBooleanExtra("isfromme", false);

        if (mIsFromMe) {
            mUrl = getIntent().getStringExtra("url");
        } else {
            resultsBean = (GankModel.ResultsBean) getIntent().getSerializableExtra("entity");
            mSaveModel = (SaveModel) DbManager.getInstence().queryModel(SaveModel.class, resultsBean.get_id());
            mUrl = resultsBean.getUrl();
            if (resultsBean.getImages() != null && resultsBean.getImages().size() > 0) {
                imageTemp = resultsBean.getImages().get(0);
            }
            if (mSaveModel == null) {
                initSaveModel(resultsBean, imageTemp);
            }

        }

        startLoading();
        if (StringUtils.isEmpty(mUrl)) {
            ToastUtils.showShort("网络地址加载有误，请稍后再试");
            return;
        }
        initWebView();
        mWebView.loadUrl(mUrl);

    }

    public void initSaveModel(GankModel.ResultsBean resultsBean, String imageTemp) {
        mSaveModel = new SaveModel(resultsBean.get_id(), resultsBean.getCreatedAt(),
                resultsBean.getDesc(), resultsBean.getPublishedAt(), resultsBean.getSource(),
                resultsBean.getType(), resultsBean.getUrl(), resultsBean.isUsed(), (String) resultsBean.getWho(),
                imageTemp, false);
    }

    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        //支持js脚本
        webSettings.setJavaScriptEnabled(true);
        //支持缩放
        webSettings.setSupportZoom(true);
        //支持内容重新布局
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //多窗口
        webSettings.supportMultipleWindows();
        //当webview调用requestFocus时为webview设置节点
        webSettings.setNeedInitialFocus(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(false);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //优先使用缓存:
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //提高渲染的优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 开启H5(APPCache)缓存功能
        webSettings.setAppCacheEnabled(true);
        // 开启 DOM storage 功能
        webSettings.setDomStorageEnabled(true);
        // 应用可以有数据库
        webSettings.setDatabaseEnabled(true);
        // 可以读取文件缓存(manifest生效)
        webSettings.setAllowFileAccess(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    //网页加载完成
                    stopLoading();
                }
            }
        });
    }

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_detail, null);
        return mView;
    }

    @Override
    protected String initToolbarTitle() {
        return "详情查看";
    }

    @Override
    protected void updateOptionsMenu(Menu menu) {
        if (mSaveModel != null && mSaveModel.isCollection()) {
            menu.findItem(R.id.action_save).setIcon(R.drawable.menu_action_save_choosen);
        }
        menu.findItem(R.id.action_download).setVisible(false);
        if (mIsFromMe) {
            menu.findItem(R.id.action_save).setVisible(false);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                startShareIntent("text/plain", "分享一片有用的文章:" + mUrl);
                break;
            case R.id.action_save:
                if (!mSaveModel.isCollection() && mSaveModel != null) {
                    mSaveModel.setCollection(true);
                    DbManager.getInstence().save(mSaveModel);
                    ToastUtils.showShort("收藏成功");
                    item.setIcon(R.drawable.menu_action_save_choosen);
                } else {
                    DbManager.getInstence().cancelSave(SaveModel.class, mSaveModel.get_id());
                    ToastUtils.showShort("取消收藏");
                    item.setIcon(R.drawable.menu_action_save);
                    finish();
                }

                break;
        }
        return true;
    }

}
