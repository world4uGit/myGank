package com.rndchina.mygank.base;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.lzy.ninegrid.NineGridView;
import com.rndchina.mygank.image.GlideNineImageLoader;

import io.realm.Realm;

/**
 * Created by PC on 2018/1/9.
 */

public class App extends Application{

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Utils.init(this);
        Realm.init(this);
        NineGridView.setImageLoader(new GlideNineImageLoader());
    }
}
