package com.rndchina.mygank.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rndchina.mygank.R;
import com.rndchina.mygank.main.model.DrawerModel;

import java.util.List;

/**
 * Created by PC on 2018/1/12.
 */
//public class DrawerListAdapter extends BaseQuickAdapter<DrawerModel,BaseViewHolder>{
//    public DrawerListAdapter(@LayoutRes int layoutResId, @Nullable List<DrawerModel> data) {
//        super(layoutResId, data);
//    }
//
//
//
//    @Override
//    protected void convert(BaseViewHolder helper, DrawerModel item) {
//        helper.setBackgroundRes(R.id.iv_icon,item.getIconID());
//        helper.setText(R.id.tv_info,item.getTitle());
//
//    }
//}

public class DrawerListAdapter extends RecyclerView.Adapter<DrawerListAdapter.MyViewHolder> {
    private Context mContext;
    private List<DrawerModel> mList;
    private OnDrawerClickListener mOnDrawerClickListener;

    interface OnDrawerClickListener {
        void onClick(int position);
    }

    public DrawerListAdapter(Context mContext, List<DrawerModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(View.inflate(mContext, R.layout.item_main_drawerlist, null));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.imageView.setBackgroundResource(mList.get(position).getIconID());
        holder.textView.setText(mList.get(position).getTitle());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnDrawerClickListener) {
                    mOnDrawerClickListener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setmOnDrawerClickListener(OnDrawerClickListener mOnDrawerClickListener) {
        this.mOnDrawerClickListener = mOnDrawerClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        ImageView imageView;
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.layout_main);
            imageView = (ImageView) itemView.findViewById(R.id.iv_icon);
            textView = (TextView) itemView.findViewById(R.id.tv_info);
        }
    }
}
