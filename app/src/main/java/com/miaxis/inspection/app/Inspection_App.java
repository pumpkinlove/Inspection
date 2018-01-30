package com.miaxis.inspection.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.miaxis.inspection.dao.GreenDaoContext;
import com.miaxis.inspection.dao.gen.DaoMaster;
import com.miaxis.inspection.dao.gen.DaoSession;

/**
 * Inspection application
 * Created by xu.nan on 2018/1/3.
 */

public class Inspection_App extends Application {

    private DaoSession mDaoSession;
    private static Inspection_App app;

    @Override
    public void onCreate() {
        super.onCreate();
        initDbHelp();
        app = this;
    }

    private void initDbHelp() {
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(new GreenDaoContext(this), "Inspection.db", null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        DaoMaster mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public static Inspection_App getInstance() {
        return app;
    }

    public DaoSession getDaoSession() {
//        mDaoSession.clear();
        return mDaoSession;
    }

}
