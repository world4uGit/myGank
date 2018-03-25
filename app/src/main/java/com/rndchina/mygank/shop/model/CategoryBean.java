package com.rndchina.mygank.shop.model;

import java.util.List;

/**
 * Created by PC on 2018/3/5.
 */

public class CategoryBean {

    /**
     * success : true
     * errorMsg :
     * result : [{"id":1,"bannerUrl":"/img/nz1.jpg","name":"潮流女装"},{"id":2,"bannerUrl":"/img/nz2.jpg","name":"品牌男装"},{"id":3,"bannerUrl":"/img/dn5.jpg","name":"电脑办公"},{"id":4,"bannerUrl":"/img/js3.jpg","name":"食品饮料"},{"id":5,"bannerUrl":"/img/dq4.jpg","name":"家用电器"},{"id":6,"bannerUrl":"/img/sj.jpg","name":"手机数码"},{"id":52,"name":"居家生活"},{"id":53,"name":"运动户外"},{"id":54,"name":"钟表珠宝"}]
     */

    private boolean success;
    private String errorMsg;
    private List<CategoryEntity> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<CategoryEntity> getResult() {
        return result;
    }

    public void setResult(List<CategoryEntity> result) {
        this.result = result;
    }

    public static class CategoryEntity {
        /**
         * id : 1
         * bannerUrl : /img/nz1.jpg
         * name : 潮流女装
         */

        private int id;
        private String bannerUrl;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBannerUrl() {
            return bannerUrl;
        }

        public void setBannerUrl(String bannerUrl) {
            this.bannerUrl = bannerUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
