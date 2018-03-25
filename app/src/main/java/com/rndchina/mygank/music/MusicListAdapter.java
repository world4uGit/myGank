package com.rndchina.mygank.music;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rndchina.mygank.R;
import com.rndchina.mygank.music.model.MusicInfo;
import com.rndchina.mygank.music.service.MusicPlayer;

import java.util.List;

/**
 * Created by PC on 2018/3/11.
 */
public class MusicListAdapter extends BaseQuickAdapter<MusicInfo,BaseViewHolder>{
//    PlayMusic playMusic;
//    Handler handler;
//    private List<MusicInfo> mList = new ArrayList<>();


    public MusicListAdapter(@LayoutRes int layoutResId, @Nullable List<MusicInfo> data) {
        super(R.layout.item_music_list, data);
    }

    public MusicListAdapter(@Nullable List<MusicInfo> data) {
        super(data);
    }

    public MusicListAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }



    @Override
    protected void convert(final BaseViewHolder helper, MusicInfo item) {
        helper.setText(R.id.viewpager_list_toptext,item.musicName);
        helper.setText(R.id.viewpager_list_bottom_text,item.artist);
        //判断该条目音乐是否在播放
        if (MusicPlayer.getCurrentAudioId() == item.songId) {
            helper.setVisible(R.id.play_state,true);
        } else {
            helper.setGone(R.id.play_state,false);
        }
//        helper.setOnItemClickListener(R.id.rl_music, new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if(playMusic != null)
//                    handler.removeCallbacks(playMusic);
//                if(helper.getPosition() > -1){
//                    playMusic = new PlayMusic(helper.getPosition());
//                    handler.postDelayed(playMusic,70);
//                }
//            }
//        });
    }

//    class PlayMusic implements Runnable{
//        int position;
//        public PlayMusic(int position){
//            this.position = position;
//        }
//
//        @Override
//        public void run() {
//            long[] list = new long[mList.size()];
//            HashMap<Long, MusicInfo> infos = new HashMap();
//            for (int i = 0; i < mList.size(); i++) {
//                MusicInfo info = mList.get(i);
//                list[i] = info.songId;
//                info.islocal = true;
//                info.albumData = MusicUtils.getAlbumArtUri(info.albumId) + "";
//                infos.put(list[i], mList.get(i));
//            }
//            if (position > 0)
//                MusicPlayer.playAll(infos, list, position, false);
//        }
//    }
}
