package com.rndchina.mygank.friendcircle;

import com.rndchina.mygank.friendcircle.model.FrinendCircleModel;

/**
 * Created by PC on 2018/2/7.
 */

public interface FriendCircleContact {
    interface View{

        void setPresenter();
        /**
         * 更新界面列表数据
         * @param dataModel
         */
        void updateShow(FrinendCircleModel dataModel);


    }
    interface Presenter{
        /*
        获取列表数据
         */
        void loadListData();

    }
}
