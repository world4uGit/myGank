package com.rndchina.mygank.music;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rndchina.mygank.R;
import com.rndchina.mygank.music.model.ArtistInfo;
import com.rndchina.mygank.music.service.MusicPlayer;

import java.util.List;

/**
 * Created by PC on 2018/3/16.
 */
public class ArtistListAdapter extends BaseQuickAdapter<ArtistInfo,BaseViewHolder> {
    public ArtistListAdapter(@LayoutRes int layoutResId, @Nullable List<ArtistInfo> data) {
        super(R.layout.music_common_item, data);
    }

    public ArtistListAdapter(@Nullable List<ArtistInfo> data) {
        super(data);
    }

    public ArtistListAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArtistInfo item) {

        helper.setText(R.id.viewpager_list_toptext,item.artist_name);
        helper.setText(R.id.viewpager_list_bottom_text,item.number_of_tracks + "首");
        //根据播放中歌曲的歌手名判断当前歌手专辑条目是否有播放的歌曲
        if (MusicPlayer.getCurrentArtistId() == (item.artist_id)) {
            helper.setImageResource(R.id.viewpager_list_button,R.drawable.song_play_icon);
        } else {
            helper.setImageResource(R.id.viewpager_list_button,R.drawable.list_icn_more);

        }

    }
}
