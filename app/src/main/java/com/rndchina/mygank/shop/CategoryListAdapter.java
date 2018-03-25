package com.rndchina.mygank.shop;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rndchina.mygank.R;
import com.rndchina.mygank.shop.model.CategoryListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 2018/3/5.
 */
public class CategoryListAdapter extends BaseQuickAdapter<CategoryListBean.CategoryListEntity,BaseViewHolder>{

    List<CategoryListBean.CategoryListEntity.ThirdCategoryEntity> mList = new ArrayList<>();

    public CategoryListAdapter(@LayoutRes int layoutResId, @Nullable List<CategoryListBean.CategoryListEntity> data) {
        super(R.layout.categorylist_item, data);
    }

    public CategoryListAdapter(@Nullable List<CategoryListBean.CategoryListEntity> data) {
        super(data);
    }

    public CategoryListAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryListBean.CategoryListEntity item) {

        helper.setText(R.id.tv_product_name,item.getName());
        mList = item.getThirdCategory();
        RecyclerView recyclerView = helper.getView(R.id.recyclerview);
        GridLayoutManager manager = new GridLayoutManager(mContext,3, LinearLayoutManager.VERTICAL,false);
        manager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(manager);
        if(mList!=null&&mList.size()!=0) {
            SubCategroyAdapter subCategroyAdapter = new SubCategroyAdapter(R.layout.subcategory_item,mList);
            recyclerView.setAdapter(subCategroyAdapter);
        }

    }
}
