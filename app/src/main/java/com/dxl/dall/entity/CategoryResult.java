package com.dxl.dall.entity;

import android.content.ContentValues;

import java.util.List;

/**
 * CategoryResult
 * Created by bakumon on 2016/12/8.
 */
public class CategoryResult {

    public boolean error;
    public List<ResultsBean> results;

    public static class ResultsBean {

        public String _id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String who;
        public List<String> images;
        public int isread;

        /**
         * 生成contentValues，数据库插入使用
         * @return
         */
        public ContentValues getContentValues() {
            ContentValues contentValues = new ContentValues();
            contentValues.put("ID", _id);
            contentValues.put("createdAt", createdAt);
            contentValues.put("desc", desc);
            if (images != null && images.size() > 0) {
                contentValues.put("images1", images.get(0));
                if (images.size() > 1) {
                    contentValues.put("images2", images.get(1));
                }
                if (images.size() > 2) {
                    contentValues.put("images3", images.get(2));
                }
            }
            contentValues.put("source", source);
            contentValues.put("type", type);
            contentValues.put("url", url);
            contentValues.put("isdelete", 0);
            contentValues.put("isread", isread);
            return contentValues;
        }
    }


}
