package com.rndchina.mygank.comic;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rndchina.mygank.comic.model.ComicListDetail;

import java.util.List;

/**
 * Created by PC on 2018/3/20.
 */
public class ChapterAdapter extends BaseQuickAdapter<ComicListDetail.ChaptersBean,BaseViewHolder>{

    public ChapterAdapter(@LayoutRes int layoutResId, @Nullable List<ComicListDetail.ChaptersBean> data) {
        super(layoutResId, data);
    }

    public ChapterAdapter(@Nullable List<ComicListDetail.ChaptersBean> data) {
        super(data);
    }

    public ChapterAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    public int getLastPosition() {
        return 0;
    }

    @Override
    protected void convert(BaseViewHolder helper, ComicListDetail.ChaptersBean item) {

    }

    public void updatePosition(int nextPosition, int index) {

    }
}
