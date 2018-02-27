package com.miaxis.inspection.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.InspectorSpinnerAdapter;
import com.miaxis.inspection.app.Inspection_App;
import com.miaxis.inspection.dao.gen.DaoSession;
import com.miaxis.inspection.dao.gen.InspectContentDao;
import com.miaxis.inspection.dao.gen.InspectItemDao;
import com.miaxis.inspection.dao.gen.InspectPointDao;
import com.miaxis.inspection.entity.Config;
import com.miaxis.inspection.entity.InspectContent;
import com.miaxis.inspection.entity.InspectItem;
import com.miaxis.inspection.entity.InspectPoint;
import com.miaxis.inspection.entity.Inspector;
import com.miaxis.inspection.entity.ResponseEntity;
import com.miaxis.inspection.entity.comm.CheckPoint;
import com.miaxis.inspection.entity.comm.CheckProject;
import com.miaxis.inspection.entity.comm.CheckProjectContent;
import com.miaxis.inspection.net.DownInspectPointNet;
import com.miaxis.inspection.utils.CommonUtil;
import com.miaxis.inspection.view.fragment.ConfigFragment;

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
    @BindView(R.id.sp_inspector_name)
    Spinner spInspectorName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.ll_input)
    LinearLayout llInput;

    private ProgressDialog pdLogin;
    private InspectorSpinnerAdapter spinnerAdapter;
    private List<Inspector> inspectorList;

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
        inspectorList = Inspection_App.getInstance().getDaoSession().getInspectorDao().loadAll();
        spinnerAdapter = new InspectorSpinnerAdapter(inspectorList, this);
    }

    @Override
    protected void initView() {
        pdLogin = new ProgressDialog(this);
        tvVersion.setText(R.string.version);
        tvVersion.append(CommonUtil.getVerName(this));
        spInspectorName.setAdapter(spinnerAdapter);
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
        pdLogin.show();
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
                                inspectPoint.setBound(TextUtils.isEmpty(checkPoint.getCpRfid()));
                                inspectPoint.setPointName(checkPoint.getCpName());
                                inspectPoint.setOrganizationId(Long.valueOf(checkPoint.getBankId()));
                                inspectPoint.setInspectItemId(checkProject.getId());

                                inspectPointList.add(inspectPoint);

                                InspectItem inspectItem = new InspectItem();
                                inspectItem.setId(checkProject.getId());
                                inspectItem.setName(checkProject.getcProjectName());

                                itemDao.insertOrReplace(inspectItem);

                                List<CheckProjectContent> checkContentList = checkPoint.getProjectContent();
                                if (checkContentList != null && checkContentList.size() > 0) {
                                    List<InspectContent> contentList = new ArrayList<>();
                                    for (int j = 0; j < checkContentList.size(); j++) {
                                        InspectContent content = new InspectContent();
                                        content.setId(checkContentList.get(j).getId());
                                        content.setName(checkContentList.get(j).getcProjectContent());
                                        content.setInspectItemId(checkProject.getId());
                                        content.setResultTypeId(Long.valueOf(checkContentList.get(j).getcProjectStatus()));
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
