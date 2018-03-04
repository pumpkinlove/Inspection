package com.miaxis.inspection.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.InspectorSpinnerAdapter;
import com.miaxis.inspection.entity.Config;
import com.miaxis.inspection.entity.Inspector;
import com.miaxis.inspection.presenter.ILoginPresenter;
import com.miaxis.inspection.presenter.LoginPresenterImpl;
import com.miaxis.inspection.utils.CommonUtil;
import com.miaxis.inspection.view.ILoginView;
import com.miaxis.inspection.view.fragment.ConfigFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ConfigFragment.OnConfigClickListener, ILoginView {

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
    @BindView(R.id.iv_config)
    ImageView ivConfig;
    @BindView(R.id.cv_login)
    CardView cvLogin;

    private ProgressDialog pdLogin;
    private InspectorSpinnerAdapter spinnerAdapter;
    private List<Inspector> inspectorList;

    private ILoginPresenter loginPresenter;
    private Inspector inspector;

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
        loginPresenter = new LoginPresenterImpl(this);
        spinnerAdapter = new InspectorSpinnerAdapter(inspectorList, this);
    }

    @Override
    protected void initView() {
        pdLogin = new ProgressDialog(this);
        tvVersion.setText(R.string.version);
        tvVersion.append(CommonUtil.getVerName(this));
        spInspectorName.setAdapter(spinnerAdapter);
        spInspectorName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                inspector = inspectorList.get(i);
                onClearPassword();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void checkConfig() {
        loginPresenter.checkConfig();
    }

    @OnClick(R.id.btn_login)
    void onLoginClicked() {
        String password = etPassword.getText().toString();
        if (!TextUtils.isEmpty(password)) {
            loginPresenter.doLogin(inspector, password);
        }
    }

    @OnClick(R.id.iv_config)
    void onConfigClicked() {
        if (flConfig.getVisibility() == View.VISIBLE) {
            onHideConfig();
        } else {
            onShowConfig();
        }
    }


    @Override
    public void onConfigSave(Config config) {
        onHideConfig();
        loginPresenter.syncInspector(config);
    }

    @Override
    public void onConfigCancel() {

    }

    @Override
    public void onClearPassword() {
        etPassword.setText(null);
    }

    @Override
    public void onLoginSucceed() {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("LoginInspector", inspector);
        startActivity(i);
    }

    @Override
    public void onLoginFailed() {
        // TODO: 2018/2/28  登录失败提示
    }

    @Override
    public void onShowConfig() {
        flConfig.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.GONE);
        cvLogin.setVisibility(View.GONE);
        ConfigFragment configFragment = new ConfigFragment();
        getFragmentManager().beginTransaction().replace(R.id.fl_config, configFragment).commit();
    }

    @Override
    public void onHideConfig() {
        flConfig.setVisibility(View.GONE);
        btnLogin.setVisibility(View.VISIBLE);
        cvLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShowInspectorList(List<Inspector> inspectorList) {
        this.inspectorList = inspectorList;
        spinnerAdapter.setInspectorList(inspectorList);
        spinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressMessage(String message) {
        pdLogin.setMessage(message);
    }

    @Override
    public void showProgressDialog() {
        pdLogin.show();
    }

    @Override
    public void hideProgressDialog() {
        pdLogin.dismiss();
    }

    @Override
    public void setProgressDialogCancelable(boolean cancelable) {
        pdLogin.setCancelable(cancelable);
    }
}
