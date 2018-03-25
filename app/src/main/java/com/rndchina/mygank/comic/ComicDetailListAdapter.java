package com.rndchina.mygank.comic;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.came.viewbguilib.ButtonBgUi;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rndchina.mygank.R;
import com.rndchina.mygank.comic.model.ComicListDetail;
import com.rndchina.mygank.db.ComicRecord;

import java.util.List;

/**
 * Created by PC on 2018/3/19.
 */
public class ComicDetailListAdapter extends BaseQuickAdapter<ComicListDetail.ChaptersBean,BaseViewHolder>{


    private int lastPosition;
    private int index;
    private ComicRecord mComicRecord;

    public ComicDetailListAdapter(@Nullable List<ComicListDetail.ChaptersBean> data, ComicRecord comicRecord) {
        super(R.layout.comic_item_detail_list, data);
        init();
        mComicRecord = comicRecord;
    }

    public ComicDetailListAdapter(@LayoutRes int layoutResId, @Nullable List<ComicListDetail.ChaptersBean> data) {
        super(layoutResId, data);
    }

    public ComicDetailListAdapter(@Nullable List<ComicListDetail.ChaptersBean> data) {
        super(data);
    }

    public ComicDetailListAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComicListDetail.ChaptersBean item) {
        ((ButtonBgUi)helper.getView(R.id.list_item)).setText(item.getName());
//        helper.setText(R.id.list_item,item.getName());

    }

    public void init() {
        lastPosition = -1;
        index = -1;
    }

    public void updatePosition(int position, int index) {

    }
}
