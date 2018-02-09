package com.miaxis.inspection.view.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.miaxis.inspection.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InspectorListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_inspector)
    RecyclerView rvInspector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspector_list);
        ButterKnife.bind(this);

        initData();
        initView();
        initToolBar();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    private void initToolBar() {
        toolbar.setTitle("检查员管理");
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
}
