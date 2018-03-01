package com.miaxis.inspection.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.miaxis.inspection.entity.InspectContent;
import com.miaxis.inspection.entity.InspectForm;
import com.miaxis.inspection.entity.InspectItem;
import com.miaxis.inspection.entity.Inspector;
import com.miaxis.inspection.entity.Organization;
import com.miaxis.inspection.entity.ProblemType;
import com.miaxis.inspection.model.local.greenDao.GreenDaoContext;
import com.miaxis.inspection.model.local.greenDao.gen.DaoMaster;
import com.miaxis.inspection.model.local.greenDao.gen.DaoSession;
import com.miaxis.inspection.model.local.greenDao.gen.InspectContentDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectFormDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectItemDao;
import com.miaxis.inspection.model.local.greenDao.gen.OrganizationDao;
import com.miaxis.inspection.model.local.greenDao.gen.ProblemTypeDao;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * Inspection application
 * Created by xu.nan on 2018/1/3.
 */

public class Inspection_App extends Application {

    private DaoSession mDaoSession;
    private static Inspection_App app;

    private static Inspector curInspector;

    @Override
    public void onCreate() {
        super.onCreate();
        initDbHelp();
        app = this;

        ZXingLibrary.initDisplayOpinion(this);
        initProblemType();
//        testDao();
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
        mDaoSession.clear();
        return mDaoSession;
    }

    private void testDao() {

        initProblemType();
        initOrganization();

        initInspectForm();
        initInspectItem();
        initInspectContent();

//        initInspectLog();
//        initInspectContentLog();
    }

    private void initProblemType() {
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
    }

    private void initOrganization() {
        OrganizationDao organizationDao = mDaoSession.getOrganizationDao();
        List<Organization> organizationList = organizationDao.loadAll();
        Organization organization = organizationDao.loadByRowId(1L);
    }

    private void initInspector() {

    }

    private void initInspectForm() {
        InspectFormDao formDao = mDaoSession.getInspectFormDao();
        List<InspectForm> formList = formDao.loadAll();

        if (formList == null || formList.size() == 0) {
            formList = new ArrayList<>();
            InspectForm form1 = new InspectForm();
            form1.setName("安全员日常检查清单");
            form1.setCode("001");
            form1.setTypeName("日常检查");
            formList.add(form1);

            InspectForm form2 = new InspectForm();
            form2.setName("安全员月度检查清单");
            form2.setCode("002");
            form2.setTypeName("月度检查");
            formList.add(form2);

            InspectForm form3 = new InspectForm();
            form3.setName("安全员季度检查清单");
            form3.setCode("003");
            form3.setTypeName("季度检查");
            formList.add(form3);

            InspectForm form4 = new InspectForm();
            form4.setName("安全员年度检查清单");
            form4.setCode("004");
            form4.setTypeName("年度检查");
            formList.add(form4);

            formDao.saveInTx(formList);
        }

    }

    private void initInspectItem() {
        InspectItemDao inspectItemDao = mDaoSession.getInspectItemDao();
        List<InspectItem> itemList = inspectItemDao.loadAll();
        if (itemList == null || itemList.size() == 0) {
            itemList = new ArrayList<>();
            InspectItem item1 = new InspectItem();
            item1.setName("水、电");
            item1.setInspectFormId(1L);
            itemList.add(item1);

            InspectItem item2 = new InspectItem();
            item2.setName("门、窗");
            item2.setInspectFormId(2L);
            itemList.add(item2);

            InspectItem item3 = new InspectItem();
            item3.setName("自助设备");
            item3.setInspectFormId(1L);
            itemList.add(item3);

            InspectItem item4 = new InspectItem();
            item4.setName("视频监控");
            item4.setInspectFormId(2L);
            itemList.add(item4);

            inspectItemDao.saveInTx(itemList);
        }

    }

    private void initInspectLog() {
    }

    private void initInspectContent() {
        InspectContentDao contentDao = mDaoSession.getInspectContentDao();
        List<InspectContent> contentList = contentDao.loadAll();
        if (contentList == null || contentList.size() == 0) {
            contentList = new ArrayList<>();

            InspectContent content1 = new InspectContent();
            content1.setName("每天营业终了巡查水源、无用电源、燃气及计算机设备等是否关闭");
            content1.setInspectItemId(1L);
            contentList.add(content1);

            InspectContent content2 = new InspectContent();
            content2.setName("每天营业前对网点门窗、周边环境进行巡查，有无被撬盗破坏等可疑现象");
            content2.setInspectItemId(1L);
            contentList.add(content2);

            InspectContent content3 = new InspectContent();
            content3.setName("是否存在出钞口及自助设备被非法安装或被破坏等可疑迹象");
            content3.setInspectItemId(2L);
            contentList.add(content3);

            InspectContent content4 = new InspectContent();
            content4.setName("是否存在非法张贴物、针孔摄像机被堵塞（遮挡）");
            content4.setInspectItemId(1L);
            contentList.add(content4);

            contentDao.saveInTx(contentList);
        }
    }


    public static Inspector getCurInspector() {
        return curInspector;
    }

    public static void setCurInspector(Inspector curInspector) {
        Inspection_App.curInspector = curInspector;
    }
}
