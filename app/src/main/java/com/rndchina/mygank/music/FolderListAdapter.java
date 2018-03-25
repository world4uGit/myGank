package com.rndchina.mygank.music;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rndchina.mygank.R;
import com.rndchina.mygank.music.model.FolderInfo;
import com.rndchina.mygank.music.service.MusicPlayer;

import java.io.File;
import java.util.List;

/**
 * Created by PC on 2018/3/16.
 */
public class FolderListAdapter extends BaseQuickAdapter<FolderInfo,BaseViewHolder> {
    public FolderListAdapter(@LayoutRes int layoutResId, @Nullable List<FolderInfo> data) {
        super(layoutResId, data);
    }

    public FolderListAdapter(@Nullable List<FolderInfo> data) {
        super(data);
    }

    public FolderListAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FolderInfo item) {

        helper.setText(R.id.viewpager_list_toptext,item.folder_name);
        helper.setText(R.id.viewpager_list_bottom_text,item.folder_count + "首 " + item.folder_path);

        helper.setImageResource(R.id.viewpager_list_img,R.drawable.list_icn_folder);
        //根据播放中歌曲的专辑名判断当前专辑条目是否有播放的歌曲
        String folder_path = null;
        if (MusicPlayer.getPath() != null && MusicPlayer.getTrackName() != null) {
            folder_path = MusicPlayer.getPath().substring(0, MusicPlayer.getPath().lastIndexOf(File.separator));
        }
        if (folder_path != null && folder_path.equals(item.folder_path)) {
            helper.setImageResource(R.id.viewpager_list_button,R.drawable.song_play_icon);
        } else {
            helper.setImageResource(R.id.viewpager_list_button,R.drawable.list_icn_more);

        }

    }
}
