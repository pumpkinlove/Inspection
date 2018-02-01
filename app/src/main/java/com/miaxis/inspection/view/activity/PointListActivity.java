package com.miaxis.inspection.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.SimpleContentAdapter;
import com.miaxis.inspection.adapter.SimplePointAdapter;
import com.miaxis.inspection.entity.InspectContent;
import com.miaxis.inspection.entity.InspectPoint;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PointListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_point)
    RecyclerView rvPoint;

    private List<InspectPoint> pointList;
    private SimplePointAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_list);
        ButterKnife.bind(this);
        initData();
        initView();

    }

    @Override
    protected void initData() {
        pointList = new ArrayList<>();
        InspectPoint point1 = new InspectPoint();
        point1.setPointName("测试检查点1");
        pointList.add(point1);

        InspectPoint point2 = new InspectPoint();
        point2.setPointName("测试检查点2");
        pointList.add(point2);

        InspectPoint point3 = new InspectPoint();
        point3.setPointName("测试检查点3");
        pointList.add(point3);


        adapter = new SimplePointAdapter(pointList, this);
        adapter.setListener(new SimplePointAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(PointListActivity.this, ScanPointActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void initView() {
        initToolBar();
        rvPoint.setLayoutManager(new LinearLayoutManager(this));
        rvPoint.setAdapter(adapter);
    }

    private void initToolBar() {
        toolbar.setTitle("检查点");
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
