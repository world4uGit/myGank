package com.rndchina.mygank.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rndchina.mygank.R;
import com.rndchina.mygank.base.BaseAdapter;
import com.rndchina.mygank.base.BaseView;
import com.rndchina.mygank.common.Constant;
import com.rndchina.mygank.db.GankModel;
import com.rndchina.mygank.detail.DetailActivity;
import com.rndchina.mygank.net.Api;
import com.rndchina.mygank.net.HttpManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements BaseView {

    @BindView(R.id.tv_nonetwork)
    TextView mTvNonetwork;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout mRefreshlayout;
    @BindView(R.id.avi)
    AVLoadingIndicatorView mAvi;


    private Context mContext;
    private int mPage = 1;
    private BaseAdapter mBaseAdapter;
    //是否可以加载更多
    private boolean mIsLoadMore = true;
    private List<GankModel.ResultsBean> mList = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerview.setLayoutManager(initLayoutManager());
        //设置适配器
        mBaseAdapter = new BaseAdapter(R.layout.item_homefragment,mList);

        mRecyclerview.setAdapter(mBaseAdapter);

        //设置下拉刷新
        mRefreshlayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        initListener();
        initItemClickListener();
//        if(NetworkUtils.isConnected()) {
//            showLoading();
//            getDateFromNet(Constant.GET_DATA_TYPE_NORMAL);
//        }

        showLoading();
        mPage=1;
        getDateFromNet(Constant.GET_DATA_TYPE_NORMAL);


        


    }

    private void initItemClickListener() {
        mBaseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GankModel.ResultsBean resultsBean = (GankModel.ResultsBean) adapter.getItem(position);
                startActivity(new Intent(mContext, DetailActivity.class).putExtra("entity",resultsBean));
            }
        });
    }

    private void initListener() {
        View view = View.inflate(mContext,R.layout.errview,null);
        TextView textView = (TextView) view.findViewById(R.id.tv_error);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                stopLodingMore();
                mPage = 1;
                getDateFromNet(Constant.GET_DATA_TYPE_NORMAL);
            }
        });


        mRefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showLoading();
                stopLodingMore();
                mPage = 1;
                getDateFromNet(Constant.GET_DATA_TYPE_NORMAL);

            }
        });

        mBaseAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                showLoading();
                if(!mIsLoadMore) {

                    ToastUtils.showShort("没有更多数据了");
                    mBaseAdapter.loadMoreEnd();
                }else {
                    startLodingMore();
                    mPage++;
                    getDateFromNet(Constant.GET_DATA_TYPE_LOADMORE);

                }

            }
        });

    }

    private void getDateFromNet(final int type) {

        Api api = HttpManager.getInstance().getApiService();
        api.getCategroyData("Android",Constant.PAGE_SIZE,mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull GankModel gankModel) {

                        if(gankModel.getError()) {
                            ToastUtils.showShort("服务器异常");
                            return;
                        }
                        if(type==Constant.GET_DATA_TYPE_NORMAL) {

                            //正常加载

                            mList.clear();
                            mList = gankModel.getResults();

                        }else {

                            //加载更多

                            mList.addAll(gankModel.getResults());
                        }
                        //判断是否已经到底
                        if(gankModel.getResults().size()<Constant.PAGE_SIZE) {
                            mIsLoadMore = false;
                        }
                        
                        mBaseAdapter.setNewData(mList);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        stopRefush();
                        if(type==Constant.GET_DATA_TYPE_LOADMORE) {

                            loadMoreErr();

                        }else {
                            startLodingMore();

                        }
                        hideLoading();
                        if(!NetworkUtils.isConnected()) {
                            mTvNonetwork.setVisibility(View.VISIBLE);

                        }else {
                            mTvNonetwork.setVisibility(View.GONE);
                        }
                        mBaseAdapter.setEmptyView(R.layout.errview,mRecyclerview);


                    }

                    @Override
                    public void onComplete() {
                        stopRefush();
                        if(mBaseAdapter.getItemCount()<=0) {
                            mBaseAdapter.setEmptyView(R.layout.emptyview,mRecyclerview);
                        }

                        if(type==Constant.GET_DATA_TYPE_LOADMORE) {

                            loadComplete();

                        }else {
                            startLodingMore();
                        }
                        hideLoading();
                        if(!NetworkUtils.isConnected()) {
                            mTvNonetwork.setVisibility(View.VISIBLE);

                        }else {
                            mTvNonetwork.setVisibility(View.GONE);

                        }

                    }
                });

    }

    private RecyclerView.LayoutManager initLayoutManager() {
        return new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
    }

    @Override
    public void showLoading() {
        mAvi.smoothToShow();

    }

    @Override
    public void hideLoading() {
        if(mAvi.isShown()) {
            mAvi.smoothToHide();
        }

    }

//    @Override
//    public void startRefush() {
//        mRefreshlayout.setRefreshing(true);
//
//    }

    @Override
    public void stopRefush() {
        if(mRefreshlayout.isRefreshing()) {
            mRefreshlayout.setRefreshing(false);
        }

    }

    @Override
    public void startLodingMore() {
        mBaseAdapter.setEnableLoadMore(true);
    }

    @Override
    public void stopLodingMore() {

        mBaseAdapter.setEnableLoadMore(false);

    }

    @Override
    public void loadMoreErr() {
        mBaseAdapter.loadMoreFail();

    }

    @Override
    public void loadComplete() {
        mBaseAdapter.loadMoreComplete();

    }
}
