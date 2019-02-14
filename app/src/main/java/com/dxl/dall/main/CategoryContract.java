package com.dxl.dall.main;

import com.dxl.dall.base.IView;
import com.dxl.dall.entity.CategoryResult;

public interface CategoryContract {
    interface ICategoryView extends IView {
        String getCategoryName();

        void onSuccess(CategoryResult datas);

        void onError(Throwable e);

        void showLoading(boolean show);
    }

    interface ICategoryPresenter {
        void loadCategoryResult(boolean refresh);
    }
}
