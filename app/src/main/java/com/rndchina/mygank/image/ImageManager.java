package com.rndchina.mygank.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.target.Target;
import com.rndchina.mygank.R;

/**
 * Created by PC on 2018/1/15.
 */

public class ImageManager {
    private static ImageManager mImageManager;

    private ImageManager() {
    }

    public static ImageManager getInstance(){
        if(mImageManager==null) {
            mImageManager = new ImageManager();
        }

        return mImageManager;
    }

    /**
     * 加载图片 显示默认的加载占位图和错误图
     *
     * @param context
     * @param path
     * @param targetImageView
     */
    public void loadImage(Context context, Object path, ImageView targetImageView) {
        Glide.with(context)
                .load(path)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.notfound)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(targetImageView);
    }
    /**
     * 加载图片 显示默认的加载占位图和错误图,设置转换器
     *
     * @param context
     * @param path
     * @param targetImageView
     * @param bitmapTransformation
     */
    public void loadImage(Context context, Object path, ImageView targetImageView,BitmapTransformation bitmapTransformation) {
        Glide.with(context)
                .load(path)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.notfound)
                .bitmapTransform(bitmapTransformation)
                .into(targetImageView);
    }

    /**
     * 加载图片
     *
     * @param context
     * @param path
     * @param placeholderResourceId
     * @param errorResourceId
     * @param targetImageView
     */
    public void loadImage(Context context, Object path, int placeholderResourceId, int errorResourceId, ImageView targetImageView) {
        Glide.with(context)
                .load(path)
                .placeholder(placeholderResourceId)
                .error(errorResourceId)
                .into(targetImageView);
    }

    /**
     * 加载图片，获取bitmap数据
     * @param context
     * @param path
     * @return
     */
    public Bitmap loadImage(Context context, Object path) {
        Bitmap bitmap;
        try {
            bitmap = Glide.with(context)
                    .load(path)
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
        return bitmap;
    }
}
