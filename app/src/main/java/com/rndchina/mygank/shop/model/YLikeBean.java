package com.rndchina.mygank.shop.model;

import java.util.List;

/**
 * Created by PC on 2018/3/4.
 */

public class YLikeBean {

    /**
     * success : true
     * errorMsg :
     * result : {"total":10,"rows":[{"price":15,"name":"卜珂空气巧克力纯可可脂黑巧克力","iconUrl":"/img/p1.jpg","productId":1},{"price":99,"name":"芭喜/Baci 意大利进口婚庆喜糖盒装巧克力 榛仁黑巧克力100颗 年货量贩装 情人节礼物 黑巧","iconUrl":"/img/p/bx1.jpg","productId":6},{"price":16,"name":"士力架花生夹心巧克力家庭装460g（加量装随机发送）","iconUrl":"/img/p/lsj1.jpg","productId":2},{"price":19,"name":"歌斐颂 纯可可脂纯黑巧克力盒装320g 休闲零食大礼包糖果礼盒装","iconUrl":"/img/p/g2.jpg","productId":4}]}
     */

    private boolean success;
    private String errorMsg;
    private ResultEntity result;

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

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public static class ResultEntity {
        /**
         * total : 10
         * rows : [{"price":15,"name":"卜珂空气巧克力纯可可脂黑巧克力","iconUrl":"/img/p1.jpg","productId":1},{"price":99,"name":"芭喜/Baci 意大利进口婚庆喜糖盒装巧克力 榛仁黑巧克力100颗 年货量贩装 情人节礼物 黑巧","iconUrl":"/img/p/bx1.jpg","productId":6},{"price":16,"name":"士力架花生夹心巧克力家庭装460g（加量装随机发送）","iconUrl":"/img/p/lsj1.jpg","productId":2},{"price":19,"name":"歌斐颂 纯可可脂纯黑巧克力盒装320g 休闲零食大礼包糖果礼盒装","iconUrl":"/img/p/g2.jpg","productId":4}]
         */

        private int total;
        private List<RowsEntity> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsEntity> getRows() {
            return rows;
        }

        public void setRows(List<RowsEntity> rows) {
            this.rows = rows;
        }

        public static class RowsEntity {
            /**
             * price : 15
             * name : 卜珂空气巧克力纯可可脂黑巧克力
             * iconUrl : /img/p1.jpg
             * productId : 1
             */

            private int price;
            private String name;
            private String iconUrl;
            private int productId;

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }
        }
    }
}
