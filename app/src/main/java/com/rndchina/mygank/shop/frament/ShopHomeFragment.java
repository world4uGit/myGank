package com.rndchina.mygank.shop.frament;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rndchina.mygank.R;
import com.rndchina.mygank.base.BaseView;
import com.rndchina.mygank.common.Constant;
import com.rndchina.mygank.image.ImageManager;
import com.rndchina.mygank.net.Api;
import com.rndchina.mygank.net.HttpManager;
import com.rndchina.mygank.shop.ALikeAdapter;
import com.rndchina.mygank.shop.ProductDetialsActivity;
import com.rndchina.mygank.shop.SecondAdapter;
import com.rndchina.mygank.shop.model.BannerBean;
import com.rndchina.mygank.shop.model.SecondBean;
import com.rndchina.mygank.shop.model.YLikeBean;
import com.wang.avi.AVLoadingIndicatorView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoaderInterface;

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
public class ShopHomeFragment extends Fragment implements BaseView{


    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.clock)
    ImageView mClock;
    @BindView(R.id.seckill_tip_tv)
    TextView mSeckillTipTv;
    @BindView(R.id.seckill_tv)
    TextView mSeckillTv;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.recommend_gv)
    RecyclerView mRecommendGv;
    @BindView(R.id.ll)
    LinearLayout mLl;
    @BindView(R.id.tv_nonetwork)
    TextView mTvNonetwork;
    @BindView(R.id.avi)
    AVLoadingIndicatorView mAvi;


    private Context mContext;
    private SecondAdapter mSecondAdapter;
    private ALikeAdapter mALikeAdapter;
    List<BannerBean.ResultEntity> bannerList = new ArrayList<>();
    List<SecondBean.ResultEntity.RowsEntity> secondList = new ArrayList<>();
    List<YLikeBean.ResultEntity.RowsEntity> ylikeList = new ArrayList<>();
    private List<String> images = new ArrayList<>();

    public ShopHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initAdapter();
        initListener();
        if(NetworkUtils.isConnected()) {
            showLoading();
            getDateFromNet();
        }else {
            mTvNonetwork.setVisibility(View.VISIBLE);
        }
    }

    private void initListener() {
        mSecondAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SecondBean.ResultEntity.RowsEntity item = (SecondBean.ResultEntity.RowsEntity) adapter.getItem(position);
                Intent intent = new Intent(getContext(), ProductDetialsActivity.class);
                intent.putExtra("productId",item.getProductId());
                startActivity(intent);
            }
        });
        mALikeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                YLikeBean.ResultEntity.RowsEntity item = (YLikeBean.ResultEntity.RowsEntity) adapter.getItem(position);
                Intent intent = new Intent(getContext(), ProductDetialsActivity.class);
                intent.putExtra("productId",item.getProductId());
                startActivity(intent);
            }
        });
    }

    private void initAdapter() {
        mSecondAdapter = new SecondAdapter(R.layout.second_item,secondList);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        mRecyclerview.setAdapter(mSecondAdapter);

        mALikeAdapter = new ALikeAdapter(R.layout.ylike_item,ylikeList);
        mRecommendGv.setLayoutManager(new GridLayoutManager(mContext,2,LinearLayoutManager.VERTICAL,false));
        mRecommendGv.setAdapter(mALikeAdapter);


    }

    private void getDateFromNet() {
        getBannerData();
        getSecondData();
        getListData();
    }

    private void getListData() {
        Api api = HttpManager.getInstance().getShopApiService();
        api.getYLikeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<YLikeBean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull YLikeBean yLikeBean) {
                if(!yLikeBean.isSuccess()) {
                    ToastUtils.showShort("服务器出错");
                }else {
                    ylikeList = yLikeBean.getResult().getRows();
                    mALikeAdapter.setNewData(ylikeList);
                    mALikeAdapter.notifyDataSetChanged();
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

    private void getSecondData() {
        Api api = HttpManager.getInstance().getShopApiService();
        api.getSecondData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SecondBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull SecondBean secondBean) {
                        if(!secondBean.isSuccess()) {
                            ToastUtils.showShort("服务器出错");
                        }else {
                            secondList = secondBean.getResult().getRows();
                            mSecondAdapter.setNewData(secondList);
                            mSecondAdapter.notifyDataSetChanged();
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

    private void getBannerData() {
        Api api = HttpManager.getInstance().getShopApiService();
        api.getBannerData()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<BannerBean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull BannerBean bannerBean) {
                if(!bannerBean.isSuccess()) {
                    ToastUtils.showShort("服务器异常");
                }else {
                    bannerList.clear();
                    images.clear();
                    bannerList = bannerBean.getResult();
                    for(int i = 0; i < bannerList.size(); i++) {
                        String imageUrl = Constant.BASE_SHOP_URL+bannerList.get(i).getAdUrl();
                        images.add(imageUrl);
                    }

                    mBanner.setImages(images)
                            .setImageLoader(new BannerImageLoader())
                            .start();

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

    private class BannerImageLoader implements ImageLoaderInterface {
        @Override
        public void displayImage(Context context, Object path, View imageView) {
            ImageManager.getInstance().loadImage(mContext,path, (ImageView) imageView);
        }

        @Override
        public View createImageView(Context context) {
            return null;
        }
    }
}
