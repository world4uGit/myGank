package com.rndchina.mygank.friendcircle;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rndchina.mygank.R;
import com.rndchina.mygank.friendcircle.model.FrinendCircleModel;

import java.util.List;

/**
 * Created by PC on 2018/3/1.
 */
public class ReplyAdapter extends BaseAdapter {

    private Context mContext;
    private List<FrinendCircleModel.DataModel.DynaListModel.CommentListEntity> list_result;
    private SpannableString ss;

    public ReplyAdapter(Context mContext, List<FrinendCircleModel.DataModel.DynaListModel.CommentListEntity> list_result) {
        super();
        this.mContext = mContext;
        this.list_result = list_result;
    }

    @Override
    public int getCount() {
        return list_result.size();
    }

    @Override
    public Object getItem(int position) {
        return list_result.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        String proName = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.reply_item, null);
            vh = new ViewHolder();
            vh.replyContent = (TextView) convertView.findViewById(R.id.replyContent);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        // ImageShow result = list_result.get(position);
        FrinendCircleModel.DataModel.DynaListModel.CommentListEntity commentItem = list_result.get(position);

        final String replyNickName = commentItem.getUser_nickname();
        final String commentNickName = commentItem.getUserto_nickname();
        String replyContentStr = commentItem.getCom_content();

        if(commentNickName!=null&&!TextUtils.isEmpty(commentNickName)){
            ss = new SpannableString(replyNickName + " 回复: " + commentNickName + " " + replyContentStr);
            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#1294ea")), 0, replyNickName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#1294ea")), replyNickName.length() + 5,replyNickName.length() + commentNickName.length() + 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);




        }else{
            //ss = new SpannableString(replyNickName + "评论" + commentNickName + ":" + replyContentStr);
            ss = new SpannableString(replyNickName +  ":  " + replyContentStr);
            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#1294ea")), 0, replyNickName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);



        }





//		ss = new SpannableString(replyNickName + "回复" + commentNickName + ":" + replyContentStr);

        vh.replyContent.setText(ss);
        return convertView;
    }

    class ViewHolder {
        TextView replyContent;
    }
}
