package com.miaxis.inspection.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.miaxis.inspection.dao.GreenDaoContext;
import com.miaxis.inspection.dao.gen.DaoMaster;
import com.miaxis.inspection.dao.gen.DaoSession;
import com.miaxis.inspection.dao.gen.InspectFormDao;
import com.miaxis.inspection.dao.gen.ProblemTypeDao;
import com.miaxis.inspection.entity.InspectContent;
import com.miaxis.inspection.entity.InspectForm;
import com.miaxis.inspection.entity.ProblemType;

import java.util.List;

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

        testDao();
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

    private void testDao() {
        ProblemTypeDao problemTypeDao = mDaoSession.getProblemTypeDao();

        List<ProblemType> problemTypeList = problemTypeDao.loadAll();
        if (problemTypeList == null || problemTypeList.size() == 0) {
            ProblemType problemType1 = new ProblemType();
            problemType1.setType(1L);
            problemType1.setTypeName("消防类");
            problemTypeDao.save(problemType1);

            ProblemType problemType2 = new ProblemType();
            problemType2.setType(2L);
            problemType2.setTypeName("人防类");
            problemTypeDao.save(problemType2);

            ProblemType problemType3 = new ProblemType();
            problemType3.setType(3L);
            problemType3.setTypeName("技防类");
            problemTypeDao.save(problemType3);

            ProblemType problemType4 = new ProblemType();
            problemType4.setType(4L);
            problemType4.setTypeName("制度类");
            problemTypeDao.save(problemType4);

            ProblemType problemType5 = new ProblemType();
            problemType5.setType(5L);
            problemType5.setTypeName("物防类");
            problemTypeDao.save(problemType5);

            ProblemType problemType6 = new ProblemType();
            problemType6.setType(6L);
            problemType6.setTypeName("其它类");
            problemTypeDao.save(problemType6);
        }

        InspectFormDao inspectFormDao = mDaoSession.getInspectFormDao();

        InspectForm inspectForm1 = new InspectForm();
        inspectForm1.setCode("01");
        inspectForm1.setName("网点保安员日常安全检查");


        InspectContent inspectContent = new InspectContent();
        inspectContent.setProblemTypeCode(2L);
        inspectContent.setName("对门前进行安检，清理运钞车位车辆及杂物");

    }

}
