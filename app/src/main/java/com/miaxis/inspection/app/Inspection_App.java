package com.miaxis.inspection.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.miaxis.inspection.dao.GreenDaoContext;
import com.miaxis.inspection.dao.gen.DaoMaster;
import com.miaxis.inspection.dao.gen.DaoSession;
import com.miaxis.inspection.dao.gen.InspectContentDao;
import com.miaxis.inspection.dao.gen.InspectContentLogDao;
import com.miaxis.inspection.dao.gen.InspectFormDao;
import com.miaxis.inspection.dao.gen.InspectItemDao;
import com.miaxis.inspection.dao.gen.InspectLogDao;
import com.miaxis.inspection.dao.gen.InspectPointDao;
import com.miaxis.inspection.dao.gen.OrganizationDao;
import com.miaxis.inspection.dao.gen.ProblemTypeDao;
import com.miaxis.inspection.dao.gen.ResultTypeDao;
import com.miaxis.inspection.dao.gen.TaskDao;
import com.miaxis.inspection.entity.InspectContent;
import com.miaxis.inspection.entity.InspectContentLog;
import com.miaxis.inspection.entity.InspectForm;
import com.miaxis.inspection.entity.InspectItem;
import com.miaxis.inspection.entity.InspectLog;
import com.miaxis.inspection.entity.InspectPoint;
import com.miaxis.inspection.entity.Organization;
import com.miaxis.inspection.entity.ProblemType;
import com.miaxis.inspection.entity.ResultType;
import com.miaxis.inspection.entity.Task;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

        ZXingLibrary.initDisplayOpinion(this);

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
        mDaoSession.clear();
        return mDaoSession;
    }

    private void testDao() {

        initResultType();
        initProblemType();
        initOrganization();
        initInspectPoint();

        initInspectForm();
        initInspectItem();
        initInspectContent();

//        initInspectLog();
//        initInspectContentLog();
        initTask();
    }

    private void initTask() {
        TaskDao taskDao = mDaoSession.getTaskDao();
        List<Task> taskList = taskDao.loadAll();
        if (taskList == null || taskList.size() == 0) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(2018,2,8, 6,0,0);
            Date d1 = calendar.getTime();
            calendar.set(2018,2,8, 23,59,59);
            Date d2 = calendar.getTime();
            calendar.set(2018,2,7, 6,0,0);
            Date d3 = calendar.getTime();
            calendar.set(2018,2,7, 23,59,59);
            Date d4 = calendar.getTime();
            calendar.set(2018,2,6, 6,0,0);
            Date d5 = calendar.getTime();
            calendar.set(2018,2,6, 23,59,59);
            Date d6 = calendar.getTime();

         taskList = new ArrayList<>();

            Task task1 = new Task();
            task1.setInspectFormId(1L);
            task1.setName("日常检查任务");
            task1.setBeginTime(d1);
            task1.setEndTime(d2);

            taskList.add(task1);

            Task task2 = new Task();
            task2.setInspectFormId(2L);
            task2.setName("日常检查任务");
            task2.setBeginTime(d3);
            task2.setEndTime(d4);

            taskList.add(task2);

            Task task3 = new Task();
            task3.setInspectFormId(3L);
            task3.setName("月度检查任务");
            task3.setBeginTime(d5);
            task3.setEndTime(d6);

            taskList.add(task3);

            taskDao.saveInTx(taskList);

        }
    }

    private void initResultType() {
        ResultTypeDao typeDao = mDaoSession.getResultTypeDao();
        List<ResultType> resultTypeList = typeDao.loadAll();
        if (resultTypeList == null || resultTypeList.size() == 0) {
            resultTypeList = new ArrayList<>();

            ResultType type1 = new ResultType();
            type1.setIsProblem(false);
            type1.setResultName("正常");
            resultTypeList.add(type1);

            ResultType type2 = new ResultType();
            type2.setIsProblem(true);
            type2.setResultName("异常");
            resultTypeList.add(type2);

            typeDao.saveInTx(resultTypeList);

        }
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
        Organization organization = organizationDao.load(1L);
        if (organization == null) {
            organization = new Organization();
            organization.setId(1L);
            organization.setName("测试机构");
            organization.setCode("001");
            organization.setNode("1.0");
            organizationDao.insert(organization);
        }
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
            item1.setCount(5);
            item1.setFrequencyType("次/天");
            itemList.add(item1);

            InspectItem item2 = new InspectItem();
            item2.setName("门、窗");
            item2.setInspectFormId(2L);
            item1.setCount(4);
            item1.setFrequencyType("次/天");
            itemList.add(item2);

            InspectItem item3 = new InspectItem();
            item3.setName("自助设备");
            item3.setInspectFormId(1L);
            item1.setCount(2);
            item1.setFrequencyType("次/天");
            itemList.add(item3);

            InspectItem item4 = new InspectItem();
            item4.setName("视频监控");
            item4.setInspectFormId(2L);
            item1.setCount(3);
            item1.setFrequencyType("次/天");
            itemList.add(item4);

            inspectItemDao.saveInTx(itemList);
        }

    }

    private void initInspectPoint() {
        InspectPointDao pointDao = mDaoSession.getInspectPointDao();
        List<InspectPoint> pointList = pointDao.loadAll();
        if (pointList == null || pointList.size() == 0) {
            pointList = new ArrayList<>();

            InspectPoint point1 = new InspectPoint();
            point1.setPointName("监控室");
            point1.setInspectItemId(1L);
            point1.setOrganizationId(1L);
            pointList.add(point1);

            InspectPoint point2 = new InspectPoint();
            point2.setPointName("周围环境");
            point2.setInspectItemId(1L);
            point2.setOrganizationId(1L);
            pointList.add(point2);

            InspectPoint point3 = new InspectPoint();
            point3.setPointName("食堂");
            point3.setInspectItemId(2L);
            point3.setOrganizationId(1L);
            pointList.add(point3);

            InspectPoint point4 = new InspectPoint();
            point4.setPointName("营业室");
            point4.setInspectItemId(2L);
            point4.setOrganizationId(1L);
            pointList.add(point4);

            pointDao.saveInTx(pointList);

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

}
