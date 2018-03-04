package com.miaxis.inspection.model;

import android.text.TextUtils;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.inspection.app.Inspection_App;
import com.miaxis.inspection.entity.Config;
import com.miaxis.inspection.entity.InspectContent;
import com.miaxis.inspection.entity.InspectForm;
import com.miaxis.inspection.entity.InspectItem;
import com.miaxis.inspection.entity.InspectPoint;
import com.miaxis.inspection.entity.Inspector;
import com.miaxis.inspection.entity.ResponseEntity;
import com.miaxis.inspection.entity.Task;
import com.miaxis.inspection.entity.comm.CheckPoint;
import com.miaxis.inspection.entity.comm.CheckProject;
import com.miaxis.inspection.entity.comm.CheckProjectContent;
import com.miaxis.inspection.entity.comm.CommTask;
import com.miaxis.inspection.entity.comm.Permission;
import com.miaxis.inspection.model.local.greenDao.gen.DaoSession;
import com.miaxis.inspection.model.local.greenDao.gen.InspectContentDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectFormDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectItemDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectPointDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectorDao;
import com.miaxis.inspection.model.local.greenDao.gen.PermissionDao;
import com.miaxis.inspection.model.local.greenDao.gen.TaskDao;
import com.miaxis.inspection.model.remote.retrofit.DownInspectPointNet;
import com.miaxis.inspection.model.remote.retrofit.DownInspectorNet;
import com.miaxis.inspection.model.remote.retrofit.DownPermissionNet;
import com.miaxis.inspection.model.remote.retrofit.DownTaskNet;
import com.miaxis.inspection.presenter.ILoginPresenter;
import com.miaxis.inspection.utils.DateUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 登录模块model实现类  处理登录业务逻辑
 * Created by xu.nan on 2018/2/28.
 */

public class LoginModelImpl implements ILoginModel {

    private ILoginPresenter loginPresenter;

