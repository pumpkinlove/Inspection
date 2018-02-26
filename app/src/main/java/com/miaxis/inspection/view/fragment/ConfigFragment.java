package com.miaxis.inspection.view.fragment;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.inspection.R;
import com.miaxis.inspection.app.Inspection_App;
import com.miaxis.inspection.dao.gen.ConfigDao;
import com.miaxis.inspection.dao.gen.InspectorDao;
import com.miaxis.inspection.dao.gen.OrganizationDao;
import com.miaxis.inspection.entity.Config;
import com.miaxis.inspection.entity.Inspector;
import com.miaxis.inspection.entity.Organization;
import com.miaxis.inspection.entity.ResponseEntity;
import com.miaxis.inspection.net.DownInspectorNet;
import com.miaxis.inspection.net.DownOrganizationNet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigFragment extends Fragment {

    private static final String TAG = "ConfigFragment";

    @BindView(R.id.et_ip)
    EditText etIp;
    @BindView(R.id.et_port)
    EditText etPort;
    @BindView(R.id.et_orgCode)
    EditText etOrgCode;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    Unbinder unbinder;
    private ConfigDao configDao;
    private Config config;
    private ProgressDialog pdSaveConfig;
    private OnConfigClickListener mListener;

    public ConfigFragment() {
        // Required empty public constructor
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext(context);
    }

    /*
     * Deprecated on API 23
     * Use onAttachToContext instead
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
    }

    private void onAttachToContext(Context context) {
        if (context instanceof ConfigFragment.OnConfigClickListener) {
            mListener = (ConfigFragment.OnConfigClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnConfigClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config, container, false);
        unbinder = ButterKnife.bind(this, view);

        initData();
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadConfig();
    }

    void initView() {
        pdSaveConfig = new ProgressDialog(getActivity());
    }

    void initData() {
        configDao = Inspection_App.getInstance().getDaoSession().getConfigDao();
    }

    @OnClick(R.id.btn_confirm)
    void onSave(View view) {
        if (TextUtils.isEmpty(etIp.getText().toString())) {
            return;
        }
        if (TextUtils.isEmpty(etPort.getText().toString())) {
            return;
        }
        if (TextUtils.isEmpty(etOrgCode.getText().toString())) {
            return;
        }
        if (config == null) {
            config = new Config();
        }
        config.setIp(etIp.getText().toString());
        config.setPort(etPort.getText().toString());
        config.setOrgCode(etOrgCode.getText().toString());
        saveConfig(config);
        downloadOrganization(config);
        downloadInspector(config);
    }

    @OnClick(R.id.btn_cancel)
    void onCancel(View view) {
        mListener.onConfigCancel();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnConfigClickListener {
        void onConfigSave(Config config);
        void onConfigCancel();
    }

    /**
     * 从数据库读去设置，并显示
     */
    private void loadConfig() {
        Observable
                .create(new ObservableOnSubscribe<Config>() {
                    @Override
                    public void subscribe(ObservableEmitter<Config> e) throws Exception {
                        config = configDao.load(1L);
                        e.onNext(config);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Config>() {
                    @Override
                    public void accept(Config config) throws Exception {
                        etIp.setText(config.getIp());
                        etPort.setText(config.getPort());
                        etOrgCode.setText(config.getOrgCode());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });



    }

    /**
     * 保存设置
     * @param config
     */
    private void saveConfig(Config config) {
        Observable
                .just(config)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Config>() {
                    @Override
                    public void accept(Config config) throws Exception {
                        pdSaveConfig.setMessage("正在保存设置...");
                        pdSaveConfig.show();
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Config>() {
                    @Override
                    public void accept(Config config) throws Exception {
                        configDao.save(config);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Config>() {
                    @Override
                    public void accept(Config config) throws Exception {
                        pdSaveConfig.setMessage("保存成功");
                        pdSaveConfig.dismiss();
                        mListener.onConfigSave(config);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        pdSaveConfig.setMessage("保存失败");
                        pdSaveConfig.setCancelable(true);
                    }
                });
    }

    /**
     * 下载机构信息
     * @param config
     */
    private void downloadOrganization(Config config) {
        pdSaveConfig.setMessage("正在下载机构信息...");
        pdSaveConfig.show();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();

        DownOrganizationNet net = retrofit.create(DownOrganizationNet.class);

        net.downloadOrgnization(config.getOrgCode())
                .subscribeOn(Schedulers.newThread())
                .doOnNext(new Consumer<ResponseEntity<Organization>>() {
                    @Override
                    public void accept(ResponseEntity<Organization> responseEntity) throws Exception {
                        OrganizationDao dao = Inspection_App.getInstance().getDaoSession().getOrganizationDao();
                        Organization o = responseEntity.getData();
                        dao.insertOrReplace(o);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity<Organization>>() {
                    @Override
                    public void accept(ResponseEntity<Organization> responseEntity) throws Exception {
                        if (responseEntity.getCode().equals("200")) {
                            pdSaveConfig.setMessage("机构信息下载完成！");
                            pdSaveConfig.dismiss();
                        } else {
                            pdSaveConfig.setMessage(responseEntity.getMessage());
                            pdSaveConfig.setCancelable(true);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        pdSaveConfig.setMessage("机构信息下载失败！");
                        pdSaveConfig.setCancelable(true);
                    }
                });

    }

    /**
     * 下载检查员
     * @param config
     */
    private void downloadInspector(Config config) {
        pdSaveConfig.setMessage("正在下载检查员信息...");
        pdSaveConfig.show();
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
                            pdSaveConfig.setMessage("检查员信息下载完成！");
                            pdSaveConfig.dismiss();
                        } else {
                            pdSaveConfig.setMessage(responseEntity.getMessage());
                            pdSaveConfig.setCancelable(true);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "ee" + throwable.getMessage());
                        pdSaveConfig.setMessage("检查员信息下载失败！");
                        pdSaveConfig.setCancelable(true);
                    }
                });
    }


}
