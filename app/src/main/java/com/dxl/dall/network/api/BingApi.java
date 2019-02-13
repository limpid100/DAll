package com.dxl.dall.network.api;

import com.dxl.dall.entity.BingDailyPic;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author dxl
 * @date 2019/2/13 15:23
 */
public interface BingApi {
    /**
     * 获取必应每日图片
     * https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN
     */
    @GET("HPImageArchive.aspx")
    Observable<BingDailyPic> getBingDailyPic(@Query("format") String format, @Query("idx") int idx, @Query("n") int n, @Query("mkt") String mkt);

}