    public LoginModelImpl(ILoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void checkConfig() {
        Observable
                .create(new ObservableOnSubscribe<Config>() {
                    @Override
                    public void subscribe(ObservableEmitter<Config> e) throws Exception {
                        Config config = Inspection_App.getInstance().getDaoSession().getConfigDao().load(1L);
                        e.onNext(config);
                        e.onComplete();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Config>() {
                    @Override
                    public void accept(Config config) throws Exception {
                        loginPresenter.onHideConfig();
                        loginPresenter.syncInspector(config);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loginPresenter.onShowConfig();
                    }
                });
    }

    @Override
    public void checkPassword(Inspector inspector, String password) {
        if (password.equals(inspector.getPassword())) {
            Inspection_App.setCurInspector(inspector);
            Config config = Inspection_App.getInstance().getDaoSession().getConfigDao().load(1L);
            doLogin(inspector, config);
        } else {
            loginPresenter.onLoginFailed();
        }
    }

    @Override
    public void syncInspector(Config config) {
        downloadInspector(config);
    }

    /**
     * 下载检查员
     * @param config
     */
    private void downloadInspector(Config config) {
        // TODO: 2018/2/28 下载失败，读本地数据库的检查员
        loginPresenter.showProgressDialog();
        loginPresenter.setProgressDialogCancelable(false);
        loginPresenter.showProgressMessage("正在同步检查员信息...");
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();

        DownInspectorNet net = retrofit.create(DownInspectorNet.class);

        net.downloadInspector(config.getOrgCode())
                .subscribeOn(Schedulers.newThread())
                .doOnNext(new Consumer<ResponseEntity<Inspector>>() {
                    @Override
                    public void accept(ResponseEntity<Inspector> responseEntity) throws Exception {
                        if (responseEntity.getCode().equals("200")) {
                            InspectorDao dao = Inspection_App.getInstance().getDaoSession().getInspectorDao();
                            List<Inspector> inspectors = responseEntity.getListData();
                            dao.insertOrReplaceInTx(inspectors);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity<Inspector>>() {
                    @Override
                    public void accept(ResponseEntity<Inspector> responseEntity) throws Exception {
                        if (responseEntity.getCode().equals("200")) {
                            loginPresenter.showProgressMessage("检查员信息下载完成！");
                            loginPresenter.hideProgressDialog();
                            loginPresenter.onShowInspectorList(responseEntity.getListData());
                        } else {
                            loginPresenter.showProgressMessage(responseEntity.getMessage());
                            loginPresenter.setProgressDialogCancelable(true);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("loginModelImpl", "ee" + throwable.getMessage());
                        loginPresenter.showProgressMessage("检查员信息下载失败！");
                        loginPresenter.setProgressDialogCancelable(true);
                    }
                });
    }

    @Override
    public Observable<ResponseEntity<CheckPoint>> downloadInspectPoint(Config config) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();

        DownInspectPointNet net = retrofit.create(DownInspectPointNet.class);

        return net.downloadInspectPoint(config.getOrgCode())
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
                .doOnNext(new Consumer<ResponseEntity<CheckPoint>>() {
                    @Override
                    public void accept(ResponseEntity<CheckPoint> responseEntity) throws Exception {
                        if (responseEntity.getCode().equals("200")) {
                            loginPresenter.showProgressMessage("检查点下载完成！");
                            loginPresenter.hideProgressDialog();
                        } else {
                            loginPresenter.showProgressMessage(responseEntity.getMessage());
                            loginPresenter.setProgressDialogCancelable(true);
                        }
                    }
                });

    }

    @Override
    public Observable<ResponseEntity<Permission>> downloadPermission(final Inspector inspector, Config config) {
        loginPresenter.showProgressDialog();
        loginPresenter.setProgressDialogCancelable(false);
        loginPresenter.showProgressMessage("正在下载检查员权限...");
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();

        DownPermissionNet net = retrofit.create(DownPermissionNet.class);
        return net.downPermission(inspector.getCensorCode())
                .subscribeOn(Schedulers.newThread())
                .doOnNext(new Consumer<ResponseEntity<Permission>>() {
                    @Override
                    public void accept(ResponseEntity<Permission> permissionResponseEntity) throws Exception {
                        if ("200".equals(permissionResponseEntity.getCode())) {
                            List<Permission> permissionList = permissionResponseEntity.getListData();
                            for (int i = 0; i < permissionList.size(); i ++) {
                                permissionList.get(i).setInspectorCode(inspector.getCensorCode());
                            }
                            PermissionDao dao = Inspection_App.getInstance().getDaoSession().getPermissionDao();
                            dao.insertOrReplaceInTx(permissionList);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ResponseEntity<Permission>>() {
                    @Override
                    public void accept(ResponseEntity<Permission> responseEntity) throws Exception {
                        if (responseEntity.getCode().equals("200")) {
                            loginPresenter.showProgressMessage("检查员权限下载完成！");
                            loginPresenter.hideProgressDialog();
                            loginPresenter.onLoginSucceed();
                        } else {
                            loginPresenter.showProgressMessage(responseEntity.getMessage());
                            loginPresenter.setProgressDialogCancelable(true);
                        }
                    }
                });
    }

    @Override
    public Observable<ResponseEntity<CommTask>> downloadTask(Config config) {
        loginPresenter.showProgressDialog();
        loginPresenter.setProgressDialogCancelable(false);
        loginPresenter.showProgressMessage("正在下载任务...");
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();

        DownTaskNet net = retrofit.create(DownTaskNet.class);

        return net.downTask(config.getOrgCode())
                .subscribeOn(Schedulers.newThread())
                .doOnNext(new Consumer<ResponseEntity<CommTask>>() {
                    @Override
                    public void accept(ResponseEntity<CommTask> taskResponseEntity) throws Exception {
                        if ("200".equals(taskResponseEntity.getCode())) {
                            analysisTaskResp(taskResponseEntity);
                        }

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ResponseEntity<CommTask>>() {
                    @Override
                    public void accept(ResponseEntity<CommTask> responseEntity) throws Exception {
                        if (responseEntity.getCode().equals("200")) {
                            loginPresenter.showProgressMessage("任务下载完成！");
                            loginPresenter.hideProgressDialog();
                            loginPresenter.onLoginSucceed();
                        } else {
                            loginPresenter.showProgressMessage(responseEntity.getMessage());
                            loginPresenter.setProgressDialogCancelable(true);
                        }
                    }
                });


    }

    private void analysisTaskResp(ResponseEntity<CommTask> taskResponseEntity) throws ParseException {
        TaskDao taskDao = Inspection_App.getInstance().getDaoSession().getTaskDao();
        InspectFormDao formDao = Inspection_App.getInstance().getDaoSession().getInspectFormDao();

        List<Task> aTaskList = taskDao.loadAll();
        List<InspectForm> aFormList = formDao.loadAll();

        List<CommTask> commTaskList = taskResponseEntity.getListData();
        List<Task> taskList = new ArrayList<>();
        for (int i = 0; i < commTaskList.size(); i ++) {
            Task task = new Task();
            CommTask commTask = commTaskList.get(i);

            CheckProject checkForm = commTask.getProject();
            InspectForm inspectForm = new InspectForm();
            inspectForm.setId(checkForm.getId());
            inspectForm.setCode(checkForm.getcProjectCode());
            inspectForm.setType(checkForm.getcProjectType());
            inspectForm.setTypeName(checkForm.getcProjectTypeName());
            inspectForm.setRequirement(checkForm.getcProjectContent());
            inspectForm.setCompletionRate(checkForm.getcProjectComplete());
            inspectForm.setRoleName(checkForm.getcProjectRole());
//            inspectForm.setOpDate(DateUtil.fromMonthDay(checkForm.getOpDate()));
            inspectForm.setOpUser(checkForm.getOpUser());
            inspectForm.setOpUsername(checkForm.getOpUserName());

            formDao.insertOrReplace(inspectForm);

            task.setTaskName(commTask.getTaskName());
            task.setId(commTask.getId());
            task.setInspectFormId(inspectForm.getId());

            taskList.add(task);
        }

        if (taskList.size() > 0) {
            taskDao.insertOrReplaceInTx(taskList);
        }
    }

    private void analysisCheckPointResp(ResponseEntity<CheckPoint> checkPointResponseEntity) {
        DaoSession daoSession = Inspection_App.getInstance().getDaoSession();
        InspectPointDao pointDao = daoSession.getInspectPointDao();
        InspectItemDao itemDao = daoSession.getInspectItemDao();
        InspectContentDao contentDao = daoSession.getInspectContentDao();

        List<InspectPoint> aPointList = pointDao.loadAll();
        List<InspectItem> aItemList = itemDao.loadAll();
        List<InspectContent> aContentList = contentDao.loadAll();

        List<CheckPoint> checkPointList = checkPointResponseEntity.getListData();
        List<InspectPoint> inspectPointList = new ArrayList<>();
        for (int i = 0; i < checkPointList.size(); i++) {
            CheckPoint checkPoint = checkPointList.get(i);
            CheckProject checkProject = checkPoint.getProject();
            InspectPoint inspectPoint = new InspectPoint();
            inspectPoint.setId(checkPoint.getId());
            inspectPoint.setRfid(checkPoint.getCpRfid());
            inspectPoint.setBound(!TextUtils.isEmpty(checkPoint.getCpRfid()));
            inspectPoint.setPointName(checkPoint.getCpName());
            inspectPoint.setOrganizationId(Long.valueOf(checkPoint.getBankId()));
            inspectPoint.setInspectItemId(checkProject.getId());

            inspectPointList.add(inspectPoint);

            InspectItem inspectItem = new InspectItem();
            inspectItem.setId(checkProject.getId());
            inspectItem.setName(checkProject.getcProjectName());
            inspectItem.setInspectFormCode(checkProject.getParentCode());

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

    private void doLogin(final Inspector inspector, final Config config) {
        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();
        Observable
                .just(inspector)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Inspector>() {
                    @Override
                    public void accept(Inspector inspector) throws Exception {
                        loginPresenter.showProgressDialog();
                        loginPresenter.setProgressDialogCancelable(false);
                        loginPresenter.showProgressMessage("密码验证通过，正在下载检查员权限...");
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<Inspector, ObservableSource<ResponseEntity<Permission>>>() {
                    @Override
                    public ObservableSource<ResponseEntity<Permission>> apply(Inspector inspector) throws Exception {
                        DownPermissionNet net = retrofit.create(DownPermissionNet.class);
                        return net.downPermission(inspector.getCensorCode());
                    }
                })
                .doOnNext(new Consumer<ResponseEntity<Permission>>() {
                    @Override
                    public void accept(ResponseEntity<Permission> responseEntity) throws Exception {
                        if ("200".equals(responseEntity.getCode())) {
                            List<Permission> permissionList = responseEntity.getListData();
                            for (int i = 0; i < permissionList.size(); i ++) {
                                permissionList.get(i).setInspectorCode(inspector.getCensorCode());
                            }
                            PermissionDao dao = Inspection_App.getInstance().getDaoSession().getPermissionDao();
                            dao.insertOrReplaceInTx(permissionList);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ResponseEntity<Permission>>() {
                    @Override
                    public void accept(ResponseEntity<Permission> responseEntity) throws Exception {
                        if (responseEntity.getCode().equals("200")) {
                            loginPresenter.showProgressMessage("检查员权限下载完成！正在下载检查点信息...");
                        } else {
                            loginPresenter.showProgressMessage(responseEntity.getMessage());
                            loginPresenter.setProgressDialogCancelable(true);
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<ResponseEntity<Permission>, ObservableSource<ResponseEntity<CheckPoint>>>() {
                    @Override
                    public ObservableSource<ResponseEntity<CheckPoint>> apply(ResponseEntity<Permission> permissionResponseEntity) throws Exception {
                        DownInspectPointNet net = retrofit.create(DownInspectPointNet.class);
                        return net.downloadInspectPoint(config.getOrgCode());
                    }
                })
                .doOnNext(new Consumer<ResponseEntity<CheckPoint>>() {
                    @Override
                    public void accept(ResponseEntity<CheckPoint> responseEntity) throws Exception {
                        if (TextUtils.equals(responseEntity.getCode(), "200")) {
                            analysisCheckPointResp(responseEntity);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ResponseEntity<CheckPoint>>() {
                    @Override
                    public void accept(ResponseEntity<CheckPoint> responseEntity) throws Exception {
                        if (TextUtils.equals(responseEntity.getCode(), "200")) {
                            loginPresenter.showProgressMessage("检查员权限下载完成！正在下载任务...");
                        } else {
                            loginPresenter.showProgressMessage(responseEntity.getMessage());
                            loginPresenter.setProgressDialogCancelable(true);
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<ResponseEntity<CheckPoint>, ObservableSource<ResponseEntity<CommTask>>>() {
                    @Override
                    public ObservableSource<ResponseEntity<CommTask>> apply(ResponseEntity<CheckPoint> responseEntity) throws Exception {
                        DownTaskNet net = retrofit.create(DownTaskNet.class);
                        return net.downTask(config.getOrgCode());
                    }
                })
                .doOnNext(new Consumer<ResponseEntity<CommTask>>() {
                    @Override
                    public void accept(ResponseEntity<CommTask> responseEntity) throws Exception {
                        if (TextUtils.equals(responseEntity.getCode(), "200")) {
                            analysisTaskResp(responseEntity);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity<CommTask>>() {
                    @Override
                    public void accept(ResponseEntity<CommTask> responseEntity) throws Exception {
                        if (TextUtils.equals(responseEntity.getCode(), "200")) {
                            loginPresenter.showProgressMessage("检查任务下载完成！");
                            loginPresenter.hideProgressDialog();
                            loginPresenter.onLoginSucceed();
                        } else {
                            loginPresenter.showProgressMessage(responseEntity.getMessage());
                            loginPresenter.setProgressDialogCancelable(true);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loginPresenter.showProgressMessage("失败！ " + throwable.getMessage());
                        loginPresenter.setProgressDialogCancelable(true);
                        loginPresenter.onLoginFailed();
                    }
                });

    }

}
