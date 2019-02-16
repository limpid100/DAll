package com.dxl.dall.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * @author dxl
 * @date 2019/2/16 13:47
 */
public class SqliteDateBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dall.db";

    public static final String CATEGORY_RESULT_TABLE_NAME = "category_result_bean";

    public static final String CREATE_TABLE_SQL =
            "CREATE TABLE " + CATEGORY_RESULT_TABLE_NAME + " ( " +
                    "[ID] varchar(254) PRIMARY KEY NOT NULL DEFAULT '0', " +
                    "[createdAt] time, " +
                    "[desc] text, " +
                    "[images1] nvarchar(254), " +
                    "[images2] nvarchar(254), " +
                    "[images3] nvarchar(254), " +
                    "[source] nvarchar(254), " +
                    "[type] nvarchar(254), " +
                    "[url] nvarchar(254), " +
                    "[isdelete] boolean NOT NULL DEFAULT 0, " +
                    "[isread] boolean NOT NULL DEFAULT 0 " +
                    ");";

    public SqliteDateBaseHelper(@Nullable Context context, @Nullable int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
