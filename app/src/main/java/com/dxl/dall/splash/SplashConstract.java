package com.dxl.dall.splash;

import com.dxl.dall.base.IView;

/**
 * @author dxl
 * @date 2019/2/13 15:02
 */
public interface SplashConstract {
    interface ISplashView extends IView {
        void showImage(String url);

        void onError(Throwable e);
    }

    interface ISplashPresenter {
        void getImageURL();
    }

}
