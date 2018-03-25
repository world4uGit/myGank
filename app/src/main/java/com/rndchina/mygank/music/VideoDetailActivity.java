package com.rndchina.mygank.music;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rndchina.mygank.R;
import com.rndchina.mygank.base.BaseActivity;
import com.rndchina.mygank.base.Result;
import com.rndchina.mygank.image.ImageManager;
import com.rndchina.mygank.music.model.VideoData;
import com.rndchina.mygank.music.model.VideoDetail;
import com.rndchina.mygank.music.util.AnalysisHTML;
import com.rndchina.mygank.music.util.Util;
import com.rndchina.mygank.net.Api;
import com.rndchina.mygank.net.HttpManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VideoDetailActivity extends BaseActivity {

    @BindView(R.id.web_player)
    NormalGSYVideoPlayer mWebPlayer;
    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.activity_video_detail)
    LinearLayout mActivityVideoDetail;
    @BindView(R.id.news_parse_web)
    LinearLayout mNewsParseWeb;
    private View mView;
    private String videoTitle;


    private boolean isPlay;
    private boolean isPause;
    private boolean isSamll;

    private OrientationUtils orientationUtils;


    @Override
    protected void initOptions() {
        ButterKnife.bind(this);


        initPlayer();
        initWebView();
        initData();


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        VideoData videoData = bundle.getParcelable("videoData");
        startLoading();

        if (videoData != null) {

            //增加封面
            videoTitle = videoData.getTitle();
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mWebPlayer.setThumbImageView(imageView);

            ImageManager.getInstance().loadImage(this, videoData.getThumbnail(), imageView);
            mWebPlayer.setUp(videoData.getVideo(), true, "");

//            video.setUp(item.getVideo(), JCVideoPlayer.SCREEN_LAYOUT_LIST,"");
////            Glide.with(this).load(item.getThumbnail()).centerCrop().into(video.thumbImageView);
//            ImageLoaderUtil.load(this,item.getThumbnail(),video.thumbImageView);
//            newsTopType.setText("视 频");
//            newsTopLeadLine.setVisibility(View.VISIBLE);
//            newsTopImgUnderLine.setVisibility(View.VISIBLE);
//            newsTopDate.setText(item.getUpdate_time());
//            newsTopTitle.setText(item.getTitle());
//            newsTopAuthor.setText(item.getAuthor());
//            newsTopLead.setText(item.getLead());
            getDataFromNet(videoData.getId());
        }

    }

    private void getDataFromNet(String id) {
        Api api = HttpManager.getInstance().getVideoApiService();
        api.getVideoDetail("api", "getPost", id, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result.Data<VideoDetail>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Result.Data<VideoDetail> videoDetailData) {

                        VideoDetail data = videoDetailData.getDatas();
                        if (data.getParseXML() == 1) {
                            int i = data.getLead().trim().length();
                            AnalysisHTML analysisHTML = new AnalysisHTML();
                            analysisHTML.loadHtml(VideoDetailActivity.this, data.getContent(), analysisHTML.HTML_STRING, mNewsParseWeb, i);
                            stopLoading();
                        } else {
                            mWebView.loadUrl(addParams2WezeitUrl(data.getHtml5(), false));
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    @Override
    public void onBackPressed() {

        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }

        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }


    public String addParams2WezeitUrl(String url, boolean paramBoolean) {
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append(url);
        localStringBuffer.append("?client=android");
        localStringBuffer.append("&device_id=" + Util.getDeviceId(this));
        localStringBuffer.append("&version=" + "1.3.0");
        if (paramBoolean)
            localStringBuffer.append("&show_video=0");
        else {
            localStringBuffer.append("&show_video=1");
        }
        return localStringBuffer.toString();
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

    private void initPlayer() {
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, mWebPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        mWebPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        mWebPlayer.setRotateViewAuto(false);
        mWebPlayer.setLockLand(false);
        mWebPlayer.setShowFullAnimation(false);
        mWebPlayer.setNeedLockFull(true);
        //detailPlayer.setOpenPreView(true);
        mWebPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                mWebPlayer.startWindowFullscreen(VideoDetailActivity.this, true, true);
            }
        });

        mWebPlayer.setStandardVideoAllCallBack(new SampleListener() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //开始播放了才能旋转和全屏
                orientationUtils.setEnable(true);
                isPlay = true;
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
            }

            @Override
            public void onClickStartError(String url, Object... objects) {
                super.onClickStartError(url, objects);
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                if (orientationUtils != null) {
                    orientationUtils.backToProtVideo();
                }
            }
        });

        mWebPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });
    }

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_video_detail, null);
        return mView;
    }

    @Override
    protected String initToolbarTitle() {
        return null;
    }

    @Override
    protected void updateOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_save).setVisible(false);
//        menu.findItem(R.id.action_download).setVisible(false);
//        menu.findItem(R.id.action_share).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:

                break;
            case R.id.action_download:

                break;
        }
        return true;
    }

}
