package com.dxl.dall.base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.dxl.dall.database.SqliteDateBaseHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author dxl
 * @date 2019/2/16 14:50
 */
public class BaseApplication extends Application {

    private static ExecutorService executorService = null;
    private static BaseApplication instance = null;
    private SQLiteDatabase mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static ExecutorService getThreadPool() {
        if (executorService == null) {
            executorService = Executors.newCachedThreadPool();
        }

        return executorService;
    }

    public static BaseApplication getInstance() {
        if (instance == null) {
            synchronized (BaseApplication.class) {
                instance = new BaseApplication();
            }
        }
        return instance;
    }


    public SQLiteDatabase getDB() {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = new SqliteDateBaseHelper(this, 1).getWritableDatabase();
        }
        return mDatabase;
    }
}
