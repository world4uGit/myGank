package com.rndchina.mygank.comic;

import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.widget.ImageView;

import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rndchina.mygank.R;
import com.rndchina.mygank.comic.model.ComicPreView;
import com.rndchina.mygank.image.ImageManager;

import java.util.List;

/**
 * Created by PC on 2018/3/20.
 */
public class PreviewAdapter extends BaseQuickAdapter<ComicPreView.PagesBean,BaseViewHolder>{

    private ComicPreView mComicPreView;
    //记录上一次的位置。防止再次计算
    private int lastPosition;
    //预览模式
    private int module;

    private SparseArray<ComicPreView> mComicPreViewSparseArray = new SparseArray<>();


    public PreviewAdapter(int layoutResId, @Nullable List<ComicPreView.PagesBean> data, ComicPreView currComicPreView) {
        super(layoutResId, data);
        setComicPreView(currComicPreView);
        module = SPUtils.getInstance().getInt("module", 0);

    }

    public void setComicPreView(ComicPreView comicPreView) {
        mComicPreView = comicPreView;
        if (comicPreView != null) {
            mComicPreView.setPagerSize(comicPreView.getPages().size());
            mComicPreViewSparseArray.put(comicPreView.getIndex(), comicPreView);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, ComicPreView.PagesBean item) {

        ImageView imageView = helper.getView(R.id.iv_cover);
        if (item.getIndex() == -1){
            item.setIndex(mComicPreView.getIndex());
        }

        ImageManager.getInstance().loadImage(mContext,item.getTrack_url(),imageView);




    }
}
