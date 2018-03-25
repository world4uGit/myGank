package com.rndchina.mygank.comic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rndchina.mygank.R;
import com.rndchina.mygank.comic.model.ComicListDetail;
import com.rndchina.mygank.comic.model.ComicPreView;
import com.rndchina.mygank.net.ComicApi;
import com.rndchina.mygank.net.HttpManager;
import com.rndchina.mygank.view.PreCacheLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ComicPreviewActivity extends Activity {

    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.ac_title)
    TextView mAcTitle;
    @BindView(R.id.ac_toolbar)
    Toolbar mAcToolbar;
    @BindView(R.id.tv_curr_pager)
    TextView mTvCurrPager;
    @BindView(R.id.tv_left_title)
    TextView mTvLeftTitle;
    @BindView(R.id.rv_left_list)
    RecyclerView mRvLeftList;
    @BindView(R.id.ll_left_layout)
    LinearLayout mLlLeftLayout;
    @BindView(R.id.sb_bar)
    SeekBar mSbBar;
    @BindView(R.id.tv_menu)
    TextView mTvMenu;
    @BindView(R.id.tv_brightness)
    TextView mTvBrightness;
    @BindView(R.id.tv_switch_screen)
    TextView mTvSwitchScreen;
    @BindView(R.id.tv_switch_module)
    TextView mTvSwitchModule;
    @BindView(R.id.ll_bottom)
    LinearLayout mLlBottom;
    @BindView(R.id.fl_layout)
    FrameLayout mFlLayout;
    private ComicListDetail mComicListDetail;
    private int index;
    private int page;
    private PreviewAdapter mPreviewAdapter;
    //集数适配器
    private ChapterAdapter mLeftChaptersAdapter;
    private ComicPreView firstVisibleItem;
    private int nextPosition;
    private boolean isUpdate;
    private PagerSnapHelper mPagerSnapHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_preview);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        //为了更好的提高滚动的流畅性，可以加大 RecyclerView 的缓存，用空间换时间
        mRvList.setHasFixedSize(true);
        mRvList.setItemViewCacheSize(10);
        mRvList.setDrawingCacheEnabled(true);
        mRvList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        initModule(SPUtils.getInstance().getInt("module", 0));

        mSbBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //先更新当前图片所在的对象
                updateCurrObject(mRvList);
                int progress = seekBar.getProgress() <= 0 ? 0 : seekBar.getProgress() - 1;
                ComicPreView.PagesBean pagesBean = firstVisibleItem.getPages().get(progress);
                if (pagesBean != null) {
                    int i = mPreviewAdapter.getData().indexOf(pagesBean);
                    if (i != -1) {
                        mRvList.scrollToPosition(i);
                        isUpdate = true;
                    }
                }
            }
        });

    }

    private void updateCurrObject(RecyclerView rvList) {

    }

    private void initModule(int module) {
        PreCacheLayoutManager linearLayoutManager = new PreCacheLayoutManager(this);
        linearLayoutManager.setExtraSpace(2);
        //默认的模式
        if (module == 1) {
            mRvList.setLayoutManager(linearLayoutManager);
            if (mPagerSnapHelper != null)
                mPagerSnapHelper.attachToRecyclerView(null);
        } else {
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRvList.setLayoutManager(linearLayoutManager);
            mPagerSnapHelper = new PagerSnapHelper();
            mPagerSnapHelper.attachToRecyclerView(mRvList);
        }
        mPreviewAdapter = null;

    }

    private void initData() {
        mComicListDetail = (ComicListDetail) getIntent().getSerializableExtra("comicListDetail");
        index = getIntent().getIntExtra("index", 1);
        page = getIntent().getIntExtra("index", 0);
        hideLayout();
        if(mComicListDetail!=null){
            setToolBar(mAcToolbar, mComicListDetail.getName(), true);
            showInfo(mComicListDetail);
            request(index);
        }

    }

    private void request(int index) {
        if(mComicListDetail!=null) {
            getPreviewFromNet(mComicListDetail.getId(), index);
        }
    }

    private void getPreviewFromNet(int id, int index) {
        ComicApi comicApi = HttpManager.getInstance().getComicApiService();
        comicApi.getComicPreViewById(id,index)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ComicPreView>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ComicPreView comicPreView) {
                        showPreview(comicPreView);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void showPreview(ComicPreView comicPreView) {
        final int module = SPUtils.getInstance().getInt("module", 0);
        //设置设置的预览模式
        //用哪个布局
        int layoutRes = module == 0 ? R.layout.comic_item_preview : R.layout.comic_item_preview2;
        List<ComicPreView.PagesBean> pages = comicPreView.getPages();

        if (mPreviewAdapter == null) {
            mPreviewAdapter = new PreviewAdapter(layoutRes, pages, comicPreView);
            firstVisibleItem = comicPreView;
            int size = pages.size();
            String format = String.format("%s  %d / %d", comicPreView.getName(), 1, size);
            mTvCurrPager.setText(format);
            mSbBar.setMax(size);
            mPreviewAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    //这里可以提前加载下一话
                    int lastPosition = mLeftChaptersAdapter.getLastPosition();
                    List<ComicListDetail.ChaptersBean> data = mLeftChaptersAdapter.getData();
                    nextPosition = lastPosition + 1;
                    if ((nextPosition == data.size())) {
                        //已经是最后一话不在自动加载
                        mPreviewAdapter.loadMoreEnd();
                    } else {
                        ComicListDetail.ChaptersBean item = mLeftChaptersAdapter.getItem(nextPosition);
                        if (item != null) {
                            request(item.getIndex());
                        }
                    }
                }
            }, mRvList);
            mRvList.setAdapter(mPreviewAdapter);
            mRvList.scrollToPosition(page);
        } else {
            //更新记录
            mPreviewAdapter.setComicPreView(comicPreView);
            mPreviewAdapter.setNewData(pages);
            //更新左边菜单
            mLeftChaptersAdapter.updatePosition(nextPosition, index);
            mPreviewAdapter.loadMoreComplete();
        }
        ToastUtils.showShort("页数"+comicPreView.getPages().size());
    }

    private void hideLayout() {
        mLlBottom.setTranslationY(SizeUtils.getMeasuredHeight(mLlBottom));
        mAcToolbar.setTranslationY(-SizeUtils.getMeasuredHeight(mAcToolbar));
        mLlLeftLayout.setTranslationX(-ScreenUtils.getScreenWidth());
    }

    private void setToolBar(Toolbar toolbar, String name, boolean needBackButton) {

    }

    private void showInfo(ComicListDetail comicListDetail) {

    }
}
