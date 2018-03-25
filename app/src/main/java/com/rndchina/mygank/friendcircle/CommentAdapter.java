package com.rndchina.mygank.friendcircle;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rndchina.mygank.R;
import com.rndchina.mygank.friendcircle.model.FrinendCircleModel.DataModel.DynaListModel.CommentListEntity;

import java.util.List;

/**
 * Created by PC on 2018/2/9.
 */

public class CommentAdapter extends BaseQuickAdapter<CommentListEntity,BaseViewHolder>{
    private SpannableString ss;

    public CommentAdapter(@LayoutRes int layoutResId, @Nullable List<CommentListEntity> data) {
        super(layoutResId, data);
    }

    public CommentAdapter(@Nullable List<CommentListEntity> data) {
        super(data);
    }

    public CommentAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentListEntity item) {
        String replyNickName = item.getUser_nickname();
        String commentNickName = item.getUserto_nickname();
        String replyContentStr = item.getCom_content();

        if(commentNickName!=null&&!TextUtils.isEmpty(commentNickName)){
            ss = new SpannableString(replyNickName + " 回复: " + commentNickName + " " + replyContentStr);
            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#3fc2e3")), 0, replyNickName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#3fc2e3")), replyNickName.length() + 5,replyNickName.length() + commentNickName.length() + 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);




        }else{
            //ss = new SpannableString(replyNickName + "评论" + commentNickName + ":" + replyContentStr);
            ss = new SpannableString(replyNickName +  ":  " + replyContentStr);
            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#1294ea")), 0, replyNickName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);



        }

        helper.setText(R.id.replyContent,ss);

    }
}
