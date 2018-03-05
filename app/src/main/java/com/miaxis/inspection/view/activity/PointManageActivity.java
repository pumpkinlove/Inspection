package com.miaxis.inspection.view.activity;

import android.app.ProgressDialog;
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
import com.miaxis.inspection.presenter.IPointManagePresenter;
import com.miaxis.inspection.presenter.PointManagePresenterImpl;
import com.miaxis.inspection.view.IPointManageView;
import com.miaxis.inspection.view.custom.SimpleDialog;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PointManageActivity extends BaseActivity implements IPointManageView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_point)
    RecyclerView rvPoint;
    @BindView(R.id.srl_point)
    SwipeRefreshLayout srlPoint;

    private List<InspectPoint> pointList;
    private PointManageAdapter adapter;

    private ProgressDialog pdPointManage;

    private SimpleDialog unboundDialog;

    private IPointManagePresenter pointManagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_manage);
        ButterKnife.bind(this);

        initData();
        initView();

    }

    private void initToolBar() {
        pointManagePresenter = new PointManagePresenterImpl(this);
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
                        point.setBound(false);
                        point.setRfid(null);
                        point.setOpDate(new Date());
                        point.setOpUserCode(Inspection_App.getCurInspector().getCensorCode());
                        point.setOpUserName(Inspection_App.getCurInspector().getCensorName());
                        pointManagePresenter.updatePoint(point);
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
        pdPointManage = new ProgressDialog(this);
        pdPointManage.setCancelable(false);
        rvPoint.setLayoutManager(new LinearLayoutManager(this));
        rvPoint.setAdapter(adapter);

        srlPoint.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pointManagePresenter.loadInspectPoints();
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
                String result = bundle.getString(CodeUtils.RESULT_STRING);
                point = pointList.get(requestCode);        //这里requestCode 就是position
                point.setRfid(result);
                point.setBound(true);
                point.setOpDate(new Date());
                point.setOpUserCode(Inspection_App.getCurInspector().getCensorCode());
                point.setOpUserName(Inspection_App.getCurInspector().getCensorName());
                pointManagePresenter.updatePoint(point);
            } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                Toast.makeText(PointManageActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void stopRefresh() {
        srlPoint.setRefreshing(false);
    }

    @Override
    public void showInspectPoints(List<InspectPoint> inspectPointList) {
        adapter.setPointList(inspectPointList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressDialog() {
        pdPointManage.show();
    }

    @Override
    public void hideProgressDialog() {
        pdPointManage.dismiss();
    }

    @Override
    public void setProgressDialogMessage(String message) {
        pdPointManage.setMessage(message);
    }

    @Override
    public void setProgressDialogCancelable(boolean cancelable) {
        pdPointManage.setCancelable(cancelable);
    }

    @Override
    public void showUnboundDialog() {

    }

    @Override
    public void hideUnboundDialog() {

    }
}
