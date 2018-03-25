package com.rndchina.mygank.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.FlexboxLayout;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.rndchina.mygank.R;
import com.rndchina.mygank.base.BaseAdapter;
import com.rndchina.mygank.common.Constant;
import com.rndchina.mygank.db.GankModel;
import com.rndchina.mygank.detail.DetailActivity;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SearchActivity extends AppCompatActivity implements SearchContract.View {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_search)
    TextView mTvSearch;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.tv_hotsearch)
    TextView mTvHotsearch;
    @BindView(R.id.flexbox_layout)
    FlexboxLayout mFlexboxLayout;
    @BindView(R.id.iv_deleteall)
    ImageView mIvDeleteall;
    @BindView(R.id.layout_history)
    RelativeLayout mLayoutHistory;
    @BindView(R.id.avi)
    AVLoadingIndicatorView mAvi;
    @BindView(R.id.avi_loadmore)
    AVLoadingIndicatorView mAviLoadmore;
    @BindView(R.id.layout_loadmore)
    LinearLayout mLayoutLoadmore;
    @BindView(R.id.recyclerview_history)
    RecyclerView mRecyclerviewHistory;

    private List<String> mHistoryTitles = new ArrayList<String>();//历史搜索数据
    private List<String> mHotTags = new ArrayList<String>();
    private int mPage = 1;//当前页码
    private Disposable mDisposable;
    protected List<GankModel.ResultsBean> mList = new ArrayList<>();//从服务端加载到的数据
    private SearchPresenter mSearchPrenter;
    private HistorySearchAdapter mHistorySearchAdapter;
    private BaseAdapter mBaseAdapter;
    private String mKeywords;
    private boolean mIsLoadMore = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setPrestener();
        mHistoryTitles = mSearchPrenter.loadHistroyData();
        mHotTags = mSearchPrenter.loadHotTag();
        showHotTag(mHotTags);
        //展示历史搜索效果
        mRecyclerviewHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //定义适配器
        mHistorySearchAdapter = new HistorySearchAdapter(R.layout.item_history_search,mHistoryTitles);
        mRecyclerviewHistory.setAdapter(mHistorySearchAdapter);
        mBaseAdapter = new BaseAdapter(R.layout.item_homefragment,mList);
        hideLoading();
        initListener();

    }

    private void initListener() {

        mHistorySearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mKeywords = mHistoryTitles.get(position);
                mSearchPrenter.historyClick(mKeywords);
            }
        });

        mHistorySearchAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShort("子控件点击事件"+position);
                mHistoryTitles.remove(position);
                mSearchPrenter.saveHistoryData(mHistoryTitles);
                mHistorySearchAdapter.notifyDataSetChanged();
            }
        });

        mBaseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GankModel.ResultsBean resultBean = (GankModel.ResultsBean) adapter.getItem(position);
                Intent intent = new Intent(SearchActivity.this, DetailActivity.class).putExtra("entity",resultBean);
                startActivity(intent);
            }
        });


        mBaseAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
