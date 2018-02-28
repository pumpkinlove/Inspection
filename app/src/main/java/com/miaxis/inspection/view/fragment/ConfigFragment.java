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
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.inspection.R;
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
import com.miaxis.inspection.presenter.ConfigPresenterImpl;
import com.miaxis.inspection.presenter.IConfigPresenter;
import com.miaxis.inspection.view.IConfigView;

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

public class ConfigFragment extends Fragment implements IConfigView {

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
    private ProgressDialog pdSaveConfig;
    private OnConfigClickListener mListener;

    private IConfigPresenter configPresenter;

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
        configPresenter.loadConfig();
    }

    private void initView() {
        pdSaveConfig = new ProgressDialog(getActivity());
        pdSaveConfig.setCancelable(false);
    }

    private void initData() {
        configPresenter = new ConfigPresenterImpl(this);
    }

    @OnClick(R.id.btn_confirm)
    void onSave(View view) {
        String ip = etIp.getText().toString();
        String port = etPort.getText().toString();
        String orgCode = etOrgCode.getText().toString();

        if (TextUtils.isEmpty(ip)) {
            Toast.makeText(getActivity(), "Ip地址 不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(port)) {
            Toast.makeText(getActivity(), "端口号 不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(orgCode)) {
            Toast.makeText(getActivity(), "机构编号 不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        configPresenter.saveConfig(ip, port, orgCode, mListener);
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

    @Override
    public void showProgressDialog() {
        pdSaveConfig.show();
    }

    @Override
    public void hideProgressDialog() {
        pdSaveConfig.dismiss();
    }

    @Override
    public void setProgressDialogMessage(String message) {
        pdSaveConfig.setMessage(message);
    }

    @Override
    public void setProgressDialogCancelable(boolean cancelable) {
        pdSaveConfig.setCancelable(cancelable);
    }

    @Override
    public void fetchConfig(Config config) {
        etIp.setText(config.getIp());
        etPort.setText(config.getPort());
        etOrgCode.setText(config.getOrgCode());
    }

}
