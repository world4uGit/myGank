package com.rndchina.mygank.collect;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rndchina.mygank.R;
import com.rndchina.mygank.base.BaseActivity;
import com.rndchina.mygank.base.BaseAdapter;
import com.rndchina.mygank.db.DbManager;
import com.rndchina.mygank.db.GankModel;
import com.rndchina.mygank.db.SaveModel;
import com.rndchina.mygank.detail.DetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectActivity extends BaseActivity {


    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private View mView;

    private List<SaveModel> mSaveModelList;
    private BaseAdapter mBaseAdapter;
    private List<GankModel.ResultsBean> mList = new ArrayList<>();


    @Override
    protected void initOptions() {
        ButterKnife.bind(this);
        //设置适配器，展示数据
        mRecyclerview.setLayoutManager(new LinearLayoutManager(CollectActivity.this, LinearLayoutManager.VERTICAL, false));
        //加载数据库数据
        loadCollectionData();
        mBaseAdapter = new BaseAdapter(R.layout.item_homefragment, mList);
        mRecyclerview.setAdapter(mBaseAdapter);
        if(mList.size()==0) {
            mBaseAdapter.setEmptyView(R.layout.emptyview,mRecyclerview);
        }

        mBaseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(CollectActivity.this, DetailActivity.class);
                GankModel.ResultsBean entity = (GankModel.ResultsBean) adapter.getItem(position);
//                String images = "";
//                if (entity.getImages() != null && entity.getImages().size() > 0) {
//                    images = entity.getImages().get(0);
//                }
                intent.putExtra("entity", entity);
                startActivity(intent);

            }
        });


    }

    private void loadCollectionData() {
        mSaveModelList = DbManager.getInstence().queryAll(SaveModel.class);
        if(mSaveModelList!=null&&mSaveModelList.size()>0) {
            initAdapterList(mSaveModelList);
        }

    }

    private void initAdapterList(List<SaveModel> saveModelList) {
        mList.clear();
        for (SaveModel savemodel : saveModelList) {
            List<String> images = new ArrayList<>();
            images.add(savemodel.getImages());
            GankModel.ResultsBean entity = new GankModel.ResultsBean(
                    savemodel.get_id(),
                    savemodel.getCreatedAt(),
                    savemodel.getDesc(),
                    savemodel.getPublishedAt(),
                    savemodel.getSource(),
                    savemodel.getType(),
                    savemodel.getUrl(),
                    savemodel.getUsed(),
                    savemodel.getWho(),
                    images);
            mList.add(entity);
        }
    }

    @Override
    protected void updateOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_save).setVisible(false);
        menu.findItem(R.id.action_download).setVisible(false);
        menu.findItem(R.id.action_share).setVisible(false);
    }

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_collect, null);

        return mView;
    }

    @Override
    protected String initToolbarTitle() {
        return "我的收藏";
    }

}
