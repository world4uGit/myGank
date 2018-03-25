package com.rndchina.mygank.music;

/**
 * Created by PC on 2018/3/12.
 */
public interface MusicStateListener {
    /**
     * 更新歌曲状态信息
     */
    void updateTrackInfo();

    void updateTime();

    void changeTheme();

    void reloadAdapter();
}
