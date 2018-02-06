package com.miaxis.inspection.view.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.PointManageAdapter;
import com.miaxis.inspection.app.Inspection_App;
import com.miaxis.inspection.entity.InspectPoint;
import com.miaxis.inspection.view.custom.SimpleDialog;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

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
            case R.id.action_add:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {
        pointList = Inspection_App.getInstance().getDaoSession().getInspectPointDao().loadAll();
        adapter = new PointManageAdapter(pointList, this);
        adapter.setListener(new PointManageAdapter.OnItemClickListener() {
            @Override
            public void onAddRfid(View view, int position) {
                startActivityForResult(new Intent(PointManageActivity.this, CaptureActivity.class), position);
            }

            @Override
            public void onModRfid(View view, int position) {
                startActivityForResult(new Intent(PointManageActivity.this, CaptureActivity.class), position);
            }

            @Override
            public void onDelRfid(View view, final int position) {
                final SimpleDialog sd = new SimpleDialog();
                sd.setMessage("您确定要解除绑定吗？");
                sd.setConfirmListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        InspectPoint point = pointList.get(position);
                        Inspection_App.getInstance().getDaoSession().getInspectPointDao().update(point);
                        point.setBound(false);
                        point.setRfid(null);
                        adapter.notifyDataSetChanged();
                        sd.dismiss();
                    }
                });

                sd.setCancelListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sd.dismiss();
                    }
                });
                sd.show(getFragmentManager(), "unbind");
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != data) {
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                InspectPoint point;
                try {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    point = pointList.get(requestCode);        //这里requestCode 就是position
                    point.setRfid(result);
                    point.setBound(true);
                    Inspection_App.getInstance().getDaoSession().getInspectPointDao().save(point);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(PointManageActivity.this, "绑定成功", Toast.LENGTH_LONG).show();
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(PointManageActivity.this, "绑定失败，编码重复", Toast.LENGTH_LONG).show();
                    // TODO: 2018/2/6 异常 数据恢复
                } catch (Exception ex) {
                    Toast.makeText(PointManageActivity.this, "绑定失败", Toast.LENGTH_LONG).show();
                }

            } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                Toast.makeText(PointManageActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
            }
        }
    }


}
