package com.rndchina.mygank.music;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rndchina.mygank.R;
import com.rndchina.mygank.base.BaseMusicActivity;
import com.rndchina.mygank.music.db.PlaylistsManager;
import com.rndchina.mygank.music.model.MusicInfo;
import com.rndchina.mygank.music.service.MediaService;
import com.rndchina.mygank.music.service.MusicPlayer;
import com.rndchina.mygank.music.util.HandlerUtil;
import com.rndchina.mygank.utils.IConstants;
import com.rndchina.mygank.utils.MusicUtils;
import com.rndchina.mygank.view.PlayerSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rndchina.mygank.music.service.MusicPlayer.getAlbumPath;

public class PlayingActivity extends BaseMusicActivity implements IConstants{

    @BindView(R.id.albumArt)
    ImageView mAlbumArt;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.view_line)
    View mViewLine;
    @BindView(R.id.needle)
    ImageView mNeedle;
    @BindView(R.id.headerView)
    FrameLayout mHeaderView;
    @BindView(R.id.volume_seek)
    SeekBar mVolumeSeek;
    @BindView(R.id.volume_layout)
    LinearLayout mVolumeLayout;
    @BindView(R.id.tragetlrc)
    TextView mTragetlrc;
    @BindView(R.id.lrcview)
    TextView mLrcview;
    @BindView(R.id.lrcviewContainer)
    RelativeLayout mLrcviewContainer;
    @BindView(R.id.playing_fav)
    ImageView mPlayingFav;
    @BindView(R.id.playing_down)
    ImageView mPlayingDown;
    @BindView(R.id.playing_cmt)
    ImageView mPlayingCmt;
    @BindView(R.id.playing_more)
    ImageView mPlayingMore;
    @BindView(R.id.music_tool)
    LinearLayout mMusicTool;
    @BindView(R.id.music_duration_played)
    TextView mMusicDurationPlayed;
    @BindView(R.id.play_seek)
    PlayerSeekBar mPlaySeek;
    @BindView(R.id.music_duration)
    TextView mMusicDuration;
    @BindView(R.id.playing_mode)
    ImageView mPlayingMode;
    @BindView(R.id.playing_pre)
    ImageView mPlayingPre;
    @BindView(R.id.playing_play)
    ImageView mPlayingPlay;
    @BindView(R.id.playing_next)
    ImageView mPlayingNext;
    @BindView(R.id.playing_playlist)
    ImageView mPlayingPlaylist;
    @BindView(R.id.bottom_control)
    LinearLayout mBottomControl;


    private ObjectAnimator mNeedleAnim, mRotateAnim;
    private AnimatorSet mAnimatorSet;
    private Handler mHandler;
    private Handler mPlayHandler;
    private static final int VIEWPAGER_SCROLL_TIME = 390;
    private static final int TIME_DELAY = 500;
    private static final int NEXT_MUSIC = 0;
    private static final int PRE_MUSIC = 1;
    private boolean isFav;
    private PlaylistsManager mPlaylistsManager;
    private PlayMusic mPlayThread;
    private long lastAlbum;
    private ActionBar ab;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        ButterKnife.bind(this);
        mPlaylistsManager = PlaylistsManager.getInstance(this);

        mHandler = HandlerUtil.getInstance(this);

