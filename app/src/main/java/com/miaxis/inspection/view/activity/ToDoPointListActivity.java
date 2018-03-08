package com.miaxis.inspection.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.SimplePointAdapter;
import com.miaxis.inspection.entity.InspectPoint;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToDoPointListActivity extends BaseActivity {

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

        int toDoPointCount = getIntent().getIntExtra("toDoPointCount", 0);
        for (int i = 0; i < toDoPointCount; i ++) {
            InspectPoint point = (InspectPoint) getIntent().getSerializableExtra("toDoPoint" + i);
            pointList.add(point);
        }
        adapter = new SimplePointAdapter(pointList, this);
        adapter.setListener(new SimplePointAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(ToDoPointListActivity.this, CaptureActivity.class);
                startActivityForResult(i, position);
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
        toolbar.setTitle("待检查点");
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != data) {
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                String rfid = bundle.getString(CodeUtils.RESULT_STRING);
                if (!pointList.get(requestCode).getRfid().equals(rfid)) {
                    Toast.makeText(ToDoPointListActivity.this, "错误的二维码", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(ToDoPointListActivity.this, DoInspectItemActivity.class);
                    i.putExtra("point", pointList.get(requestCode));
                    startActivity(i);
                }
            } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                Toast.makeText(ToDoPointListActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
            }
        }
    }

}
