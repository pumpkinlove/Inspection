package com.miaxis.inspection.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.miaxis.inspection.R;
import com.miaxis.inspection.view.fragment.ConfigFragment;
import com.miaxis.inspection.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

//        ConfigFragment configFragment = new ConfigFragment();
//        getFragmentManager().beginTransaction().replace(R.id.fl_config, configFragment).commit();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tvVersion.setText(R.string.version);
        tvVersion.append(CommonUtil.getVerName(this));
    }

    @OnClick(R.id.btn_login)
    void onLoginClicked() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onConfigSave() {

    }

    @Override
    public void onConfigCancel() {

    }
}
