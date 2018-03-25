package com.rndchina.mygank.shop.model;

import java.util.List;

/**
 * Created by PC on 2018/3/4.
 */

public class BannerBean {

    /**
     * success : true
     * errorMsg :
     * result : [{"id":1,"type":1,"adUrl":"/img/a1.jpg","webUrl":"","adKind":1},{"id":2,"type":2,"adUrl":"/img/ad6.jpg","webUrl":null,"adKind":2},{"id":3,"type":1,"adUrl":"/img/a5.jpg","webUrl":null,"adKind":1},{"id":4,"type":1,"adUrl":"/img/a4.jpg","webUrl":null,"adKind":1},{"id":5,"type":1,"adUrl":"/img/a3.jpg","webUrl":null,"adKind":1},{"id":6,"type":1,"adUrl":"/img/a2.jpg","webUrl":null,"adKind":1}]
     */

    private boolean success;
    private String errorMsg;
    private List<ResultEntity> result;

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

    public List<ResultEntity> getResult() {
        return result;
    }

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    public static class ResultEntity {
        /**
         * id : 1
         * type : 1
         * adUrl : /img/a1.jpg
         * webUrl :
         * adKind : 1
         */

        private int id;
        private int type;
        private String adUrl;
        private String webUrl;
        private int adKind;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getAdUrl() {
            return adUrl;
        }

        public void setAdUrl(String adUrl) {
            this.adUrl = adUrl;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        public int getAdKind() {
            return adKind;
        }

        public void setAdKind(int adKind) {
            this.adKind = adKind;
        }
    }
}
