package com.rndchina.mygank.music;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rndchina.mygank.R;
import com.rndchina.mygank.base.App;
import com.rndchina.mygank.music.service.MusicPlayer;
import com.rndchina.mygank.music.util.HandlerUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PC on 2018/3/12.
 */
public class QuickControlsFragment extends BaseMucicFragment {
    @BindView(R.id.playbar_img)
    ImageView mPlaybarImg;
    @BindView(R.id.playbar_info)
    TextView mPlaybarInfo;
    @BindView(R.id.playbar_singer)
    TextView mPlaybarSinger;
    @BindView(R.id.play_list)
    ImageView mPlayList;
    @BindView(R.id.control)
    ImageView mControl;
    @BindView(R.id.play_next)
    ImageView mPlayNext;
    @BindView(R.id.song_progress_normal)
    ProgressBar mProgress;

    public Runnable mUpdateProgress = new Runnable() {

        @Override
        public void run() {

            long position = MusicPlayer.position();
            long duration = MusicPlayer.duration();
            if (duration > 0 && duration < 627080716){
                mProgress.setProgress((int) (1000 * position / duration));
            }

            if (MusicPlayer.isPlaying()) {
                mProgress.postDelayed(mUpdateProgress, 50);
            }else {
                mProgress.removeCallbacks(mUpdateProgress);
            }

        }
    };

    public static QuickControlsFragment newInstance() {
        return new QuickControlsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_nav, container, false);
        ButterKnife.bind(this, view);
        mProgress.postDelayed(mUpdateProgress,0);

        mControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mControl.setImageResource(MusicPlayer.isPlaying() ? R.drawable.playbar_btn_pause
                        : R.drawable.playbar_btn_play);
//                mControl.setImageTintList(Color.par("#3fc2e3"));

                if (MusicPlayer.getQueueSize() == 0) {
                    Toast.makeText(App.context, getResources().getString(R.string.queue_is_empty),
                            Toast.LENGTH_SHORT).show();
                } else {
                    HandlerUtil.getInstance(App.context).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MusicPlayer.playOrPause();
                        }
                    }, 60);
                }

            }
        });

        mPlayNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MusicPlayer.next();
                    }
                }, 60);

            }
        });

        mPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        PlayQueueFragment playQueueFragment = new PlayQueueFragment();
//                        playQueueFragment.show(getFragmentManager(), "playqueueframent");
                    }
                }, 60);

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(App.context, PlayingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                App.context.startActivity(intent);
            }
        });
        return view;
    }

    public void updateNowplayingCard() {
        mPlaybarInfo.setText(MusicPlayer.getTrackName());
        mPlaybarSinger.setText(MusicPlayer.getArtistName());

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        mProgress.removeCallbacks(mUpdateProgress);
    }

    @Override
    public void onResume() {
        super.onResume();
        mProgress.setMax(1000);
        mProgress.removeCallbacks(mUpdateProgress);
        mProgress.postDelayed(mUpdateProgress,0);
        updateNowplayingCard();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void updateState() {
        if (MusicPlayer.isPlaying()) {
            mControl.setImageResource(R.drawable.playbar_btn_pause);
//            mControl.setImageTintList(R.color.theme_color_primary);
            mProgress.removeCallbacks(mUpdateProgress);
            mProgress.postDelayed(mUpdateProgress,50);
        } else {
            mControl.setImageResource(R.drawable.playbar_btn_play);
//            mControl.setImageTintList(R.color.theme_color_primary);
            mProgress.removeCallbacks(mUpdateProgress);
        }
    }


    public void updateTrackInfo() {
        updateNowplayingCard();
        updateState();
    }


    @Override
    public void changeTheme() {
        super.changeTheme();
    }


}
