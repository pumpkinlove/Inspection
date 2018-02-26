package com.miaxis.inspection.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.miaxis.inspection.R;
import com.miaxis.inspection.entity.Config;
import com.miaxis.inspection.view.fragment.ConfigFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfigActivity extends BaseActivity implements ConfigFragment.OnConfigClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        ButterKnife.bind(this);

        initToolBar();
    }

    private void initToolBar() {
        toolbar.setTitle(R.string.setting);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigSave(Config config) {
        finish();
    }

    @Override
    public void onConfigCancel() {
        finish();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }
}
