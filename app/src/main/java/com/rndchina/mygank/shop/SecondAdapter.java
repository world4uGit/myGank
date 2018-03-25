package com.rndchina.mygank.shop;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rndchina.mygank.R;
import com.rndchina.mygank.common.Constant;
import com.rndchina.mygank.image.ImageManager;
import com.rndchina.mygank.shop.model.SecondBean;

import java.util.List;

/**
 * Created by PC on 2018/3/4.
 */
public class SecondAdapter extends BaseQuickAdapter<SecondBean.ResultEntity.RowsEntity,BaseViewHolder>{
    public SecondAdapter(@LayoutRes int layoutResId, @Nullable List<SecondBean.ResultEntity.RowsEntity> data) {
        super(R.layout.second_item, data);
    }

    public SecondAdapter(@Nullable List<SecondBean.ResultEntity.RowsEntity> data) {
        super(data);
    }

    public SecondAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SecondBean.ResultEntity.RowsEntity item) {
        helper.setText(R.id.nowprice_tv,item.getPointPrice()+"");
        helper.setText(R.id.normalprice_tv,item.getAllPrice()+"");
        ImageManager.getInstance().loadImage(mContext, Constant.BASE_SHOP_URL+item.getIconUrl(), (ImageView) helper.getView(R.id.image_iv));



    }
}
