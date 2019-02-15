package com.dxl.dall.search;

import com.dxl.dall.base.IView;
import com.dxl.dall.entity.CategoryResult;

/**
 * @author dxl
 * @date 2019/2/15 14:22
 */
public interface SearchContract {

    interface ISearchView extends IView {
        void onSuccess(CategoryResult datas);

        void onError(Throwable e);
    }

    interface ISearchPresenter {
        void loadCategoryResult(String search, boolean refresh);
    }
}
