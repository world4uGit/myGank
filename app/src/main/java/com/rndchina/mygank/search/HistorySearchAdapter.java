package com.rndchina.mygank.search;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rndchina.mygank.R;

import java.util.List;

/**
 * Created by PC on 2018/2/4.
 */
public class HistorySearchAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
    public HistorySearchAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(R.layout.item_history_search, data);
    }

    public HistorySearchAdapter(@Nullable List<String> data) {
        super(data);
    }

    public HistorySearchAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_info,item);
        helper.addOnClickListener(R.id.iv_clear);
    }
}
