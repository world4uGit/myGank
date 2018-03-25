package com.rndchina.mygank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.rndchina.mygank.main.MainActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.iv_guide_skip)
    ImageView ivGuideSkip;
    @BindView(R.id.tv_guide_time)
    TextView tvGuideTime;
    private int mTime = 2;
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        disposable =  Observable.timer(2, TimeUnit.SECONDS)

                .repeat()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mTime--;
                        tvGuideTime.setText(mTime+" s");
                        if (mTime == 0) {
                            //倒计时结束
                            startActivity(new Intent(GuideActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });

    }

    @OnClick(R.id.iv_guide_skip)
    public void onClick() {
        startActivity(new Intent(GuideActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消订阅
        if ( disposable != null ){
            disposable.dispose();
        }
    }
}
