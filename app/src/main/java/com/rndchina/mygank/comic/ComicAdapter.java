package com.rndchina.mygank.comic;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rndchina.mygank.R;
import com.rndchina.mygank.comic.model.ComicListInfo;
import com.rndchina.mygank.image.ImageManager;

import java.util.List;

/**
 * Created by PC on 2018/3/18.
 */
public class ComicAdapter extends BaseQuickAdapter<ComicListInfo.EntriesBean,BaseViewHolder>{
    public ComicAdapter(@LayoutRes int layoutResId, @Nullable List<ComicListInfo.EntriesBean> data) {
        super(layoutResId, data);
    }

    public ComicAdapter(@Nullable List<ComicListInfo.EntriesBean> data) {
        super(data);
    }

    public ComicAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComicListInfo.EntriesBean item) {
        String tag = "";
        for (String s : item.getTag_list()) {
            tag += TextUtils.isEmpty(tag) ? s : " ".concat(s);
        }
        helper.setText(R.id.tv_title, item.getName());
        helper.setText(R.id.tv_tag, item.getStatus());
        ImageManager.getInstance().loadImage(mContext,item.getCover(),(ImageView) helper.getView(R.id.book_layout));

    }
}
