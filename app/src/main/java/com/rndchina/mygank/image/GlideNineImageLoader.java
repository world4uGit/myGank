package com.rndchina.mygank.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.lzy.ninegrid.NineGridView;

/**
 * Created by PC on 2017/5/8.
 */
public class GlideNineImageLoader implements NineGridView.ImageLoader {
    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
//        Glide.with(context)                             //配置上下文
//                .load(url)     //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
//                .error(R.mipmap.default_image)           //设置错误图片
//                .placeholder(R.mipmap.default_image)     //设置占位图片
//                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
//                .into(imageView);
        ImageManager.getInstance()
                .loadImage(context, url, imageView);
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }

}
