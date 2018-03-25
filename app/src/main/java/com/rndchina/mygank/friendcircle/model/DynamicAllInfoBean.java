package com.rndchina.mygank.friendcircle.model;

import java.util.List;

/**
 * Created by dell on 2017-8-13.
 */

public class DynamicAllInfoBean {
    private List<DynamicItemBean> dynaList;
    private List<DynamicBannerBean> bannerList;
    private List<DynamicToUserBean> touserInfo;

    public List<DynamicItemBean> getDynaList() {
        return dynaList;
    }

    public void setDynaList(List<DynamicItemBean> dynaList) {
        this.dynaList = dynaList;
    }

    public List<DynamicBannerBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<DynamicBannerBean> bannerList) {
        this.bannerList = bannerList;
    }

    public List<DynamicToUserBean> getTouserInfo() {
        return touserInfo;
    }

    public void setTouserInfo(List<DynamicToUserBean> touserInfo) {
        this.touserInfo = touserInfo;
    }
}
