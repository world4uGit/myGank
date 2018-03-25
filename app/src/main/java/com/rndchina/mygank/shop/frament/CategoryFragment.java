package com.rndchina.mygank.shop.frament;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.rndchina.mygank.net.Api;
import com.rndchina.mygank.net.HttpManager;
import com.rndchina.mygank.shop.CategoryAdapter;
import com.rndchina.mygank.shop.CategoryListAdapter;
import com.rndchina.mygank.shop.model.CategoryBean;
import com.rndchina.mygank.shop.model.CategoryListBean;
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
public class CategoryFragment extends Fragment implements BaseView {

    @BindView(R.id.tv_nonetwork)
    TextView mTvNonetwork;
    @BindView(R.id.avi)
    AVLoadingIndicatorView mAvi;
    @BindView(R.id.rv_catagery)
    RecyclerView mRvCatagery;
    @BindView(R.id.rv_catagerylist)
    RecyclerView mRvCatagerylist;

    private Context mContext;
    private CategoryAdapter mCategoryAdapter;
    private CategoryListAdapter mCategoryListAdapter;
    private List<CategoryBean.CategoryEntity> mCategoryList = new ArrayList<>();
    private List<CategoryListBean.CategoryListEntity> mSubList = new ArrayList<>();



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initAdapter();
        initListener();
        if(NetworkUtils.isConnected()) {
            showLoading();
            getDataFromNet();
        }else {

            mTvNonetwork.setVisibility(View.VISIBLE);
        }
    }

    private void initListener() {
        mCategoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mCategoryAdapter.setCurrentItemIndex(position);
                mCategoryAdapter.notifyDataSetChanged();
                CategoryBean.CategoryEntity categoryEntity = (CategoryBean.CategoryEntity) adapter.getItem(position);
                getListData(categoryEntity.getId());
            }
        });
    }

    private void getListData(int id) {
        Api api = HttpManager.getInstance().getShopApiService();
        api.getCategoryListData(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryListBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CategoryListBean categoryListBean) {

                        if(!categoryListBean.getSuccess()) {
                            ToastUtils.showShort("服务器出错");
                        }else {
                            mSubList = categoryListBean.getResult();
                            mCategoryListAdapter.setNewData(mSubList);
                            mCategoryListAdapter.notifyDataSetChanged();

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

    private void getDataFromNet() {
        Api api = HttpManager.getInstance().getShopApiService();
        api.getCategoryData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CategoryBean categoryBean) {
                        if(!categoryBean.isSuccess()) {
                            ToastUtils.showShort("服务器出错");

                        }else {

                            mCategoryList = categoryBean.getResult();
                            mCategoryAdapter.setNewData(mCategoryList);
                            mCategoryListAdapter.notifyDataSetChanged();

                            mRvCatagery.postDelayed(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    if(mRvCatagery.findViewHolderForAdapterPosition(0)!=null )
                                    {
                                        mRvCatagery.findViewHolderForAdapterPosition(0).itemView.performClick();
                                    }
                                }
                            },50);


                        }


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        hideLoading();

                    }

                    @Override
                    public void onComplete() {

                        hideLoading();

                    }
                });

    }

    private void initAdapter() {
        mCategoryAdapter = new CategoryAdapter(R.layout.category_item,mCategoryList);
        mRvCatagery.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mRvCatagery.setAdapter(mCategoryAdapter);


        mCategoryListAdapter = new CategoryListAdapter(R.layout.categorylist_item,mSubList);
        mRvCatagerylist.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mRvCatagerylist.setAdapter(mCategoryListAdapter);

    }

    @Override
    public void showLoading() {

        mAvi.smoothToShow();

    }

    @Override
    public void hideLoading() {
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

    }

    @Override
    public void loadComplete() {

    }


}
