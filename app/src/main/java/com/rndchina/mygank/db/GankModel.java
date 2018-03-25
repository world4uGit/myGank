package com.rndchina.mygank.db;

import java.io.Serializable;
import java.util.List;

/**
 * Created by PC on 2018/1/13.
 */

public class GankModel {

    /**
     * error : false
     * results : [{"_id":"5a4af266421aa90fe50c02b6","createdAt":"2018-01-02T10:45:58.490Z","desc":"一个简洁、实用、方便的Android异步处理库，已应用到百万日活的线上项目中","publishedAt":"2018-01-10T07:57:19.486Z","source":"web","type":"Android","url":"https://github.com/SilenceDut/TaskScheduler","used":true,"who":null},{"_id":"5a4df36c421aa90fe2f02d26","createdAt":"2018-01-04T17:27:08.992Z","desc":"一款好用、可自定义状态的数据状态切换布局","images":["http://img.gank.io/c141c075-afdf-4f8f-9d17-ec41faa10e75"],"publishedAt":"2018-01-10T07:57:19.486Z","source":"web","type":"Android","url":"https://github.com/Bakumon/StatusLayoutManager/blob/master/README.md","used":true,"who":"马飞"},{"_id":"5a531646421aa90fe725370a","createdAt":"2018-01-08T14:57:10.956Z","desc":"Android自定义 View - 仿淘宝 淘抢购进度条","images":["http://img.gank.io/b1a4758e-6fb1-42b7-b197-7dc2d36a20d0"],"publishedAt":"2018-01-10T07:57:19.486Z","source":"web","type":"Android","url":"https://github.com/zhlucky/SaleProgressView","used":true,"who":null},{"_id":"5a54667f421aa90fe50c02d0","createdAt":"2018-01-09T14:51:43.528Z","desc":"探究Android View 绘制流程，Xml 文件到 View 对象的转换过程","images":["http://img.gank.io/4dd0f9e4-a016-4579-a1cd-73f56f05a466"],"publishedAt":"2018-01-10T07:57:19.486Z","source":"web","type":"Android","url":"https://www.jianshu.com/p/eccd8ba87e8b","used":true,"who":"Kai Sun"},{"_id":"5a07b7fe421aa90fe7253624","createdAt":"2017-11-12T10:54:54.391Z","desc":"应用模块化和懒加载在 Instagram 中的实现","publishedAt":"2018-01-04T13:45:57.211Z","source":"chrome","type":"Android","url":"https://github.com/Instagram/ig-lazy-module-loader","used":true,"who":"vincgao"},{"_id":"5a07e798421aa90fef20351c","createdAt":"2017-11-12T14:18:00.758Z","desc":"带来高收入的 10 大开源技术","publishedAt":"2018-01-04T13:45:57.211Z","source":"web","type":"Android","url":"https://mp.weixin.qq.com/s?__biz=MzIyMjQ0MTU0NA==&mid=2247484681&idx=1&sn=814c2799270911211c1ca2679b2b1dae&chksm=e82c3c2edf5bb538f929e1bad6d93af3c2a64f29125219f892875f9f3705a7809dee954b4859&mpshare=1&scene=1&srcid=1112Xqhm9lOPXS8nzaQQwBWn&key=bfc872d4a5d909acdba60130fbee50669b02772b3b48a1d612fdf0c4f16d2275b40855559612872361d1b8216a50791a9414df4f25ca3b1bbaac45b1fc2a5a6db68d60509170e222270da24f293c93af&ascene=0&uin=MjMzMzgwOTEwMQ%3D%3D&devicetype=iMac+MacBookPro12%2C1+OSX+OSX+10.10.5+build(14F27)&version=11020201&pass_ticket=ou7zYvjxcdHOv5jQYjvGT8YTTADmWIwriTFISUiYMatR1c7FfFAxWJTAwdm7Fc58","used":true,"who":"Tamic (码小白)"},{"_id":"5a0aa939421aa90fe7253638","createdAt":"2017-11-14T16:28:41.513Z","desc":"Kotlin 扩展函数实现原理","images":["http://img.gank.io/7b82e6e5-4557-4e6c-9aa8-db86ffe25160"],"publishedAt":"2018-01-04T13:45:57.211Z","source":"web","type":"Android","url":"http://caimuhao.com/2017/11/14/How-Kotlin-implements-extension-function/","used":true,"who":null},{"_id":"5a4362db421aa90fe50c02a9","createdAt":"2017-12-27T17:07:39.802Z","desc":"图解RxJava2(三)","images":["http://img.gank.io/40c7c720-b439-4e77-9b3b-12ce12b6eb6a"],"publishedAt":"2018-01-04T13:45:57.211Z","source":"web","type":"Android","url":"http://rkhcy.github.io/2017/12/22/%E5%9B%BE%E8%A7%A3RxJava2(%E4%B8%89)/","used":true,"who":"HuYounger"},{"_id":"5a4c37d0421aa90fe2f02d22","createdAt":"2018-01-03T09:54:24.402Z","desc":"kotlin实现的Android Spannable API","images":["http://img.gank.io/ce18d361-fc65-4a05-aea3-a43f09c1bb37"],"publishedAt":"2018-01-04T13:45:57.211Z","source":"web","type":"Android","url":"https://github.com/2dxgujun/span","used":true,"who":"Jun Gu"},{"_id":"5a4ca71e421aa90fe50c02be","createdAt":"2018-01-03T17:49:18.880Z","desc":"日食加载动画","images":["http://img.gank.io/eba7a8cf-b9fc-4ece-8b3f-d093cb1da70a"],"publishedAt":"2018-01-04T13:45:57.211Z","source":"web","type":"Android","url":"https://github.com/Bakumon/EclipseLoading","used":true,"who":"马飞"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public void setError(boolean error) {
        this.error = error;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public boolean getError() {
        return error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public static class ResultsBean implements Serializable {
        public ResultsBean(String _id, String createdAt, String desc, String publishedAt, String source, String type, String url, boolean used, Object who, List<String> images) {
            this._id = _id;
            this.createdAt = createdAt;
            this.desc = desc;
            this.publishedAt = publishedAt;
            this.source = source;
            this.type = type;
            this.url = url;
            this.used = used;
            this.who = who;
            this.images = images;
        }

        /**
         * _id : 5a4af266421aa90fe50c02b6
         * createdAt : 2018-01-02T10:45:58.490Z
         * desc : 一个简洁、实用、方便的Android异步处理库，已应用到百万日活的线上项目中
         * publishedAt : 2018-01-10T07:57:19.486Z
         * source : web
         * type : Android
         * url : https://github.com/SilenceDut/TaskScheduler
         * used : true
         * who : null
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private Object who;
        private List<String> images;

        public boolean isUsed() {
            return used;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public void setWho(Object who) {
            this.who = who;
        }

        public String get_id() {
            return _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public String getSource() {
            return source;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }

        public boolean getUsed() {
            return used;
        }

        public Object getWho() {
            return who;
        }
    }
}
