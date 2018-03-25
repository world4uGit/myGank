package com.rndchina.mygank.net;

import com.rndchina.mygank.base.Result;
import com.rndchina.mygank.db.GankModel;
import com.rndchina.mygank.me.model.VideoModel;
import com.rndchina.mygank.music.model.VideoData;
import com.rndchina.mygank.music.model.VideoDetail;
import com.rndchina.mygank.read.model.ReadModel;
import com.rndchina.mygank.shop.model.BannerBean;
import com.rndchina.mygank.shop.model.CategoryBean;
import com.rndchina.mygank.shop.model.CategoryListBean;
import com.rndchina.mygank.shop.model.SecondBean;
import com.rndchina.mygank.shop.model.YLikeBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by PC on 2018/1/15.
 */

public interface Api {

    @GET("data/{category}/{pageSize}/{page}")
    Observable<GankModel> getCategroyData(@Path("category") String category,
                                          @Path("pageSize") int pageSize,
                                          @Path("page") int page);

    @GET("search/query/{searchkeyword}/category/all/count/10/page/{page} ")
    Observable<GankModel> getSearchData(@Path("searchkeyword") String searchkeyword,
                                        @Path("page") int page);

    @GET("api/v1/info/{page}/{pageSize}")
    Observable<ReadModel> getReadData(@Path("page") int page,
                                      @Path("pageSize") int pageSize);
    @GET("/banner")
    Observable<BannerBean> getBannerData();


    @GET("/seckill")
    Observable<SecondBean> getSecondData();


    @GET("/getYourFav")
    Observable<YLikeBean> getYLikeData();


    @GET("/category")
    Observable<CategoryBean> getCategoryData();


    @GET("/category")
    Observable<CategoryListBean> getCategoryListData(@Query("parentId") int parentId);

    @GET("api/v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    Observable<List<VideoModel>> getCategories();


    @GET("/")
    Observable<Result.Data<List<VideoData>>> getVideoList(@Query("c") String c, @Query("a") String a, @Query("p") int page, @Query("model") int model, @Query("page_id") String pageId, @Query("create_time") String createTime, @Query("client") String client, @Query("version") String version, @Query("time") long time, @Query("device_id") String deviceId, @Query("show_sdv") int show_sdv);

    @GET("/")
    Observable<Result.Data<VideoDetail>> getVideoDetail(@Query("c") String c, @Query("a") String a, @Query("post_id") String post_id, @Query("show_sdv") int show_sdv);



}
