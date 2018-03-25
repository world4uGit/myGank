package com.rndchina.mygank.music.util;

import com.rndchina.mygank.music.model.MusicInfo;
import com.rndchina.mygank.music.service.MusicPlayer;
import com.rndchina.mygank.utils.MusicUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by PC on 2018/3/16.
 */

public class PlayMusic implements Runnable{
    int position;
    List<MusicInfo> mList = new ArrayList<>();
    public PlayMusic(int position,List<MusicInfo> mList){
        this.position = position;
        this.mList = mList;
    }

    @Override
    public void run() {
        long[] list = new long[mList.size()];
        HashMap<Long, MusicInfo> infos = new HashMap();
        for (int i = 0; i < mList.size(); i++) {
            MusicInfo info = mList.get(i);
            list[i] = info.songId;
            info.islocal = true;
            info.albumData = MusicUtils.getAlbumArtUri(info.albumId) + "";
            infos.put(list[i], mList.get(i));
        }
        if (position > -1)
            MusicPlayer.playAll(infos, list, position, false);
    }
}
