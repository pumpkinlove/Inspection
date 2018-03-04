package com.miaxis.inspection.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.SimpleContentAdapter;
import com.miaxis.inspection.app.Inspection_App;
import com.miaxis.inspection.entity.InspectContent;
import com.miaxis.inspection.entity.InspectContentLog;
import com.miaxis.inspection.entity.InspectForm;
import com.miaxis.inspection.entity.InspectItem;
import com.miaxis.inspection.entity.InspectPointLog;
import com.miaxis.inspection.entity.InspectPoint;
import com.miaxis.inspection.entity.Task;
import com.miaxis.inspection.model.local.greenDao.gen.DaoSession;
import com.miaxis.inspection.model.local.greenDao.gen.InspectFormDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectPointLogDao;
import com.miaxis.inspection.model.local.greenDao.gen.TaskDao;
import com.miaxis.inspection.utils.DateUtil;
import com.miaxis.inspection.view.custom.SimpleDialog;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DoInspectItemActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.tv_point_name)
    TextView tvPointName;
    @BindView(R.id.tv_item_name)
    TextView tvItemName;

    private InspectPoint inspectPoint;
    private InspectItem inspectItem;
    private SimpleContentAdapter adapter;
    private InspectPointLog pointLog;
    private List<InspectContentLog> contentLogList;
    private List<InspectContent> contentList;
    private Task task;

    private InspectPointLogDao pointLogDao;
    private DaoSession mDaoSession;

    private String pointLogCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_inspect);
        ButterKnife.bind(this);
        initData();
        initView();

    }

    @Override
    protected void initData() {

        mDaoSession = Inspection_App.getInstance().getDaoSession();
        pointLogDao = mDaoSession.getInspectPointLogDao();

        inspectPoint = (InspectPoint) getIntent().getSerializableExtra("point");
        inspectPoint.__setDaoSession(mDaoSession);

        inspectItem = inspectPoint.getInspectItem();

        InspectFormDao formDao = mDaoSession.getInspectFormDao();
        InspectForm inspectForm = formDao.queryBuilder().where(InspectFormDao.Properties.Code.eq(inspectItem.getInspectFormCode())).unique();
        TaskDao taskDao = mDaoSession.getTaskDao();
        task = taskDao.queryBuilder().where(TaskDao.Properties.InspectFormId.eq(inspectForm.getId())).unique();

        contentList = inspectItem.getInspectContentList();
        contentLogList = pointLog.getContentList();

        pointLogCode = inspectPoint.getCode() + "_" + new Date().getTime();

        pointLog = new InspectPointLog();
        pointLog.setInspectItemId(inspectItem.getId());
        pointLog.setInspectItem(inspectItem);
        pointLog.setPointLogCode(pointLogCode);
        pointLog.setInspectPointId(inspectPoint.getId());
        pointLog.setInspectPoint(inspectPoint);

        adapter = new SimpleContentAdapter(contentList, contentLogList, this);
        adapter.setListener(new SimpleContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(DoInspectItemActivity.this, DoInspectContentActivity.class);
                i.putExtra("pointLogCode", pointLogCode);
                i.putExtra("content", contentList.get(position));
                startActivity(i);
            }
        });
    }

    @Override
    protected void initView() {
        initToolBar();
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(adapter);

        tvItemName.setText(inspectItem.getName());
        tvPointName.setText(inspectPoint.getPointName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDaoSession.clear();
        pointLog = pointLogDao.queryBuilder().where(InspectPointLogDao.Properties.InspectItemId.eq(inspectItem.getId())).unique();
        contentList = inspectItem.getInspectContentList();
        contentLogList = pointLog.getContentList();
        adapter.setContentList(contentList);
        adapter.setContentLogList(contentLogList);
        adapter.notifyDataSetChanged();
    }

    private void initToolBar() {
        toolbar.setTitle("开始检查");
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

    @OnClick(R.id.btn_submit)
    void submit() {
        final SimpleDialog sd = new SimpleDialog();
        sd.setMessage("是否提交任务？");
        sd.setConfirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pointLog.setInspected(true);
                pointLog.setOpDate(new Date());
                pointLog.setOpInspectorCode(Inspection_App.getCurInspector().getCensorCode());
                pointLog.setOpInspectorName(Inspection_App.getCurInspector().getCensorName());
                contentLogList = pointLog.getContentList();
                pointLog.setResult("正常");
                for (int i = 0; i < contentLogList.size(); i++) {
                    if (contentLogList.get(i).getHasProblem()) {
                        pointLog.setResult("异常");
                    }
                }
                Inspection_App.getInstance().getDaoSession().getInspectPointLogDao().save(pointLog);
                sd.dismiss();
                finish();
            }
        });
        sd.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sd.dismiss();
            }
        });
        sd.show(getFragmentManager(), "submit");

    }

}
