package com.dxl.dall.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * SearchResult
 * Created by bakumon on 2016/12/19 17:00.
 */
public class SearchResult {

    public int count;
    public boolean error;
    public List<ResultsBean> results;

    public static class ResultsBean {

        public String desc;
        public String ganhuo_id;
        public String publishedAt;
        public String readability;
        public String type;
        public String url;
        public String who;
    }


    public CategoryResult toCategoryResult(){
        CategoryResult categoryResult = new CategoryResult();
        categoryResult.error = this.error;
        List<CategoryResult.ResultsBean> resultsBeans = null;
        if (results != null) {
            resultsBeans = new ArrayList<>();
            for (ResultsBean result : results) {
                CategoryResult.ResultsBean categoryResultBean = new CategoryResult.ResultsBean();
                categoryResultBean.url = result.url;
                categoryResultBean._id = result.ganhuo_id;
                categoryResultBean.desc = result.desc;
                categoryResultBean.type = result.type;
                categoryResultBean.publishedAt = result.publishedAt;
                categoryResultBean.who = result.who;
                resultsBeans.add(categoryResultBean);
            }
        }
        categoryResult.results = resultsBeans;
        return categoryResult;
    }
}
