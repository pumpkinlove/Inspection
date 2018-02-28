package com.miaxis.inspection.model;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.inspection.app.Inspection_App;
import com.miaxis.inspection.entity.Config;
import com.miaxis.inspection.entity.Inspector;
import com.miaxis.inspection.entity.Organization;
import com.miaxis.inspection.entity.ResponseEntity;
import com.miaxis.inspection.model.local.greenDao.gen.ConfigDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectorDao;
import com.miaxis.inspection.model.local.greenDao.gen.OrganizationDao;
import com.miaxis.inspection.model.remote.retrofit.DownInspectorNet;
import com.miaxis.inspection.model.remote.retrofit.DownOrganizationNet;
import com.miaxis.inspection.presenter.IConfigPresenter;
import com.miaxis.inspection.view.fragment.ConfigFragment;

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
 * Created by Administrator on 2018/2/28 0028.
 */

public class ConfigModelImpl implements IConfigModel {

    private IConfigPresenter configPresenter;

    public ConfigModelImpl(IConfigPresenter configPresenter) {
        this.configPresenter = configPresenter;
    }

    @Override
    public void saveConfig(String ip, String port, String orgCode, ConfigFragment.OnConfigClickListener listener) {
        Config config = new Config(1L, ip, port, orgCode);
        doSave(config, listener);
    }

    @Override
    public void fetchConfig() {
        Observable
                .create(new ObservableOnSubscribe<Config>() {
                    @Override
                    public void subscribe(ObservableEmitter<Config> e) throws Exception {
                        ConfigDao configDao = Inspection_App.getInstance().getDaoSession().getConfigDao();
                        Config config = configDao.load(1L);
                        e.onNext(config);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Config>() {
                    @Override
                    public void accept(Config config) throws Exception {
                        configPresenter.fetchConfig(config);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private void doSave(final Config config, final ConfigFragment.OnConfigClickListener listener) {
        Observable
                .just(config)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Config>() {
                    @Override
                    public void accept(Config config) throws Exception {
                        configPresenter.setProgressDialogMessage("正在保存设置...");
                        configPresenter.showProgressDialog();
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Config>() {
                    @Override
                    public void accept(Config config) throws Exception {
                        ConfigDao configDao = Inspection_App.getInstance().getDaoSession().getConfigDao();
                        configDao.insertOrReplace(config);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Config>() {
                    @Override
                    public void accept(Config config) throws Exception {
                        configPresenter.setProgressDialogMessage("设置保存成功，正在下载机构信息...");
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<Config, ObservableSource<ResponseEntity<Organization>>>() {
                    @Override
                    public ObservableSource<ResponseEntity<Organization>> apply(Config config) throws Exception {
                        Retrofit retrofit = new Retrofit.Builder()
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                                .build();
                        DownOrganizationNet net = retrofit.create(DownOrganizationNet.class);
                        return net.downloadOrgnization(config.getOrgCode());
                    }
                })
                .doOnNext(new Consumer<ResponseEntity<Organization>>() {
                    @Override
                    public void accept(ResponseEntity<Organization> responseEntity) throws Exception {
                        OrganizationDao dao = Inspection_App.getInstance().getDaoSession().getOrganizationDao();
                        Organization o = responseEntity.getData();
                        dao.insertOrReplace(o);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ResponseEntity<Organization>>() {
                    @Override
                    public void accept(ResponseEntity<Organization> responseEntity) throws Exception {
                        if (responseEntity.getCode().equals("200")) {
                            configPresenter.setProgressDialogMessage("机构信息下载完成,正在下载检查员信息...");
                            configPresenter.hideProgressDialog();
                        } else {
                            configPresenter.setProgressDialogMessage(responseEntity.getMessage());
                            configPresenter.setProgressDialogCancelable(true);
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<ResponseEntity<Organization>, ObservableSource<ResponseEntity<Inspector>>>() {
                    @Override
                    public ObservableSource<ResponseEntity<Inspector>> apply(ResponseEntity<Organization> organizationResponseEntity) throws Exception {
                        Retrofit retrofit = new Retrofit.Builder()
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                                .build();

                        DownInspectorNet net = retrofit.create(DownInspectorNet.class);

                        return net.downloadInspector(config.getOrgCode());
                    }
                })
                .doOnNext(new Consumer<ResponseEntity<Inspector>>() {
                    @Override
                    public void accept(ResponseEntity<Inspector> responseEntity) throws Exception {
                        InspectorDao dao = Inspection_App.getInstance().getDaoSession().getInspectorDao();
                        List<Inspector> inspectors = responseEntity.getListData();
                        dao.insertOrReplaceInTx(inspectors);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity<Inspector>>() {
                    @Override
                    public void accept(ResponseEntity<Inspector> responseEntity) throws Exception {
                        if (responseEntity.getCode().equals("200")) {
                            configPresenter.setProgressDialogMessage("检查员信息下载完成！");
                            configPresenter.hideProgressDialog();
                            listener.onConfigSave(config);
                        } else {
                            configPresenter.setProgressDialogMessage(responseEntity.getMessage());
                            configPresenter.setProgressDialogCancelable(true);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        configPresenter.setProgressDialogMessage("失败！" + throwable.getMessage());
                        configPresenter.setProgressDialogCancelable(true);
                    }
                });



    }

}
