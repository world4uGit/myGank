package com.rndchina.mygank.friendcircle;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.rndchina.mygank.R;
import com.rndchina.mygank.common.Constant;
import com.rndchina.mygank.friendcircle.model.FrinendCircleModel;
import com.rndchina.mygank.friendcircle.model.FrinendCircleModel.DataModel.DynaListModel;
import com.rndchina.mygank.friendcircle.model.FrinendCircleModel.DataModel.DynaListModel.CommentListEntity;
import com.rndchina.mygank.image.ImageManager;
import com.rndchina.mygank.view.NoScrollListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by PC on 2018/2/7.
 */

public class FriendCircleAdapter extends BaseMultiItemQuickAdapter<DynaListModel,BaseViewHolder>{

    private static int CONSTANT = 1;
    private RelativeLayout re_edittext;
    private EditText et_comment;
    private Button btn_send;
    private CommentAdapter commentAdapter;
    private ReplyAdapter mReplyAdapter;
    private Context mContext;


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public FriendCircleAdapter(Context context,List<DynaListModel> data) {
        super(data);
        mContext = context;
        re_edittext = (RelativeLayout) ((Activity)context).findViewById(R.id.re_edittext);
        et_comment = (EditText) re_edittext.findViewById(R.id.et_comment);
        btn_send = (Button) re_edittext.findViewById(R.id.btn_send);
        addItemType(DynaListModel.IMG_TEXT, R.layout.item_img_text_view);
        addItemType(DynaListModel.TEXT, R.layout.item_img_text_view);
        addItemType(DynaListModel.ROUTE_IMG, R.layout.item_img_text_view);
        addItemType(DynaListModel.ROUTE_DATA, R.layout.route_img_text_view);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final FrinendCircleModel.DataModel.DynaListModel item) {
        switch (helper.getItemViewType()) {
            case DynaListModel.ROUTE_IMG :
            case DynaListModel.TEXT :
            case DynaListModel.IMG_TEXT :
                //设置姓名、性别、评论点赞等共同项
                setCommon(helper,item);

                //设置九宫格图片
                ArrayList<ImageInfo> imageInfo = new ArrayList<>();
                List<DynaListModel.PicsListEntity> imageDetails = item.getPicsList();
                if (imageDetails != null&& imageDetails.size()!=0) {
                    helper.setVisible(R.id.nineGrid,true);
                    for (DynaListModel.PicsListEntity imageDetail : imageDetails) {
                        ImageInfo info = new ImageInfo();
                        info.setThumbnailUrl(Constant.HOST+imageDetail.getPic_small());
                        info.setBigImageUrl(Constant.HOST+imageDetail.getPic_big());
                        imageInfo.add(info);
                    }
                }
                ((NineGridView)helper.getView(R.id.nineGrid)).setAdapter(new NineGridViewClickAdapter(mContext,imageInfo));



                break;
            case DynaListModel.ROUTE_DATA :
                setCommon(helper,item);


                break;
        }

    }

    private void setCommon(final BaseViewHolder helper, final DynaListModel item) {
        helper.setText(R.id.tv_nick,item.getUser_nickname());
        ImageManager.getInstance()
                .loadImage(mContext,
                        item.getUser_pics(),
                        (ImageView) helper.getView(R.id.sdv_image));
//                helper.setText(R.id.tv_cartitle,item.getCar_title());
        helper.setText(R.id.tv_content,item.getDyna_content());
        helper.setText(R.id.tv_time,item.getTime_trans());
        if(item.getUser_sex().equals("女")) {
            helper.setImageResource(R.id.iv_sex,R.drawable.sex_nv);
        }else {
            helper.setImageResource(R.id.iv_sex,R.drawable.sex_nan);
        }

        //设置点赞、评论状态
        setStatus(helper, item);


        //设置点赞人列表
        //List<DynamicItemBean.PraiseList> praiseList = item.getPraiseList();
        setPraieseList(helper, item);

        //设置评论
        setComment(helper, item.getCommentList());

        //收藏点击事件
        helper.setOnClickListener(R.id.iv_collect, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCollect(item);

            }
        });

        //点赞
        helper.setOnClickListener(R.id.iv_zan, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGood(helper,item);

            }
        });

        //评论
        helper.setOnClickListener(R.id.iv_comment, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment(item);
            }


        });

        //回复评论
