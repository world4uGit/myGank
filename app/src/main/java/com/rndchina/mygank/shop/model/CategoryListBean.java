package com.rndchina.mygank.shop.model;

import java.util.List;

/**
 * Created by PC on 2018/3/5.
 */

public class CategoryListBean {

    /**
     * success : true
     * errorMsg :
     * result : [{"id":16,"name":"衬衫","thirdCategory":[{"id":20,"bannerUrl":"/img/nz/56d64289N1844b52e.jpg","name":"纯棉衬衫"},{"id":21,"bannerUrl":"/img/nz/56d64457Nf5030e02.jpg","name":"格子衬衫"}]},{"id":17,"name":"T恤","thirdCategory":[{"id":22,"bannerUrl":"/img/nz/562345f8N9b7c9aff.jpg","name":"纯色T恤"},{"id":23,"bannerUrl":"/img/nz/562444dcN4eb56626.jpg","name":"POLO衫"}]},{"id":18,"name":"上装","thirdCategory":[{"id":24,"bannerUrl":"/img/nz/56234dc2N511b596d.jpg","name":"西服"},{"id":25,"bannerUrl":"/img/nz/56234e4eNd99c77d0.jpg","name":"风衣"},{"id":26,"bannerUrl":"/img/nz/56234e9cN2f3ac326.jpg","name":"毛呢大衣"}]},{"id":19,"name":"裤装","thirdCategory":[{"id":27,"bannerUrl":"/img/nz/56235041N93b3d8f0.jpg","name":"西裤"},{"id":28,"bannerUrl":"/img/nz/562350bfN19049375.jpg","name":"短裤"}]}]
     */

    private boolean success;
    private String errorMsg;
    private List<CategoryListEntity> result;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setResult(List<CategoryListEntity> result) {
        this.result = result;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public List<CategoryListEntity> getResult() {
        return result;
    }

    public static class CategoryListEntity {
        /**
         * id : 16
         * name : 衬衫
         * thirdCategory : [{"id":20,"bannerUrl":"/img/nz/56d64289N1844b52e.jpg","name":"纯棉衬衫"},{"id":21,"bannerUrl":"/img/nz/56d64457Nf5030e02.jpg","name":"格子衬衫"}]
         */

        private int id;
        private String name;
        private List<ThirdCategoryEntity> thirdCategory;

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setThirdCategory(List<ThirdCategoryEntity> thirdCategory) {
            this.thirdCategory = thirdCategory;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public List<ThirdCategoryEntity> getThirdCategory() {
            return thirdCategory;
        }

        public static class ThirdCategoryEntity {
            /**
             * id : 20
             * bannerUrl : /img/nz/56d64289N1844b52e.jpg
             * name : 纯棉衬衫
             */

            private int id;
            private String bannerUrl;
            private String name;

            public void setId(int id) {
                this.id = id;
            }

            public void setBannerUrl(String bannerUrl) {
                this.bannerUrl = bannerUrl;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public String getBannerUrl() {
                return bannerUrl;
            }

            public String getName() {
                return name;
            }
        }
    }
}
