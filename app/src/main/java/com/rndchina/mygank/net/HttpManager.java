package com.rndchina.mygank.net;

import android.util.Log;

import com.blankj.utilcode.util.NetworkUtils;
import com.rndchina.mygank.base.App;
import com.rndchina.mygank.common.Constant;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PC on 2018/1/15.
 */

public class HttpManager {
    private static HttpManager mHttpManager;

    private HttpManager() {
    }

    public static HttpManager getInstance() {
        if (mHttpManager==null) {
            mHttpManager = new HttpManager();
        }
        return mHttpManager;
    }

    public static Interceptor provideOfflineCacheInterceptor ()
    {
        return new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException
            {
                Request request = chain.request();

                /**
                 * 未联网获取缓存数据
                 */
                if (!NetworkUtils.isConnected() )
                {
                    //在20秒缓存有效，此处测试用，实际根据需求设置具体缓存有效时间
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(60 * 60 * 24 * 28, TimeUnit.SECONDS )
                            .build();

                    request = request.newBuilder()
                            .cacheControl( cacheControl )
                            .build();
                }

                return chain.proceed( request );
            }
        };
    }

    public static Interceptor provideCacheInterceptor ()
    {
        return new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException
            {
                Response response = chain.proceed( chain.request() );

                // re-write response header to force use of cache
                // 正常访问同一请求接口（多次访问同一接口），给30秒缓存，超过时间重新发送请求，否则取缓存数据
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(30, TimeUnit.SECONDS )
                        .build();

                return response.newBuilder()
                        .header("Cache-Control", cacheControl.toString() )
                        .build();
            }
        };
    }

    //设置缓存目录和缓存空间大小
    private static Cache provideCache ()
    {
        Cache cache = null;
        try
        {
            cache = new Cache( new File( App.context.getCacheDir(), "http-cache" ),
                    10 * 1024 * 1024 ); // 10 MB
        }
        catch (Exception e)
        {
            Log.e("cache","Could not create Cache!");
        }
        return cache;
    }

    private static OkHttpClient provideOkHttpClient ()
    {
        //日志
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor( interceptor )
                .addInterceptor( provideOfflineCacheInterceptor() )
                .addNetworkInterceptor( provideCacheInterceptor() )
                .cache( provideCache() )
                .build();
    }


    /**
     * 获取指定的接口服务
     *
     * @return
     */
    public Api getApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())
                .build();
        Api api = retrofit.create(Api.class);
        return api;
    }

    OkHttpClient provideComicClient() {
        //日志
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        CommonInterceptor mCommonInterceptor = new CommonInterceptor();

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(httpLoggingInterceptor)
                .addInterceptor(mCommonInterceptor)
                .addInterceptor( provideOfflineCacheInterceptor() )
                .addNetworkInterceptor( provideCacheInterceptor() )
                .cache( provideCache() )
                .build();
        return builder.build();
    }

    /**
     * 获取指定的接口服务
     *
     * @return
     */
    public Api getShopApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_SHOP_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())

                .client(provideOkHttpClient())
                .build();
        Api api = retrofit.create(Api.class);
        return api;
    }
    /**
     * 添加公共参数
     */
    public class CommonInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request oldRequest = chain.request();
            // 添加新的参数
            HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host())
                    .addQueryParameter("package_name", "com.vjson.anime")
                    .addQueryParameter("version_code", "87")
                    .addQueryParameter("version_name", "1.0.8.7")
                    .addQueryParameter("channel", "coolapk")
                    .addQueryParameter("sign", "dcf692dc1d4cead44ce1d5d1b9409e26")
                    .addQueryParameter("platform", "android");
            // 新的请求
            Request newRequest = oldRequest.newBuilder()
                    .method(oldRequest.method(), oldRequest.body())
                    .url(authorizedUrlBuilder.build())
                    .build();

            return chain.proceed(newRequest);
        }
    }

    /**
     * 获取漫画接口服务
     *
     * @return
     */
    public ComicApi getComicApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_COMIC_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideComicClient())
                .build();
        ComicApi comicApi = retrofit.create(ComicApi.class);
        return comicApi;
    }

    /**
     * 获取指定的接口服务
     *
     * @return
     */
    public Api getVideoApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_VIDEO_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())

                .build();
        Api api = retrofit.create(Api.class);
        return api;
    }

    /**
     * 获取指定的接口服务
     *
     * @return
     */
    public Api getApiService(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())

                .build();
        Api api = retrofit.create(Api.class);
        return api;
    }
}
