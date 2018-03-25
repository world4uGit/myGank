package com.rndchina.mygank.read;


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
import com.rndchina.mygank.base.BaseView;
import com.rndchina.mygank.common.Constant;
import com.rndchina.mygank.detail.DetailActivity;
import com.rndchina.mygank.net.Api;
import com.rndchina.mygank.net.HttpManager;
import com.rndchina.mygank.read.model.ReadModel;
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
public class ReadFragment extends Fragment implements BaseView {


    @BindView(R.id.tv_nonetwork)
    TextView mTvNonetwork;
    @BindView(R.id.avi)
    AVLoadingIndicatorView mAvi;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout mRefreshlayout;


    private Context mContext;
    private int mPage = 1;
    private ReadAdapter mReadAdapter;
    //是否可以加载更多
    private boolean mIsLoadMore = true;
    private List<ReadModel.NewslistBean> mList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_read, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));

        mReadAdapter = new ReadAdapter(R.layout.item_homefragment,mList);

        mRecyclerview.setAdapter(mReadAdapter);

        //设置下拉刷新
        mRefreshlayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        initListener();
        initItemClickListener();
        showLoading();
        getDateFromNet(Constant.GET_DATA_TYPE_NORMAL);
        if(NetworkUtils.isConnected()) {
            showLoading();
            getDateFromNet(Constant.GET_DATA_TYPE_NORMAL);
        }else {
            mTvNonetwork.setVisibility(View.VISIBLE);
        }

    }

    private void getDateFromNet(final int type) {
        Api api = HttpManager.getInstance().getApiService(Constant.BASE_URL_Read);
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        Api api = retrofit.create(Api.class);
        api.getReadData(mPage,Constant.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReadModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ReadModel readModel) {

//                        if(gankModel.getError()) {
//                            ToastUtils.showShort("服务器异常");
//                            return;
//                        }
                        if(type==Constant.GET_DATA_TYPE_NORMAL) {

                            //正常加载

                            mList.clear();
                            mList = readModel.getNewslist();

                        }else {

                            //加载更多

                            mList.addAll(readModel.getNewslist());
                        }
                        //判断是否已经到底
                        if(readModel.getNewslist().size()<Constant.PAGE_SIZE) {
                            mIsLoadMore = false;
                        }

                        mReadAdapter.setNewData(mList);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        stopRefush();
                        if(type==Constant.GET_DATA_TYPE_NORMAL) {

                        }else {
                            loadMoreErr();
                        }
                        mRefreshlayout.setEnabled(true);
                        hideLoading();

                    }

                    @Override
                    public void onComplete() {
                        stopRefush();
                        if(mReadAdapter!=null&&mReadAdapter.getItemCount()>0) {
                            mReadAdapter.setEmptyView(R.layout.emptyview,mRecyclerview);
                        }

                        if(type==Constant.GET_DATA_TYPE_NORMAL) {
                            startLodingMore();
                        }else {
                            loadComplete();
                        }
                        mRefreshlayout.setEnabled(true);
                        hideLoading();

                    }
                });

    }

    private void initItemClickListener() {

        mReadAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ReadModel.NewslistBean newslistBean = (ReadModel.NewslistBean) adapter.getItem(position);

                startActivity(new Intent(getContext(), DetailActivity.class).putExtra("isfromme", true).putExtra("url",newslistBean.getUrl()));
            }
        });

    }

    private void initListener() {
        mRefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                getDateFromNet(Constant.GET_DATA_TYPE_NORMAL);

            }
        });

        mReadAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRefreshlayout.setEnabled(false);
                if(!mIsLoadMore) {

                    ToastUtils.showShort("没有更多数据了");
                    mReadAdapter.loadMoreEnd();
                    mRefreshlayout.setEnabled(true);
                }else {
                    startLodingMore();
                    mPage++;
                    getDateFromNet(Constant.GET_DATA_TYPE_LOADMORE);

                }

            }
        });
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
        mReadAdapter.setEnableLoadMore(true);
    }

    @Override
    public void stopLodingMore() {

        mReadAdapter.setEnableLoadMore(false);

    }

    @Override
    public void loadMoreErr() {
        mReadAdapter.loadMoreFail();

    }

    @Override
    public void loadComplete() {
        mReadAdapter.loadMoreComplete();

    }
}