//        commentAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                if(re_edittext.getVisibility()!=View.VISIBLE) {
//                    replyComment(position,item);
//                }
//            }
//        });
        //回复评论
        ((NoScrollListView)helper.getView(R.id.replyList)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(re_edittext == null
                        || re_edittext.getVisibility() != View.VISIBLE) {
                    replyComment(position,item);
                }
            }


        });


    }

    private void replyComment(final int position, final DynaListModel item) {
        re_edittext.setVisibility(View.VISIBLE);
        et_comment.requestFocus();
        InputMethodManager manager = (InputMethodManager) et_comment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(0,InputMethodManager.SHOW_FORCED);
        btn_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String comment = et_comment.getText().toString().trim();
                if (TextUtils.isEmpty(comment)) {
                    ToastUtils.showShort("请输入评论");
                    return;
                }
                //即时改变当前ui
                CommentListEntity newComment = new CommentListEntity();
                newComment.setUser_nickname("world4U");
                newComment.setCom_level("1");
                newComment.setCommentid(item.getCommentList().get(position).getCommentid());
                newComment.setCom_content(comment);
                newComment.setUserto_id(item.getCommentList().get(position).getUserid());
                newComment.setUserto_nickname(item.getCommentList().get(position).getUser_nickname());

                item.getCommentList().add(newComment);
                notifyDataSetChanged();


                et_comment.setText("");
                hideCommentEditText();
            }

        });

    }

    private void Comment(final DynaListModel item) {
        if(re_edittext.getVisibility()!=View.VISIBLE) {
            re_edittext.setVisibility(View.VISIBLE);
            et_comment.requestFocus();
            InputMethodManager manager = (InputMethodManager) et_comment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.toggleSoftInput(0,InputMethodManager.SHOW_FORCED);
            Button btn_send = (Button) re_edittext.findViewById(R.id.btn_send);
            btn_send.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String comment = et_comment.getText().toString().trim();
                    if (TextUtils.isEmpty(comment)) {
                        Toast.makeText(mContext, "请输入评论", Toast.LENGTH_SHORT)
                                .show();
                        return;
                    }
                    //即时改变当前ui
                    CommentListEntity newComment = new CommentListEntity();
                    newComment.setUser_nickname("world4U");
                    newComment.setUserto_id("");
                    newComment.setCom_level("0");
                    newComment.setCommentid("");
                    newComment.setCom_content(comment);
                    newComment.setUserto_id("");
                    newComment.setUserto_nickname("");
                    newComment.setUserid("0");

                    item.getCommentList().add(newComment);
                    FriendCircleAdapter.this.notifyDataSetChanged();





                    et_comment.setText("");
                    hideCommentEditText();
                }

            });

        }

    }

    private void hideCommentEditText() {
        if(re_edittext.getVisibility() == View.VISIBLE){
            re_edittext.setVisibility(View.GONE);
            et_comment.clearFocus();
            InputMethodManager manager = (InputMethodManager) et_comment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(et_comment.getWindowToken(),0);
        }




    }

    private void setComment(BaseViewHolder helper, List<DynaListModel.CommentListEntity> commentList) {
        if(commentList==null||commentList.size()==0) {
            helper.setGone(R.id.replyList,false);
            helper.setGone(R.id.view_pop,false);

        }else {

            commentAdapter = new CommentAdapter(R.layout.reply_item,commentList);
//            RecyclerView mRecyclerView = (RecyclerView)helper.getView(R.id.replyList);
//            CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(mContext);
//            linearLayoutManager.setScrollEnabled(false);
//            mRecyclerView.setLayoutManager(linearLayoutManager);
//            mRecyclerView.setAdapter(commentAdapter);
            mReplyAdapter = new ReplyAdapter(mContext,commentList);
            ((NoScrollListView)helper.getView(R.id.replyList)).setAdapter(mReplyAdapter);
            helper.setVisible(R.id.replyList,true);
            helper.setVisible(R.id.view_pop,true);


        }
    }

    /*
    点赞
     */
    private void setGood(BaseViewHolder helper, DynaListModel item) {
        List<DynaListModel.PraiseListEntity> praiseList = item.getPraiseList();
        DynaListModel.PraiseListEntity mPraise = new DynaListModel.PraiseListEntity();
        mPraise.setUser_nickname("world4U");

        if(item.getIs_praise()==0) {
            item.setIs_praise(1);
            praiseList.add(mPraise);

        }else {
            item.setIs_praise(0);
            for(int i = 0; i < praiseList.size(); i++) {
                DynaListModel.PraiseListEntity praiseItem = praiseList.get(i);
                if(praiseItem.getUser_nickname().equals("world4U")) {
                    praiseList.remove(i);
                }
            }
        }
        notifyDataSetChanged();
    }

    /*
    收藏
     */
    private void setCollect(DynaListModel item) {
        if(item.getIs_follow()==1) {
            item.setIs_follow(0);
        }else {
            item.setIs_follow(1);
        }
        notifyDataSetChanged();

    }

    /*
    设置点赞列表
     */
    private void setPraieseList(BaseViewHolder helper, DynaListModel item) {
        StringBuilder sb = new StringBuilder();
        StringBuilder id = new StringBuilder();

        if(item.getPraiseList()!=null&&item.getPraiseList().size()!=0) {
            helper.setVisible(R.id.view_pop,true);
            helper.setVisible(R.id.ll_goodmembers,true);
            helper.setVisible(R.id.iv_mark,true);

            List<DynaListModel.PraiseListEntity> praiseLists = item.getPraiseList();
            for(int i = 0; i < praiseLists.size(); i++) {
                sb.append(praiseLists.get(i).getUser_nickname()+"、");
                id.append(praiseLists.get(i).getUserid()+"、");
            }

            if(praiseLists.size()>0) {
                String users=sb.substring(0,sb.lastIndexOf("、"));
                String userids=id.substring(0,id.lastIndexOf("、"));
                TextView tv = helper.getView(R.id.tv_goodmembers);
                tv.setText(addClickPart(users,userids));
                tv.setMovementMethod(LinkMovementMethod.getInstance());
            }




        }else {
            helper.setGone(R.id.view_pop,false);
            helper.setGone(R.id.ll_goodmembers,false);
            helper.setGone(R.id.iv_mark,false);




        }


    }

    //定义点击每个部分文字的处理方法
    private SpannableStringBuilder addClickPart(String users, String usersids) {
        SpannableString spanStr=new SpannableString("");//任意文字 主要是实现效果
        //创建一个ssb 存储总的用户
        SpannableStringBuilder ssb=new SpannableStringBuilder(spanStr);
        ssb.append(users);

        String[] users_array=users.split("、");
        final String[] ids_array=usersids.split("、");

        if(users_array.length>0){
            for (int i = 0; i < users_array.length; i++) {
                final String user_name = users_array[i];//好友0
                final String user_id = ids_array[i];//好友0
                int start=users.indexOf(user_name)+spanStr.length();

                //为每段数据增加点击事件
                final int finalI = i;
                ssb.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
//                        context.startActivity(new Intent(context, UserDetailsActivity.class).putExtra(Constant.JSON_KEY_USERID,user_id));
                        ToastUtils.showShort(user_name);

                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(Color.parseColor("#1294ea"));
                        ds.setUnderlineText(false);
                    }
                },start,start+user_name.length(),0);
            }
        }

        return ssb.append("等人觉得很赞");
    }


    private void setStatus(BaseViewHolder helper, DynaListModel item) {
        //设置收藏
        if(item.getIs_follow()==CONSTANT) {
            helper.setImageResource(R.id.iv_collect,R.drawable.collect3x_select);


            //iscollect = true;

        }else {
            helper.setImageResource(R.id.iv_collect,R.drawable.collect3x_nomal);

            //iscollect = false;


        }

        //设置点赞
        if(item.getIs_praise()==CONSTANT) {
            helper.setImageResource(R.id.iv_zan,R.drawable.zan_selected);

        }else {
            helper.setImageResource(R.id.iv_zan,R.drawable.zan3x);


        }
    }
}
