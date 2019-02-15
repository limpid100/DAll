package com.dxl.dall.main;

import com.dxl.dall.base.IView;

/**
 * @author dxl
 * @date 2019/2/15 10:52
 */
public interface MainContract {

    interface IMainView extends IView {
        /**
         * 获取顶部图片成功后，显示图片
         * @param url 图片URL
         */
        void showTopImage(String url);

        /**
         * 获取失败
         * @param e 异常
         */
        void getImageError(Throwable e);

        /**
         * 是否正在加载图片
         * @param isLoading 是否正在加载中
         */
        void isLoadingImage(boolean isLoading);
    }

    interface IMainPresenter{
        /**
         * 获取顶部图片URL
         */
        void getTopImageUrl();
    }

}
