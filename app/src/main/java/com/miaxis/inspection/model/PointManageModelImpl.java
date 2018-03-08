package com.miaxis.inspection.model;

import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.inspection.app.Inspection_App;
import com.miaxis.inspection.entity.Config;
import com.miaxis.inspection.entity.InspectContent;
import com.miaxis.inspection.entity.InspectItem;
import com.miaxis.inspection.entity.InspectPoint;
import com.miaxis.inspection.entity.ResponseEntity;
import com.miaxis.inspection.entity.comm.CheckPoint;
import com.miaxis.inspection.entity.comm.CheckProject;
import com.miaxis.inspection.entity.comm.CheckProjectContent;
import com.miaxis.inspection.entity.comm.CheckProjectTime;
import com.miaxis.inspection.model.local.greenDao.gen.CheckProjectTimeDao;
import com.miaxis.inspection.model.local.greenDao.gen.DaoSession;
import com.miaxis.inspection.model.local.greenDao.gen.InspectContentDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectItemDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectPointDao;
import com.miaxis.inspection.model.remote.retrofit.DownInspectPointNet;
import com.miaxis.inspection.model.remote.retrofit.InspectPointNet;
import com.miaxis.inspection.presenter.IPointManagePresenter;
import com.miaxis.inspection.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xu.nan on 2018/3/1.
 */

public class PointManageModelImpl implements IPointManageModel {

    private IPointManagePresenter pointManagePresenter;

    public PointManageModelImpl(IPointManagePresenter pointManagePresenter) {
        this.pointManagePresenter = pointManagePresenter;
    }

