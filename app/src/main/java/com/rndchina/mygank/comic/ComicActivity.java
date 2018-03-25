package com.rndchina.mygank.comic;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.rndchina.mygank.R;
import com.rndchina.mygank.base.BaseActivity;
import com.rndchina.mygank.comic.model.ComicListInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComicActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private View mView;
    private ComicListInfo comicListInfo;
    List<ComicListInfo.EntriesBean> mList = new ArrayList<>();
    private ComicAdapter mComicAdapter;


    @Override
    protected void initOptions() {
        ButterKnife.bind(this);

        initData();
        initAdapter();
        initListener();

    }

    private void initListener() {
        mComicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ComicActivity.this, ComicListDetailActivity.class);
                intent.putExtra("info", (ComicListInfo.EntriesBean) adapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    private void initData() {
        comicListInfo = new Gson().fromJson(JsonTest.home, ComicListInfo.class);
        mList = comicListInfo.getEntries();
    }

    private void initAdapter() {
        mComicAdapter = new ComicAdapter(R.layout.comic_item,mList);
        mRecyclerview.setLayoutManager(new GridLayoutManager(this,3, LinearLayoutManager.VERTICAL,false));
        mRecyclerview.setAdapter(mComicAdapter);

    }

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_comic, null);
        return mView;
    }

    @Override
    protected String initToolbarTitle() {
        return "4U漫画";
    }

}
