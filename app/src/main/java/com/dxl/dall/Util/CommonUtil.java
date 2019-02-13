package com.dxl.dall.Util;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * @author dxl
 * @date 2019/2/13 16:26
 */
public class CommonUtil {
    /**
     * 倒计时方法
     * @param time
     * @return
     */
    public static Observable<Long> countDown(final int time) {
        return Observable.interval(1, TimeUnit.SECONDS)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) {
                        return time - aLong;
                    }
                }).take(time);
    }
}