    @Override
    public void updatePoint(final InspectPoint inspectPoint) {
        pointManagePresenter.showProgressDialog();
        pointManagePresenter.setProgressDialogMessage("正在更新检查点信息...");
        pointManagePresenter.setProgressDialogCancelable(false);
        Observable
                .just(inspectPoint)
                .observeOn(Schedulers.newThread())
                .map(new Function<InspectPoint, CheckPoint>() {
                    @Override
                    public CheckPoint apply(InspectPoint inspectPoint) throws Exception {
                        inspectPoint.__setDaoSession(Inspection_App.getInstance().getDaoSession());
                        CheckPoint checkPoint = new CheckPoint();
                        checkPoint.setId(inspectPoint.getId());
                        checkPoint.setBankCode(inspectPoint.getOrganization().getBankcode());
                        checkPoint.setBankId(inspectPoint.getOrganization().getId());
                        checkPoint.setCpRfid(inspectPoint.getRfid());
                        checkPoint.setBankName(inspectPoint.getOrganization().getBankname());
                        checkPoint.setOpDate(DateUtil.toHourMinString(inspectPoint.getOpDate()));
                        checkPoint.setOpUser(inspectPoint.getOpUserCode());
                        checkPoint.setOpUserName(inspectPoint.getOpUserName());
                        checkPoint.setCpCode(inspectPoint.getCode());
                        checkPoint.setcProjectCode(inspectPoint.getInspectItem().getCode());
                        return checkPoint;
                    }
                })
                .flatMap(new Function<CheckPoint, ObservableSource<ResponseEntity>>() {
                    @Override
                    public ObservableSource<ResponseEntity> apply(CheckPoint checkPoint) throws Exception {
                        Config config = Inspection_App.getInstance().getDaoSession().getConfigDao().load(1L);
                        Retrofit retrofit = new Retrofit.Builder()
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                                .build();

                        InspectPointNet net = retrofit.create(InspectPointNet.class);
                        return net.updateInspectPoint(new Gson().toJson(checkPoint));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity>() {
                    @Override
                    public void accept(ResponseEntity responseEntity) throws Exception {
                        if (TextUtils.equals("200", responseEntity.getCode())) {
                            InspectPointDao pointDao = Inspection_App.getInstance().getDaoSession().getInspectPointDao();
                            pointDao.insertOrReplace(inspectPoint);
                            pointManagePresenter.showInspectPoints(pointDao.loadAll());
                            pointManagePresenter.setProgressDialogMessage("检查点信息更新完成！");
                            pointManagePresenter.hideProgressDialog();
                        } else {
                            pointManagePresenter.setProgressDialogMessage(responseEntity.getMessage());
                            pointManagePresenter.setProgressDialogCancelable(true);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        pointManagePresenter.setProgressDialogMessage("检查点信息更新失败！" + throwable.getMessage() );
                        pointManagePresenter.setProgressDialogCancelable(true);
                    }
                });
    }

    @Override
    public void downloadPoint() {
        Config config = Inspection_App.getInstance().getDaoSession().getConfigDao().load(1L);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();

        DownInspectPointNet net = retrofit.create(DownInspectPointNet.class);

        net.downloadInspectPoint(config.getOrgCode())
                .subscribeOn(Schedulers.newThread())
                .doOnNext(new Consumer<ResponseEntity<CheckPoint>>() {
                    @Override
                    public void accept(ResponseEntity<CheckPoint> checkPointResponseEntity) throws Exception {
                        String code = checkPointResponseEntity.getCode();
                        if ("200".equals(code)) {
                            analysisCheckPointResp(checkPointResponseEntity);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity<CheckPoint>>() {
                    @Override
                    public void accept(ResponseEntity<CheckPoint> responseEntity) throws Exception {
                        if (responseEntity.getCode().equals("200")) {
                            pointManagePresenter.stopRefresh();
                            InspectPointDao pointDao = Inspection_App.getInstance().getDaoSession().getInspectPointDao();
                            pointManagePresenter.showInspectPoints(pointDao.loadAll());
                        } else {
                            pointManagePresenter.stopRefresh();
                            pointManagePresenter.setProgressDialogMessage(responseEntity.getMessage());
                            pointManagePresenter.setProgressDialogCancelable(true);
                            pointManagePresenter.showProgressDialog();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        pointManagePresenter.stopRefresh();
                        pointManagePresenter.setProgressDialogMessage("刷新失败！" + throwable.getMessage());
                        pointManagePresenter.setProgressDialogCancelable(true);
                        pointManagePresenter.showProgressDialog();
                    }
                });
    }

    private void analysisCheckPointResp(ResponseEntity<CheckPoint> checkPointResponseEntity) {
        DaoSession daoSession = Inspection_App.getInstance().getDaoSession();
        InspectPointDao pointDao = daoSession.getInspectPointDao();
        InspectItemDao itemDao = daoSession.getInspectItemDao();
        InspectContentDao contentDao = daoSession.getInspectContentDao();
        CheckProjectTimeDao checkProjectTimeDao = daoSession.getCheckProjectTimeDao();
        checkProjectTimeDao.deleteAll();
        List<CheckPoint> checkPointList = checkPointResponseEntity.getListData();
        List<InspectPoint> inspectPointList = new ArrayList<>();
        for (int i = 0; i < checkPointList.size(); i++) {
            CheckPoint checkPoint = checkPointList.get(i);
            CheckProject checkProject = checkPoint.getProject();
            InspectPoint inspectPoint = new InspectPoint();
            inspectPoint.setId(checkPoint.getId());
            if (TextUtils.isEmpty(checkPoint.getCpRfid())) {
                inspectPoint.setRfid(null);
            } else {
                inspectPoint.setRfid(checkPoint.getCpRfid());
            }
            inspectPoint.setBound(!TextUtils.isEmpty(checkPoint.getCpRfid()));
            inspectPoint.setPointName(checkPoint.getCpName());
            inspectPoint.setOrganizationId(Long.valueOf(checkPoint.getBankId()));
            inspectPoint.setInspectItemId(checkProject.getId());

            inspectPointList.add(inspectPoint);

            InspectItem inspectItem = new InspectItem();
            inspectItem.setId(checkProject.getId());
            inspectItem.setName(checkProject.getcProjectName());
            inspectItem.setInspectFormCode(checkProject.getParentCode());
            inspectItem.setCode(checkProject.getcProjectCode());
            inspectItem.setCount(checkProject.getcProjectTimes());
            inspectItem.setFrequencyType(checkProject.getcProjectTimesType());

            List<CheckProjectTime> cpt = checkProject.getcProjectTime();
            for (int j = 0; j < cpt.size(); j ++) {
                cpt.get(j).setCProjectCode(checkProject.getcProjectCode());
            }
            checkProjectTimeDao.insertOrReplaceInTx(cpt);

            itemDao.insertOrReplace(inspectItem);

            List<CheckProjectContent> checkContentList = checkPoint.getProjectContent();
            if (checkContentList != null && checkContentList.size() > 0) {
                List<InspectContent> contentList = new ArrayList<>();
                for (int j = 0; j < checkContentList.size(); j++) {
                    InspectContent content = new InspectContent();
                    content.setId(checkContentList.get(j).getId());
                    content.setName(checkContentList.get(j).getcProjectContent());
                    content.setInspectItemId(checkProject.getId());
                    content.setResultType(Integer.valueOf(checkContentList.get(j).getcProjectStatus()));
                    contentList.add(content);
                }
                if (contentList.size() > 0) {
                    contentDao.insertOrReplaceInTx(contentList);
                }
            }
        }
        if (inspectPointList.size() > 0) {
            pointDao.insertOrReplaceInTx(inspectPointList);
        }
    }
}
