package com.rndchina.mygank.friendcircle.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by dell on 2017-8-13.
 */

public class DynamicItemBean implements MultiItemEntity {
    public static final int TEXT = 1;
    public static final int IMG_TEXT = 2;
    public static final int ROUTE_IMG = 3;
    public static final int ROUTE_DATA = 4;
    private String dynamicid;
    private String userid;
    private String trip_uuid;
    private String dyna_type;
    private String dyna_line;
    private String dyna_content;
    private String dyna_share_num;
    private String dyna_praise_num;
    private String dyna_comment_num;
    private String dyna_collection_num;
    private String dyna_address;
    private String dyna_lng;
    private String dyna_lat;
    private String dyna_is_hot;
    private String dyna_is_role;
    private String status;
    private String create_time;
    private String update_time;
    private String user_sex;
    private String user_pics;
    private String user_nickname;
    private String car_icon;
    private String car_title;
    private int is_follow;
    private int is_praise;
    private String time_trans;
    private List<DynamicPraiseBean> praiseList;
    /**
     * picid : 192
     * userid : 44
     * dynamicid : 99
     * pic_small : /Uploads/Picture/20170811/598d704ec07f5.png
     * pic_big : /Uploads/Picture/20170811/598d704ec07f5.png
     * status : 1
     * create_time : 1502441550
     * update_time : 0
     */

    private List<DynamicPicBean> picsList;
    private List<DynamicCommentBean> commentList;

    public String getDynamicid() {
        return dynamicid;
    }

    public void setDynamicid(String dynamicid) {
        this.dynamicid = dynamicid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTrip_uuid() {
        return trip_uuid;
    }

    public void setTrip_uuid(String trip_uuid) {
        this.trip_uuid = trip_uuid;
    }

    public String getDyna_type() {
        return dyna_type;
    }

    public void setDyna_type(String dyna_type) {
        this.dyna_type = dyna_type;
    }

    public String getDyna_line() {
        return dyna_line;
    }

    public void setDyna_line(String dyna_line) {
        this.dyna_line = dyna_line;
    }

    public String getDyna_content() {
        return dyna_content;
    }

    public void setDyna_content(String dyna_content) {
        this.dyna_content = dyna_content;
    }

    public String getDyna_share_num() {
        return dyna_share_num;
    }

    public void setDyna_share_num(String dyna_share_num) {
        this.dyna_share_num = dyna_share_num;
    }

    public String getDyna_praise_num() {
        return dyna_praise_num;
    }

    public void setDyna_praise_num(String dyna_praise_num) {
        this.dyna_praise_num = dyna_praise_num;
    }

    public String getDyna_comment_num() {
        return dyna_comment_num;
    }

    public void setDyna_comment_num(String dyna_comment_num) {
        this.dyna_comment_num = dyna_comment_num;
    }

    public String getDyna_collection_num() {
        return dyna_collection_num;
    }

    public void setDyna_collection_num(String dyna_collection_num) {
        this.dyna_collection_num = dyna_collection_num;
    }

    public String getDyna_address() {
        return dyna_address;
    }

    public void setDyna_address(String dyna_address) {
        this.dyna_address = dyna_address;
    }

    public String getDyna_lng() {
        return dyna_lng;
    }

    public void setDyna_lng(String dyna_lng) {
        this.dyna_lng = dyna_lng;
    }

    public String getDyna_lat() {
        return dyna_lat;
    }

    public void setDyna_lat(String dyna_lat) {
        this.dyna_lat = dyna_lat;
    }

    public String getDyna_is_hot() {
        return dyna_is_hot;
    }

    public void setDyna_is_hot(String dyna_is_hot) {
        this.dyna_is_hot = dyna_is_hot;
    }

    public String getDyna_is_role() {
        return dyna_is_role;
    }

    public void setDyna_is_role(String dyna_is_role) {
        this.dyna_is_role = dyna_is_role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_pics() {
        return user_pics;
    }

    public void setUser_pics(String user_pics) {
        this.user_pics = user_pics;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getCar_icon() {
        return car_icon;
    }

    public void setCar_icon(String car_icon) {
        this.car_icon = car_icon;
    }

    public String getCar_title() {
        return car_title;
    }

    public void setCar_title(String car_title) {
        this.car_title = car_title;
    }

    public int getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(int is_follow) {
        this.is_follow = is_follow;
    }

    public int getIs_praise() {
        return is_praise;
    }

    public void setIs_praise(int is_praise) {
        this.is_praise = is_praise;
    }

    public String getTime_trans() {
        return time_trans;
    }

    public void setTime_trans(String time_trans) {
        this.time_trans = time_trans;
    }

    public List<DynamicPraiseBean> getPraiseList() {
        return praiseList;
    }

    public void setPraiseList(List<DynamicPraiseBean> praiseList) {
        this.praiseList = praiseList;
    }

    public List<DynamicPicBean> getPicsList() {
        return picsList;
    }

    public void setPicsList(List<DynamicPicBean> picsList) {
        this.picsList = picsList;
    }

    public List<DynamicCommentBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<DynamicCommentBean> commentList) {
        this.commentList = commentList;
    }

    @Override
    public int getItemType() {
        return Integer.parseInt(dyna_type);
    }

    private class DynamicPraiseBean {
        private String userid;
        private String user_nickname;
        private String dyusid;

        public String getDyusid() {
            return dyusid;
        }

        public void setDyusid(String dyusid) {
            this.dyusid = dyusid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }
    }

    private class DynamicPicBean {
        private String picid;
        private String userid;
        private String dynamicid;
        private String pic_small;
        private String pic_big;
        private String status;
        private String create_time;
        private String update_time;

        public String getPicid() {
            return picid;
        }

        public void setPicid(String picid) {
            this.picid = picid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getDynamicid() {
            return dynamicid;
        }

        public void setDynamicid(String dynamicid) {
            this.dynamicid = dynamicid;
        }

        public String getPic_small() {
            return pic_small;
        }

        public void setPic_small(String pic_small) {
            this.pic_small = pic_small;
        }

        public String getPic_big() {
            return pic_big;
        }

        public void setPic_big(String pic_big) {
            this.pic_big = pic_big;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }

    private class DynamicCommentBean {
        private String commentid;
        private String userid;
        private String userto_id;
        private String com_level;
        private String com_content;
        private String user_nickname;
        private String userto_nickname;

        public String getCommentid() {
            return commentid;
        }

        public void setCommentid(String commentid) {
            this.commentid = commentid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUserto_id() {
            return userto_id;
        }

        public void setUserto_id(String userto_id) {
            this.userto_id = userto_id;
        }

        public String getCom_level() {
            return com_level;
        }

        public void setCom_level(String com_level) {
            this.com_level = com_level;
        }

        public String getCom_content() {
            return com_content;
        }

        public void setCom_content(String com_content) {
            this.com_content = com_content;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getUserto_nickname() {
            return userto_nickname;
        }

        public void setUserto_nickname(String userto_nickname) {
            this.userto_nickname = userto_nickname;
        }
    }
}
