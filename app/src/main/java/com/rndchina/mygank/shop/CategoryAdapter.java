package com.rndchina.mygank.shop;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rndchina.mygank.R;
import com.rndchina.mygank.shop.model.CategoryBean;

import java.util.List;

/**
 * Created by PC on 2018/3/5.
 */
public class CategoryAdapter extends BaseQuickAdapter<CategoryBean.CategoryEntity,BaseViewHolder>{

    private int currentItemIndex = 0;


    public void setCurrentItemIndex(int currentItemIndex){
        this.currentItemIndex=currentItemIndex;
    }

    public CategoryAdapter(@LayoutRes int layoutResId, @Nullable List<CategoryBean.CategoryEntity> data) {
        super(R.layout.category_item, data);
    }

    public CategoryAdapter(@Nullable List<CategoryBean.CategoryEntity> data) {
        super(data);
    }

    public CategoryAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean.CategoryEntity item) {

        helper.setText(R.id.tv,item.getName());
        TextView textView = helper.getView(R.id.tv);
        if (helper.getAdapterPosition() == currentItemIndex) {
            textView.setSelected(true);
        } else {
            textView.setSelected(false);
        }

    }
}
