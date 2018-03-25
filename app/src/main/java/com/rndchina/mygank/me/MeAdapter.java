package com.rndchina.mygank.me;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rndchina.mygank.R;
import com.rndchina.mygank.image.ImageManager;
import com.rndchina.mygank.me.model.VideoModel;

import java.util.List;

/**
 * Created by PC on 2018/1/21.
 */
public class MeAdapter extends BaseQuickAdapter<VideoModel,BaseViewHolder>{
    public MeAdapter(@LayoutRes int layoutResId, @Nullable List<VideoModel> data) {
        super(R.layout.item_video, data);
    }

    public MeAdapter(@Nullable List<VideoModel> data) {
        super(data);
    }

    public MeAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoModel item) {

        ImageManager.getInstance().loadImage(mContext,item.getResourceId(),(ImageView) helper.getView(R.id.item_me_iv_video));

    }
}
