package com.miaxis.inspection.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.inspection.R;
import com.miaxis.inspection.app.Inspection_App;
import com.miaxis.inspection.dao.gen.DaoSession;
import com.miaxis.inspection.dao.gen.InspectContentDao;
import com.miaxis.inspection.dao.gen.InspectItemDao;
import com.miaxis.inspection.dao.gen.InspectPointDao;
import com.miaxis.inspection.entity.Config;
import com.miaxis.inspection.entity.InspectContent;
import com.miaxis.inspection.entity.InspectItem;
import com.miaxis.inspection.entity.InspectPoint;
import com.miaxis.inspection.entity.ResponseEntity;
import com.miaxis.inspection.entity.comm.CheckPoint;
import com.miaxis.inspection.entity.comm.CheckProject;
import com.miaxis.inspection.net.DownInspectPointNet;
import com.miaxis.inspection.view.fragment.ConfigFragment;
import com.miaxis.inspection.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends BaseActivity implements ConfigFragment.OnConfigClickListener {

    @BindView(R.id.fl_config)
    FrameLayout flConfig;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initData();
        initView();

        checkConfig();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tvVersion.setText(R.string.version);
        tvVersion.append(CommonUtil.getVerName(this));
    }

    private void checkConfig() {
        Config config = Inspection_App.getInstance().getDaoSession().getConfigDao().load(1L);
        toggleConfigFragment(config == null);
    }

    private void toggleConfigFragment(boolean showConfigFragment) {
        if (showConfigFragment) {
            flConfig.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.INVISIBLE);
            ConfigFragment configFragment = new ConfigFragment();
            getFragmentManager().beginTransaction().replace(R.id.fl_config, configFragment).commit();
        } else {
            flConfig.setVisibility(View.INVISIBLE);
            btnLogin.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btn_login)
    void onLoginClicked() {
//        startActivity(new Intent(this, MainActivity.class));
        Config config = Inspection_App.getInstance().getDaoSession().getConfigDao().load(1L);
        downloadInspectPoint(config);
    }

    @Override
    public void onConfigSave(Config config) {
        toggleConfigFragment(config == null);
    }

    @Override
    public void onConfigCancel() {

    }

    /**
     * 下载检查点
     */
    private void downloadInspectPoint(Config config) {
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
                            DaoSession daoSession = Inspection_App.getInstance().getDaoSession();
                            InspectPointDao pointDao = daoSession.getInspectPointDao();
                            InspectItemDao itemDao = daoSession.getInspectItemDao();
                            InspectContentDao contentDao = daoSession.getInspectContentDao();

                            List<CheckPoint> checkPointList = checkPointResponseEntity.getListData();
                            List<InspectPoint> inspectPointList = new ArrayList<>();
                            for (int i = 0; i < checkPointList.size(); i++) {
                                CheckPoint checkPoint = checkPointList.get(i);
                                CheckProject checkProject = checkPoint.getProject();
                                InspectPoint inspectPoint = new InspectPoint();
                                inspectPoint.setId(checkPoint.getId());
                                inspectPoint.setRfid(checkPoint.getCpRfid());
                                inspectPoint.setBound(checkPoint.getIsBind() == 1);
                                inspectPoint.setPointName(checkPoint.getCpName());
                                inspectPoint.setOrganizationId(Long.valueOf(checkPoint.getBankId()));
                                inspectPoint.setInspectItemId(checkProject.getId());

                                InspectItem inspectItem = new InspectItem();
                                inspectItem.setId(checkProject.getId());
                                inspectItem.setName(checkProject.getcProjectName());

                                List<CheckProject> checkContentList = checkProject.getProjectContent();
                                List<InspectContent> contentList = new ArrayList<>();
                                for (int j=0; j<checkContentList.size(); j++) {
                                    InspectContent content = new InspectContent();
                                    content.setId(contentList.get(j).getId());
                                    content.setName(checkContentList.get(j).getcProjectContent());
                                    content.setInspectItemId(checkProject.getId());
                                    content.setResultTypeId(Long.valueOf(checkContentList.get(j).getcProjectStatus()));
                                    contentList.add(content);
                                }
                                contentDao.insertOrReplaceInTx(contentList);


                            }

                        } else {

                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity<CheckPoint>>() {
                    @Override
                    public void accept(ResponseEntity<CheckPoint> checkPointResponseEntity) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("tag", "throwable " + throwable.getMessage());
                    }
                });

    }

}
