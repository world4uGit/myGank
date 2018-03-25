package com.rndchina.mygank.friendcircle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rndchina.mygank.R;
import com.rndchina.mygank.base.BaseActivity;
import com.rndchina.mygank.friendcircle.model.FrinendCircleModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendCircleActivity extends BaseActivity implements FriendCircleContact.View {


    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.sw_find)
    SwipeRefreshLayout mSwFind;
    private FriendCirclePresenter mFriendCirclePresenter;
    private View mView;
    private List<FrinendCircleModel.DataModel.DynaListModel> mList = new ArrayList<>();
    private FriendCircleAdapter mFriendCircleAdapter;


    @Override
    protected void initOptions() {
        ButterKnife.bind(this);
        setPresenter();
        mRecyclerview.setLayoutManager(new LinearLayoutManager(FriendCircleActivity.this,LinearLayoutManager.VERTICAL,false));
        mFriendCircleAdapter = new FriendCircleAdapter(this,mList);
        mRecyclerview.setAdapter(mFriendCircleAdapter);
        mFriendCirclePresenter.loadListData();


    }

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_friend_circle, null);
        return mView;
    }

    @Override
    protected String initToolbarTitle() {
        return "朋友圈";
    }

    @Override
    public void setPresenter() {

        mFriendCirclePresenter = new FriendCirclePresenter(this);

    }

    @Override
    public void updateShow(FrinendCircleModel frinendCircleModel) {
        mList.clear();
        mList = frinendCircleModel.getData().getDynaList();
        mFriendCircleAdapter.setNewData(mList);
        mFriendCircleAdapter.notifyDataSetChanged();
    }

}
