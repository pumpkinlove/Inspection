package com.miaxis.inspection.view.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.InspectorAdapter;
import com.miaxis.inspection.app.Inspection_App;
import com.miaxis.inspection.entity.Inspector;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InspectorListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_inspector)
    RecyclerView rvInspector;
    @BindView(R.id.srl_inspector)
    SwipeRefreshLayout srlInspector;

    private List<Inspector> inspectorList;
    private InspectorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspector_list);
        ButterKnife.bind(this);

        initData();
        initView();
        initToolBar();
        srlInspector.post(new Runnable() {
            @Override
            public void run() {
                srlInspector.setRefreshing(true);
                onRefresh();
            }
        });


    }

    @Override
    protected void initData() {
        adapter = new InspectorAdapter(inspectorList, this);
        rvInspector.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initView() {
        rvInspector.setAdapter(adapter);
        srlInspector.setOnRefreshListener(this);
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


    @Override
    public void onRefresh() {
        inspectorList = Inspection_App.getInstance().getDaoSession().getInspectorDao().loadAll();
        adapter.setInspectorList(inspectorList);
        adapter.notifyDataSetChanged();
        srlInspector.setRefreshing(false);
    }



}
