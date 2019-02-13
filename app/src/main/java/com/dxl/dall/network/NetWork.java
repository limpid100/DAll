package com.dxl.dall.network;

import com.dxl.dall.network.api.BingApi;
import com.dxl.dall.network.api.GankApi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author dxl
 * @date 2019/2/13 15:15
 */
public class NetWork {
    private static GankApi sGankApi;
    private static BingApi sBingApi;

    public static GankApi getGankApi() {
        if (sGankApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(new OkHttpClient())
                    .baseUrl("http://gank.io/api/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            sGankApi = retrofit.create(GankApi.class);

        }
        return sGankApi;
    }

    public static BingApi getBingApi() {
        if (sBingApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
//                    .client(new OkHttpClient())
                    .baseUrl("https://cn.bing.com")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            sBingApi = retrofit.create(BingApi.class);

        }
        return sBingApi;
    }
}
