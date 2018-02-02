package com.miaxis.inspection.view.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.PointManageAdapter;
import com.miaxis.inspection.entity.InspectPoint;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PointManageActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_point)
    RecyclerView rvPoint;
    @BindView(R.id.srl_point)
    SwipeRefreshLayout srlPoint;

    private List<InspectPoint> pointList;
    private PointManageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_manage);
        ButterKnife.bind(this);

        initData();
        initView();

    }

    private void initToolBar() {
        toolbar.setTitle("检查点管理");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_point_list_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_submit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {
        pointList = new ArrayList<>();
        InspectPoint point1 = new InspectPoint();
        point1.setPointName("测试检查点1");
        point1.setBound(true);
        pointList.add(point1);

        InspectPoint point2 = new InspectPoint();
        point2.setPointName("测试检查点2");
        pointList.add(point2);

        InspectPoint point3 = new InspectPoint();
        point3.setPointName("测试检查点3");
        pointList.add(point3);

        adapter = new PointManageAdapter(pointList, this);
    }

    @Override
    protected void initView() {
        initToolBar();
        rvPoint.setLayoutManager(new LinearLayoutManager(this));
        rvPoint.setAdapter(adapter);

        srlPoint.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlPoint.setRefreshing(false);
            }
        });
    }
}
