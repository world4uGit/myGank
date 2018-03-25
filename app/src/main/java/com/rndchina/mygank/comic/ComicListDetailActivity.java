package com.rndchina.mygank.comic;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.came.viewbguilib.ButtonBgUi;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rndchina.mygank.R;
import com.rndchina.mygank.base.BaseActivity;
import com.rndchina.mygank.comic.model.ComicListDetail;
import com.rndchina.mygank.comic.model.ComicListInfo;
import com.rndchina.mygank.db.ComicRecord;
import com.rndchina.mygank.db.DbManager;
import com.rndchina.mygank.image.ImageManager;
import com.rndchina.mygank.net.ComicApi;
import com.rndchina.mygank.net.HttpManager;
import com.rndchina.mygank.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class ComicListDetailActivity extends BaseActivity {

    @BindView(R.id.activity_des_cover)
    ImageView mActivityDesCover;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_author)
    TextView mTvAuthor;
    @BindView(R.id.tv_des_content)
    TextView mTvDesContent;
    @BindView(R.id.btn_find)
    ButtonBgUi mBtnFind;
    @BindView(R.id.iv_reverse)
    ImageView mIvReverse;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    private View mView;
    private ComicListInfo.EntriesBean info;
    private int comicId;
    private ComicListDetail mComicListDetail;
    private ComicListDetail.ChaptersBean mChaptersBean;
    List<ComicListDetail.ChaptersBean> mList = new ArrayList<>();
    //适配器
    ComicDetailListAdapter mComicDetailListAdapter;

    ComicRecord mComicRecord;


    @Override
    protected void initOptions() {
        ButterKnife.bind(this);
        initAdapter();
        initListener();
        initData();

    }

    private void initListener() {
        mComicDetailListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ComicListDetail.ChaptersBean item = mComicDetailListAdapter.getItem(position);
                Realm realm = Realm.getDefaultInstance();
                if (item != null) {
//                    mRecordDao.updateRecord(item.getComic_id(), item.getName(), item.getIndex(), position);

                    if(mComicRecord != null) {

                        realm.beginTransaction();
                        mComicRecord.setIndex(item.getIndex());
                        mComicRecord.setName(item.getName());
                        mComicRecord.setPosition(position);
                        mComicRecord.setPage(0);

                        realm.commitTransaction();




                    }else{

                        mComicRecord = new ComicRecord(mComicListDetail.getId(), mChaptersBean.getName(), mChaptersBean.getIndex(), position,0);
                        DbManager.getInstence().save(mComicRecord);

                    }
                    Intent intent = new Intent(ComicListDetailActivity.this, ComicPreviewActivity.class);

                    intent.putExtra("index", item.getIndex());
                    intent.putExtra("comicListDetail", mComicListDetail);
                    intent.putExtra("page", 0);

                    startActivity(intent);
                }
            }
        });

        mComicDetailListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                List<ComicListDetail.ChaptersBean> lastChapters = mComicListDetail.getLastChapters();
                if (lastChapters != null)
                    mComicDetailListAdapter.addData(lastChapters);
                mComicDetailListAdapter.loadMoreEnd();
            }
        }, mRvList);

        mBtnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mComicListDetail != null) {
                    Intent intent = new Intent(ComicListDetailActivity.this, ComicPreviewActivity.class);
                    intent.putExtra("comicListDetail", mComicListDetail);
                    if (mComicRecord != null) {
                        intent.putExtra("index", mComicRecord.getIndex());
                        intent.putExtra("page", mComicRecord.getPage());
                    } else {
                        if (mChaptersBean != null) {
                            intent.putExtra("index", mChaptersBean.getIndex());
                            intent.putExtra("page", 0);

                            mComicRecord = new ComicRecord(mComicListDetail.getId(), mChaptersBean.getName(), mChaptersBean.getIndex(), 0,0);
                            DbManager.getInstence().save(mComicRecord);
                        }
                    }
                    startActivity(intent);
                }
            }
        });
    }

    private void initAdapter() {
        //设置布局样式
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        mRvList.setLayoutManager(layoutManager);
        mComicDetailListAdapter = new ComicDetailListAdapter(mList,mComicRecord);
        mRvList.setAdapter(mComicDetailListAdapter);
    }

    private void initData() {
        info = (ComicListInfo.EntriesBean) getIntent().getSerializableExtra("info");
        ImageManager.getInstance().loadImage(this, info.getCover(),mActivityDesCover);
        if(!StringUtils.isEmpty(info.getName())) {
            mTvName.setText(info.getName());
        }
        if(!StringUtils.isEmpty(info.getAuthor())) {
            mTvAuthor.setText(info.getAuthor());
        }
        comicId = info.getId();
        //请求网络获取级数
        getComicData(comicId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //返回的时候刷新当前看到哪一集
        updateCurrRecordInfo();
    }

    private void getComicData(int comicId) {
        ComicApi comicApi = HttpManager.getInstance().getComicApiService();
        comicApi.getComicById(comicId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ComicListDetail>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull ComicListDetail comicListDetail) {

                    showDetail(comicListDetail);

                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });

    }

    private void showDetail(ComicListDetail comicListDetail) {
        mList.clear();
        mComicListDetail = comicListDetail;
        //查看集合是否有经过裁剪、有则显示处理果的列表
        List<ComicListDetail.ChaptersBean> showChapters = comicListDetail.getShowChapters();
        mList = !ListUtils.isEmpty(showChapters) ? showChapters : comicListDetail.getChapters();
        if (!ListUtils.isEmpty(mList))
            mChaptersBean = mList.get(0);

        mComicDetailListAdapter.setNewData(mList);
        //commonAdapter.updateOnItemChildClickListener();

        //更当前列表记录
        comicId = mChaptersBean.getComic_id();
        updateCurrRecordInfo();
        //如果展示的集合不为空，说明还有集数没加载完
    }

    private void updateCurrRecordInfo() {
        mComicRecord = (ComicRecord) DbManager.getInstence().queryModel(ComicRecord.class,comicId);
        if (mComicRecord != null) {
            mBtnFind.setText("继续: ".concat(mComicRecord.getName()));
            if (mComicDetailListAdapter != null)
                mComicDetailListAdapter.updatePosition(mComicRecord.getPosition(), mComicRecord.getIndex());
        } else {
            mBtnFind.setText("start");
            if (mComicDetailListAdapter != null) {
                mComicDetailListAdapter.init();
                mComicDetailListAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_comic_list_detail, null);
        return mView;
    }

    @Override
    protected String initToolbarTitle() {
        return "漫画详情";
    }
}
