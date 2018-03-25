package com.rndchina.mygank.search;

import android.content.Context;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.rndchina.mygank.common.Constant;
import com.rndchina.mygank.common.IPreference;
import com.rndchina.mygank.common.PreferenceImpl;
import com.rndchina.mygank.db.GankModel;
import com.rndchina.mygank.net.Api;
import com.rndchina.mygank.net.HttpManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PC on 2018/2/4.
 */

public class SearchPresenter implements SearchContract.Prestener{

    private PreferenceImpl mPreUtils;
    private SearchContract.View mView;
    private final String HISTORY_SEARCHA = "history_search";
    private List<String> mHistoryTitles;
    Disposable mDisposable;

    public SearchPresenter(SearchContract.View view) {
        mView = view;
    }

    @Override
    public List<String> loadHistroyData() {
        mPreUtils = (PreferenceImpl) IPreference.prefHolder.getPreference((Context) mView, HISTORY_SEARCHA);
        mHistoryTitles = new ArrayList<>();
        if (mPreUtils.getAll(HISTORY_SEARCHA) != null) {
            mHistoryTitles = mPreUtils.getAll(HISTORY_SEARCHA);
        }
        return mHistoryTitles;
    }

    @Override
    public List<String> loadHotTag() {
        List<String> mHotTitles = new ArrayList<String>();
        mHotTitles.add("RxJava");
        mHotTitles.add("RxAndroid");
        mHotTitles.add("数据库");
        mHotTitles.add("自定义控件");
        mHotTitles.add("下拉刷新");
        mHotTitles.add("mvp");
        mHotTitles.add("直播");
        mHotTitles.add("权限管理");
        mHotTitles.add("Retrofit");
        mHotTitles.add("OkHttp");
        mHotTitles.add("WebWiew");
        mHotTitles.add("热修复");
        return mHotTitles;
    }

    @Override
    public void addHistorySearch(String keyword) {

        mHistoryTitles.add(keyword);
        mPreUtils.putAll(HISTORY_SEARCHA, mHistoryTitles);

    }

    @Override
    public void clearAllHistory(String tag) {

        mPreUtils.remove(tag);

    }

    @Override
    public void getDataFromService(String keyword, int page, final int type) {

        Api api = HttpManager.getInstance().getApiService();
        api.getSearchData(keyword,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull GankModel gankModel) {
                        if(gankModel.getError()) {
                            mView.showErrorTip("服务器异常");
                            return;
                        }
                        //显示搜索结果
                        mView.showSearchResult(true);
                        mView.showLoading();
                        mView.updateShow(gankModel,type);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if(type==Constant.GET_DATA_TYPE_LOADMORE) {

                            mView.loadMoreErr();

                        }
                        mView.hideLoading();
                    }

                    @Override
                    public void onComplete() {

                        mView.setEmptyView();

                        if(type==Constant.GET_DATA_TYPE_LOADMORE) {

                            mView.loadComplete();

                        }
                        mView.hideLoading();
                    }
                });

    }

    @Override
    public void searchFromServer(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            ToastUtils.showShort("请输入搜索条件");
            return;
        }
        mHistoryTitles.add(keyword);
        mPreUtils.putAll(HISTORY_SEARCHA, mHistoryTitles);


        getDataFromService(keyword, 1, Constant.GET_DATA_TYPE_NORMAL);


    }

    @Override
    public void historyClick(String keyword) {
        //显示搜索结果
        mView.showSearchResult(true);
        mView.showLoading();
        mView.setEditText(keyword);
        getDataFromService(keyword, 1, Constant.GET_DATA_TYPE_NORMAL);

    }

    public void saveHistoryData(List<String> historyTitles) {
        mPreUtils.putAll(HISTORY_SEARCHA, historyTitles);
    }

    public void stopDis() {
        mDisposable.dispose();
    }
}