//                if(!mIsLoadMore) {
//
//                    ToastUtils.showShort("没有更多数据了");
//                    mBaseAdapter.loadMoreEnd();
//                }else {
//                    mPage++;
//                    mSearchPrenter.getDataFromService(mKeywords, mPage, Constant.GET_DATA_TYPE_LOADMORE);
//
//                }
                mPage++;
                mSearchPrenter.getDataFromService(mKeywords, mPage, Constant.GET_DATA_TYPE_LOADMORE);

            }
        });

        RxTextView.textChanges(mEtSearch)
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(CharSequence charSequence) throws Exception {
                        //过滤为空的数据 避免多余请求
                        return charSequence.toString().trim().length() > 0;
                    }
                })
                .debounce(200, TimeUnit.MILLISECONDS)//利用debounce操作符延迟发送 TimeUnit.MILLISECONDS(毫秒)指定一个参数的单位

                .observeOn(AndroidSchedulers.mainThread())

        .subscribe(
                new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {

                mSearchPrenter.searchFromServer(mEtSearch.getText().toString().trim());

            }


        });


    }

    private void showHotTag(List<String> tags) {
        // 通过代码向FlexboxLayout添加View
        for (int i = 0; i < tags.size(); i++) {
            TextView textView = new TextView(this);
            textView.setBackground(getResources().getDrawable(R.drawable.flexbox_text_bg));
            textView.setText(tags.get(i));
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(30, 30, 30, 30);
            textView.setClickable(true);
            textView.setFocusable(true);
            textView.setTextColor(getResources().getColor(R.color.primary_text));
            mFlexboxLayout.addView(textView);
            //通过FlexboxLayout.LayoutParams 设置子元素支持的属性
            ViewGroup.LayoutParams params = textView.getLayoutParams();
            if (params instanceof FlexboxLayout.LayoutParams) {
                FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) params;
                //layoutParams.setFlexBasisPercent(0.5f);
                layoutParams.setMargins(10, 10, 20, 10);
            }
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tv = (TextView) v;
                    //得到搜索条件，首先屏蔽掉历史搜索和热门搜索
                    showSearchResult(true);
                    //更新界面，显示加载中
                    showLoading();
                    //保存搜索记录
                    mKeywords = tv.getText().toString().trim();
                    mSearchPrenter.addHistorySearch(mKeywords);
                    mEtSearch.setText(mKeywords);
                    //发起服务请求
                    mSearchPrenter.getDataFromService(mKeywords, mPage, Constant.GET_DATA_TYPE_NORMAL);
                }
            });
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setEditText(String msg) {
        mEtSearch.setText(msg);

    }

    @Override
    public void showSearchResult(boolean flag) {
        if(flag) {
            mTvHotsearch.setVisibility(GONE);
            mLayoutHistory.setVisibility(GONE);
            mFlexboxLayout.setVisibility(GONE);
        }else {
            mTvHotsearch.setVisibility(VISIBLE);
            mLayoutHistory.setVisibility(VISIBLE);
            mFlexboxLayout.setVisibility(VISIBLE);

        }

    }

    @Override
    public void updateShow(GankModel gankModel, int type) {

        if (Constant.GET_DATA_TYPE_NORMAL == type) {
            //正常模式，清空数据，重新加载
            mList.clear();
            mList = gankModel.getResults();
        } else {
            //加载更多模式
            mList.addAll(gankModel.getResults());
        }
        //判断当前显示是哪个适配器
        if (mRecyclerviewHistory.getAdapter() instanceof HistorySearchAdapter) {
            mRecyclerviewHistory.setAdapter(mBaseAdapter);
        }
        mBaseAdapter.setNewData(mList);
        mBaseAdapter.notifyDataSetChanged();

    }

    @Override
    public void showErrorTip(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void setPrestener() {
        mSearchPrenter = new SearchPresenter(this);

    }

    @Override
    public void setEmptyView() {
        if(mBaseAdapter.getItemCount()<=0) {
            mBaseAdapter.setEmptyView(R.layout.emptyview,mRecyclerviewHistory);
        }
    }


    @Override
    public void showLoading() {
        mAvi.setVisibility(VISIBLE);
        mRecyclerviewHistory.setVisibility(GONE);
        mAvi.smoothToShow();

    }

    @Override
    public void hideLoading() {
        mAvi.setVisibility(View.GONE);
        mRecyclerviewHistory.setVisibility(VISIBLE);
        mAvi.smoothToHide();

    }


    @Override
    public void stopRefush() {

    }

    @Override
    public void startLodingMore() {

    }

    @Override
    public void stopLodingMore() {

    }

    @Override
    public void loadMoreErr() {

        mBaseAdapter.loadMoreFail();

    }

    @Override
    public void loadComplete() {

        mBaseAdapter.loadMoreComplete();

    }

    @OnClick({R.id.iv_back, R.id.tv_search, R.id.iv_deleteall})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                goBack();
                break;
            case R.id.tv_search:
                LogUtils.wTag("thread0",android.os.Process.myPid()+" Thread: "+android.os.Process.myTid()+" name "+Thread.currentThread().getName());
                mSearchPrenter.searchFromServer(mEtSearch.getText().toString().trim());
                break;
            case R.id.iv_deleteall:
                mHistoryTitles.clear();
                mSearchPrenter.clearAllHistory("history_search");
                mHistorySearchAdapter.setNewData(mHistoryTitles);
                mHistorySearchAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void goBack() {
        if(mLayoutHistory.getVisibility()==VISIBLE) {
            finish();
            mSearchPrenter.stopDis();
        }else {
            showSearchResult(false);
            //判断当前显示是哪个适配器
            if (mRecyclerviewHistory.getAdapter() instanceof BaseAdapter) {
                mRecyclerviewHistory.setAdapter(mHistorySearchAdapter);
            }
            mList.clear();
            mHistoryTitles.clear();
            mHistoryTitles = mSearchPrenter.loadHistroyData();
            mHistorySearchAdapter.setNewData(mHistoryTitles);
            mHistorySearchAdapter.notifyDataSetChanged();
            setEditText("");

        }

    }
}