//        mHandler.postDelayed(mUpAlbumRunnable, 0);
        initView();
        mPlayThread = new PlayMusic();
        mPlayThread.start();

    }

    @Override
    protected void showQuickControl(boolean show) {
        //super.showOrHideQuickControl(show);
    }

    public class PlayMusic extends Thread {
        public void run(){
            if(Looper.myLooper() == null)
                Looper.prepare();
            mPlayHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what){
                        case PRE_MUSIC:
                            MusicPlayer.previous(PlayingActivity.this,true);
                            break;
                        case NEXT_MUSIC:
                            MusicPlayer.next();
                            break;
                        case 3:
                            MusicPlayer.setQueuePosition(msg.arg1);
                            break;
                    }
                }
            };

            Looper.loop();

        }
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.actionbar_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //设置动画
        mNeedleAnim = ObjectAnimator.ofFloat(mNeedle, "rotation", -25, 0);
        mNeedleAnim.setDuration(200);
        mNeedleAnim.setRepeatMode(0);
        mNeedleAnim.setInterpolator(new LinearInterpolator());
        //设置进度
        mPlaySeek.setIndeterminate(false);
        mPlaySeek.setProgress(1);
        mPlaySeek.setMax(1000);
        //设置进度条点击事件
        setSeekBarListener();

    }

    @Override
    public void updateBuffer(int p) {
        mPlaySeek.setSecondaryProgress(p*10);
    }

    @Override
    public void onResume() {
        super.onResume();
        lastAlbum = -1;
        if(MusicPlayer.isTrackLocal())
            updateBuffer(100);
        else {
            updateBuffer(MusicPlayer.secondPosition());
        }
        mHandler.postDelayed(mUpdateProgress,0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayHandler.removeCallbacksAndMessages(null);
        mPlayHandler.getLooper().quit();
        mPlayHandler = null;

        mPlaySeek.removeCallbacks(mUpdateProgress);
//        stopAnim();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        stopAnim();
        mPlaySeek.removeCallbacks(mUpdateProgress);
    }

    private void setSeekBarListener() {
        if (mPlaySeek != null)
            mPlaySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progress = 0;

                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    i = (int) (i * MusicPlayer.duration() / 1000);
//                    mLrcView.seekTo(i, true, b);
                    if (b) {
                        MusicPlayer.seek((long)i);
                        mMusicDurationPlayed.setText(MusicUtils.makeTimeString( i ));
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
    }

    @OnClick({R.id.playing_fav, R.id.playing_down, R.id.playing_cmt, R.id.playing_more, R.id.playing_mode, R.id.playing_pre, R.id.playing_play, R.id.playing_next, R.id.playing_playlist})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playing_fav:
                if (isFav) {
                    mPlaylistsManager.removeItem(PlayingActivity.this, IConstants.FAV_PLAYLIST,
                            MusicPlayer.getCurrentAudioId());
                    mPlayingFav.setImageResource(R.drawable.play_rdi_icn_love);
                    isFav = false;
                } else {
                    try {
                        MusicInfo info = MusicPlayer.getPlayinfos().get(MusicPlayer.getCurrentAudioId());
                        mPlaylistsManager.insertMusic(PlayingActivity.this, IConstants.FAV_PLAYLIST, info);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mPlayingFav.setImageResource(R.drawable.play_icn_loved);
                    isFav = true;
                }

                Intent intent = new Intent(IConstants.PLAYLIST_COUNT_CHANGED);
                sendBroadcast(intent);
                break;
            case R.id.playing_down:
                break;
            case R.id.playing_cmt:
                break;
            case R.id.playing_more:

                break;
            case R.id.playing_mode:
                MusicPlayer.cycleRepeat();
                updatePlaymode();
                break;
            case R.id.playing_pre:
                Message msgPre = new Message();
                msgPre.what = PRE_MUSIC;
                mPlayHandler.sendMessage(msgPre);
                break;
            case R.id.playing_play:
                if (MusicPlayer.isPlaying()) {
                    mPlayingPlay.setImageResource(R.drawable.play_rdi_btn_pause);
                } else {
                    mPlayingPlay.setImageResource(R.drawable.play_rdi_btn_play);
                }
                if (MusicPlayer.getQueueSize() != 0) {
                    MusicPlayer.playOrPause();
                }
                break;
            case R.id.playing_next:
//                if (mRotateAnim != null) {
//                    mRotateAnim.end();
//                    mRotateAnim = null;
//                }
//                mHandler.removeCallbacks(mNextRunnable);
//                mHandler.postDelayed(mNextRunnable,300);
                Message msgNext = new Message();
                msgNext.what = NEXT_MUSIC;
                mPlayHandler.sendMessage(msgNext);

                break;
            case R.id.playing_playlist:
                break;
        }
    }
    @Override
    public void updateTrack() {
//        mHandler.removeCallbacks(mUpAlbumRunnable);
//        if(MusicPlayer.getCurrentAlbumId() != lastAlbum)
//            mHandler.postDelayed(mUpAlbumRunnable, 1600);


        isFav = false;
        long[] favlists = mPlaylistsManager.getPlaylistIds(IConstants.FAV_PLAYLIST);
        long currentid = MusicPlayer.getCurrentAudioId();
        for (long i : favlists) {
            if (currentid == i) {
                isFav = true;
                break;
            }
        }
//        updateFav(isFav);
//        updateLrc();


        ab.setTitle(MusicPlayer.getTrackName());
        ab.setSubtitle(MusicPlayer.getArtistName());
        mMusicDuration.setText(MusicUtils.makeShortTimeString(PlayingActivity.this.getApplication(), MusicPlayer.duration() / 1000));
    }

    private Runnable mUpAlbumRunnable = new Runnable() {
        @Override
        public void run() {
            new setBlurredAlbumArt().execute();
        }
    };
    private class setBlurredAlbumArt extends AsyncTask<Void, Void, Drawable> {

        long albumid = MusicPlayer.getCurrentAlbumId();

        @Override
        protected Drawable doInBackground(Void... loadedImage) {
            lastAlbum = albumid;
            Drawable drawable = null;
//            mBitmap = null;
//            if (mNewOpts == null) {
//                mNewOpts = new BitmapFactory.Options();
//                mNewOpts.inSampleSize = 6;
//                mNewOpts.inPreferredConfig = Bitmap.Config.RGB_565;
//            }
            if (!MusicPlayer.isTrackLocal()) {
                if (getAlbumPath() == null) {
//                    mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder_disk_210);
//                    drawable = ImageUtils.createBlurredImageFromBitmap(mBitmap, PlayingActivity.this.getApplication(), 3);
                    return drawable;
                }

            } else {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return drawable;
        }

        @Override
        protected void onPostExecute(Drawable result) {

            if (albumid != MusicPlayer.getCurrentAlbumId()) {
                this.cancel(true);
                return;
            }
//            setDrawable(result);

        }

    }


    @Override
    public void updateTrackInfo() {

        if (MusicPlayer.getQueueSize() == 0) {
            return;
        }

//        Fragment fragment = (RoundFragment) mViewPager.getAdapter().instantiateItem(mViewPager, mViewPager.getCurrentItem());
//        if (fragment != null) {
//            View v = fragment.getView();
//            if(mViewWeakReference.get() != v && v != null){
//                ((ViewGroup) v).setAnimationCacheEnabled(false);
//                if (mViewWeakReference != null)
//                    mViewWeakReference.clear();
//                mViewWeakReference = new WeakReference<View>(v);
//                mActiveView = mViewWeakReference.get();
//            }
//        }

//        if (mActiveView != null) {
//            //            animatorWeakReference = new WeakReference<>((ObjectAnimator) mActiveView.getTag(R.id.tag_animator));
//            //            mRotateAnim = animatorWeakReference.get();
//            mRotateAnim = (ObjectAnimator) mActiveView.getTag(R.id.tag_animator);
//        }

        //mProgress.setMax((int) MusicPlayer.mDuration());


//        mAnimatorSet = new AnimatorSet();
        if (MusicPlayer.isPlaying()) {
            mPlaySeek.removeCallbacks(mUpdateProgress);
            mPlaySeek.postDelayed(mUpdateProgress, 200);
            mPlayingPlay.setImageResource(R.drawable.play_rdi_btn_pause);
//            if (mAnimatorSet != null && mRotateAnim != null && !mRotateAnim.isRunning()) {
//                //修复从playactivity回到Main界面null
//                if (mNeedleAnim == null) {
//                    mNeedleAnim = ObjectAnimator.ofFloat(mNeedle, "rotation", -30, 0);
//                    mNeedleAnim.setDuration(200);
//                    mNeedleAnim.setRepeatMode(0);
//                    mNeedleAnim.setInterpolator(new LinearInterpolator());
//                }
//                mAnimatorSet.play(mNeedleAnim).before(mRotateAnim);
//                mAnimatorSet.start();
//            }

        } else {
            mPlaySeek.removeCallbacks(mUpdateProgress);
            mPlayingPlay.setImageResource(R.drawable.play_rdi_btn_play);
//            if (mNeedleAnim != null) {
//                mNeedleAnim.reverse();
//                mNeedleAnim.end();
//            }
//
//            if (mRotateAnim != null && mRotateAnim.isRunning()) {
//                mRotateAnim.cancel();
//                float valueAvatar = (float) mRotateAnim.getAnimatedValue();
//                mRotateAnim.setFloatValues(valueAvatar, 360f + valueAvatar);
//            }
        }

//        isNextOrPreSetPage = false;
//        if (MusicPlayer.getQueuePosition() + 1 != mViewPager.getCurrentItem()) {
//            mViewPager.setCurrentItem(MusicPlayer.getQueuePosition() + 1);
//            isNextOrPreSetPage = true;
//        }

    }

    private Runnable mUpdateProgress = new Runnable() {

        @Override
        public void run() {

            if (mPlaySeek != null) {
                long position = MusicPlayer.position();
                long duration = MusicPlayer.duration();
                if (duration > 0 && duration < 627080716){
                    mPlaySeek.setProgress((int) (1000 * position / duration));
                    mMusicDurationPlayed.setText(MusicUtils.makeTimeString( position ));
                }

                if (MusicPlayer.isPlaying()) {
                    mPlaySeek.postDelayed(mUpdateProgress, 200);
                }else {
                    mPlaySeek.removeCallbacks(mUpdateProgress);
                }
            }
        }
    };

    private void updatePlaymode() {
        if (MusicPlayer.getShuffleMode() == MediaService.SHUFFLE_NORMAL) {
            mPlayingMode.setImageResource(R.drawable.play_icn_shuffle);
            Toast.makeText(PlayingActivity.this.getApplication(), getResources().getString(R.string.random_play),
                    Toast.LENGTH_SHORT).show();
        } else {
            switch (MusicPlayer.getRepeatMode()) {
                case MediaService.REPEAT_ALL:
                    mPlayingMode.setImageResource(R.drawable.play_icn_loop);
                    Toast.makeText(PlayingActivity.this.getApplication(), getResources().getString(R.string.loop_play),
                            Toast.LENGTH_SHORT).show();
                    break;
                case MediaService.REPEAT_CURRENT:
                    mPlayingMode.setImageResource(R.drawable.play_icn_one);
                    Toast.makeText(PlayingActivity.this.getApplication(), getResources().getString(R.string.play_one),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
