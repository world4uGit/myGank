package com.rndchina.mygank.shop;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rndchina.mygank.R;
import com.rndchina.mygank.common.Constant;
import com.rndchina.mygank.image.ImageManager;
import com.rndchina.mygank.shop.model.YLikeBean;

import java.util.List;

/**
 * Created by PC on 2018/3/4.
 */
public class ALikeAdapter extends BaseQuickAdapter<YLikeBean.ResultEntity.RowsEntity,BaseViewHolder>{
    public ALikeAdapter(@LayoutRes int layoutResId, @Nullable List<YLikeBean.ResultEntity.RowsEntity> data) {
        super(R.layout.ylike_item, data);
    }

    public ALikeAdapter(@Nullable List<YLikeBean.ResultEntity.RowsEntity> data) {
        super(data);
    }

    public ALikeAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, YLikeBean.ResultEntity.RowsEntity item) {
        helper.setText(R.id.name_tv,item.getName()+"");
        helper.setText(R.id.price_tv,item.getPrice()+"");
        ImageManager.getInstance().loadImage(mContext, Constant.BASE_SHOP_URL+item.getIconUrl(), (ImageView) helper.getView(R.id.image_iv));


    }
}
