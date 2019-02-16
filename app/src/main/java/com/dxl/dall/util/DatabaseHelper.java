package com.dxl.dall.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dxl.dall.base.BaseApplication;
import com.dxl.dall.entity.CategoryResult;

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

}
