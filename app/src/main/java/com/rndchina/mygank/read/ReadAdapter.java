package com.rndchina.mygank.read;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rndchina.mygank.R;
import com.rndchina.mygank.image.ImageManager;
import com.rndchina.mygank.read.model.ReadModel;
import com.rndchina.mygank.utils.TimeHelper;

import java.util.List;

/**
 * Created by PC on 2018/1/22.
 */
public class ReadAdapter extends BaseQuickAdapter<ReadModel.NewslistBean,BaseViewHolder>{

    public ReadAdapter(@LayoutRes int layoutResId, @Nullable List<ReadModel.NewslistBean> data) {
        super(R.layout.item_homefragment, data);
    }

    public ReadAdapter(@Nullable List<ReadModel.NewslistBean> data) {
        super(data);
    }

    public ReadAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, ReadModel.NewslistBean item) {
        helper.setText(R.id.tv_title,  item.getTitle());
        helper.setText(R.id.tv_author,  item.getDescription());
        helper.setText(R.id.tv_time, TimeUtils.getFriendlyTimeSpanByNow(TimeHelper.formatDateFromStr(item.getCtime())));
        if (!StringUtils.isEmpty(item.getPicUrl()) ) {
            //显示图片

            ImageManager.getInstance()
                    .loadImage(mContext,
                            item.getPicUrl(),
                            (ImageView) helper.getView(R.id.iv_cover));
        } else {
            //隐藏图片
            helper.setGone(R.id.iv_cover,false);
        }
    }
}
