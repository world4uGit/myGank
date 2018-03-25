package com.rndchina.mygank.base;

/**
 * Created by PC on 2018/1/14.
 */

public interface BaseView {
    //展示加载动画
    void showLoading();

    //隐藏等待加载动画
    void hideLoading();


    //隐藏下拉刷新界面
    void stopRefush();

    //显示上拉加载更多界面
    void startLodingMore();

    //隐藏上拉加载更多
    void stopLodingMore();


    //下拉加载出错
    void loadMoreErr();


    //隐藏下拉刷新界面
    void loadComplete();
}
