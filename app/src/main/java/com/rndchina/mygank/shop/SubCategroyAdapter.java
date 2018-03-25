package com.rndchina.mygank.shop;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rndchina.mygank.R;
import com.rndchina.mygank.common.Constant;
import com.rndchina.mygank.image.ImageManager;
import com.rndchina.mygank.shop.model.CategoryListBean;

import java.util.List;

/**
 * Created by PC on 2018/3/6.
 */
public class SubCategroyAdapter extends BaseQuickAdapter<CategoryListBean.CategoryListEntity.ThirdCategoryEntity,BaseViewHolder>{
    public SubCategroyAdapter(@LayoutRes int layoutResId, @Nullable List<CategoryListBean.CategoryListEntity.ThirdCategoryEntity> data) {
        super(R.layout.subcategory_item, data);
    }

    public SubCategroyAdapter(@Nullable List<CategoryListBean.CategoryListEntity.ThirdCategoryEntity> data) {
        super(data);
    }

    public SubCategroyAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryListBean.CategoryListEntity.ThirdCategoryEntity item) {

        ImageManager.getInstance().loadImage(mContext, Constant.BASE_SHOP_URL+item.getBannerUrl(), (ImageView) helper.getView(R.id.iv_image));



    }
}
