package com.rndchina.mygank.friendcircle;

import com.google.gson.Gson;
import com.rndchina.mygank.common.Constant;
import com.rndchina.mygank.friendcircle.model.FrinendCircleModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 2018/2/7.
 */

public class FriendCirclePresenter implements FriendCircleContact.Presenter {
    private FriendCircleContact.View mView;

    public FriendCirclePresenter(FriendCircleContact.View view) {
        mView = view;
    }

    @Override
    public void loadListData() {
        String json = Constant.GET_LIST;
        List<FrinendCircleModel.DataModel> modelList = new ArrayList<>();
        FrinendCircleModel model = new FrinendCircleModel();
        try {
            JSONObject jsonObject = new JSONObject(json);
            Gson gson = new Gson();
            String jsonModel = gson.toJson(json);

            model = gson.fromJson(jsonObject.toString(),FrinendCircleModel.class);
            mView.updateShow(model);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
