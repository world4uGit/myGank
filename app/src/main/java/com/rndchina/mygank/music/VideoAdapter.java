package com.rndchina.mygank.music;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rndchina.mygank.R;
import com.rndchina.mygank.image.ImageManager;
import com.rndchina.mygank.music.model.VideoData;

import java.util.List;

/**
 * Created by PC on 2018/3/9.
 */
public class VideoAdapter extends BaseQuickAdapter<VideoData,BaseViewHolder>{
    public VideoAdapter(@LayoutRes int layoutResId, @Nullable List<VideoData> data) {
        super(R.layout.item_videopage, data);
    }

    public VideoAdapter(@Nullable List<VideoData> data) {
        super(data);
    }

    public VideoAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoData item) {

        helper.setText(R.id.author_tv,item.getAuthor());
        helper.setText(R.id.title_tv,item.getTitle());
        ImageManager.getInstance()
                .loadImage(mContext,
                        item.getThumbnail(),
                        (ImageView) helper.getView(R.id.image_iv));

    }
}
