package com.dxl.dall.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.dxl.dall.base.BaseApplication;
import com.dxl.dall.database.SqliteDateBaseHelper;
import com.dxl.dall.entity.CategoryResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库帮助类
 * @author dxl
 * @date 2019/2/16 15:30
 */
public class DatabaseHelper {

    public static CategoryResult.ResultsBean selectCategoryResultBean(String sql, String[] args) {
        CategoryResult.ResultsBean resultsBean = null;
        SQLiteDatabase db = BaseApplication.getInstance().getDB();
        Cursor cursor = db.rawQuery(sql, args);
        if (cursor.moveToFirst()) {
            resultsBean = new CategoryResult.ResultsBean();
            resultsBean._id = cursor.getString(cursor.getColumnIndex("ID"));
            resultsBean.isread = cursor.getInt(cursor.getColumnIndex("isread"));
        }
        cursor.close();

        return resultsBean;
    }


    public static CategoryResult selectCategoryResult(String categoryName) {
        CategoryResult categoryResult = null;
        SQLiteDatabase db = BaseApplication.getInstance().getDB();
        Cursor cursor = db.rawQuery("SELECT ID,createdAt,desc,images1,images2,images3,source,type,url,isread " +
                "FROM " + SqliteDateBaseHelper.CATEGORY_RESULT_TABLE_NAME + " WHERE TYPE = ? ORDER BY createdAt " +
                "desc limit 10", new String[]{categoryName});
        if (cursor.moveToFirst()){
            categoryResult = new CategoryResult();
            List<CategoryResult.ResultsBean> resultsBeans = new ArrayList<>();
            do {
                CategoryResult.ResultsBean resultsBean = new CategoryResult.ResultsBean();
                resultsBean._id = cursor.getString(cursor.getColumnIndex("ID"));
                resultsBean.createdAt = cursor.getString(cursor.getColumnIndex("createdAt"));
                resultsBean.desc = cursor.getString(cursor.getColumnIndex("desc"));
                resultsBean.isread = cursor.getInt(cursor.getColumnIndex("isread"));
                resultsBean.source = cursor.getString(cursor.getColumnIndex("source"));
                resultsBean.url = cursor.getString(cursor.getColumnIndex("url"));
                List<String> images = new ArrayList<>();
                String image1 = cursor.getString(cursor.getColumnIndex("images1"));
                if (!TextUtils.isEmpty(image1)) {
                    images.add(image1);
                }
                String images2 = cursor.getString(cursor.getColumnIndex("images2"));
                if (!TextUtils.isEmpty(images2)) {
                    images.add(images2);
                }
                String images3 = cursor.getString(cursor.getColumnIndex("images3"));
                if (!TextUtils.isEmpty(images3)) {
                    images.add(images3);
                }
                resultsBean.images = images;
                resultsBeans.add(resultsBean);
            }while (cursor.moveToNext());
            categoryResult.results = resultsBeans;

        }
        return categoryResult;
    }

}
