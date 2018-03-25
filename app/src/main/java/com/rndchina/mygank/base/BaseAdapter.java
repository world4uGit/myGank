package com.rndchina.mygank.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rndchina.mygank.R;
import com.rndchina.mygank.db.GankModel;
import com.rndchina.mygank.image.ImageManager;
import com.rndchina.mygank.utils.TimeHelper;

import java.util.List;

/**
 * Created by PC on 2018/1/15.
 */

public class BaseAdapter extends BaseQuickAdapter<GankModel.ResultsBean,BaseViewHolder>{
    public BaseAdapter(@Nullable List<GankModel.ResultsBean> data) {
        super(data);
    }

    public BaseAdapter(@LayoutRes int layoutResId, @Nullable List<GankModel.ResultsBean> data) {
        super(R.layout.item_homefragment, data);
    }

    public BaseAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankModel.ResultsBean item) {
        helper.setText(R.id.tv_author, (String) item.getWho());
        helper.setText(R.id.tv_title,  item.getDesc());
        helper.setText(R.id.tv_time, TimeUtils.getFriendlyTimeSpanByNow(TimeHelper.formatDateFromStr(item.getPublishedAt())));
        if (item.getImages() != null && item.getImages().size() > 0&&!item.getImages().get(0).equals("")) {
            //显示图片
            helper.setVisible(R.id.iv_cover,true);

            ImageManager.getInstance()
                    .loadImage(mContext,
                            item.getImages().get(0),
                            (ImageView) helper.getView(R.id.iv_cover));

        } else {
            //隐藏图片
            helper.setGone(R.id.iv_cover,false);
        }


    }


}
