package com.rndchina.mygank.music;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rndchina.mygank.R;
import com.rndchina.mygank.image.ImageManager;
import com.rndchina.mygank.music.model.AlbumInfo;
import com.rndchina.mygank.music.service.MusicPlayer;

import java.util.List;

/**
 * Created by PC on 2018/3/16.
 */
public class AlbumListAdapter extends BaseQuickAdapter<AlbumInfo,BaseViewHolder>{
    public AlbumListAdapter(@LayoutRes int layoutResId, @Nullable List<AlbumInfo> data) {
        super(R.layout.music_common_item, data);
    }

    public AlbumListAdapter(@Nullable List<AlbumInfo> data) {
        super(data);
    }

    public AlbumListAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AlbumInfo item) {

        helper.setText(R.id.viewpager_list_toptext,item.album_name);
        helper.setText(R.id.viewpager_list_bottom_text,item.number_of_songs + "首"+ item.album_artist);
        ImageManager.getInstance()
                .loadImage(mContext,
                        item.album_art,
                        (ImageView) helper.getView(R.id.viewpager_list_img));
        //根据播放中歌曲的歌手名判断当前歌手专辑条目是否有播放的歌曲
        if (MusicPlayer.getArtistName() != null && MusicPlayer.getAlbumName().equals(item.album_name)) {
            helper.setImageResource(R.id.viewpager_list_button,R.drawable.song_play_icon);
        } else {
            helper.setImageResource(R.id.viewpager_list_button,R.drawable.list_icn_more);

        }

    }
}
