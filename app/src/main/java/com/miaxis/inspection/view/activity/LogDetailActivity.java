package com.miaxis.inspection.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.LogContentDetailAdapter;
import com.miaxis.inspection.app.Inspection_App;
import com.miaxis.inspection.entity.InspectContentLog;
import com.miaxis.inspection.entity.InspectPointLog;
import com.miaxis.inspection.utils.DateUtil;
import com.miaxis.inspection.view.custom.ProblemDetailDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogDetailActivity extends BaseActivity {

    @BindView(R.id.tv_log_point)
    TextView tvLogPoint;
    @BindView(R.id.tv_log_time)
    TextView tvLogTime;
    @BindView(R.id.tv_log_result)
    TextView tvLogResult;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_log_inspector)
    TextView tvLogInspector;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    private InspectPointLog mLog;
    private LogContentDetailAdapter contentDetailAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_detail);
        ButterKnife.bind(this);

        initData();
        initView();

    }

    @Override
    protected void initData() {
        mLog = (InspectPointLog) getIntent().getSerializableExtra("log");
        mLog.__setDaoSession(Inspection_App.getInstance().getDaoSession());
        contentDetailAdapter = new LogContentDetailAdapter(mLog.getContentList(), this);
        contentDetailAdapter.setListener(new LogContentDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                InspectContentLog contentLog = mLog.getContentList().get(position);
                if (contentLog.getHasProblem()) {
                    ProblemDetailDialog problemDetailDialog = new ProblemDetailDialog();
                    problemDetailDialog.setProblemDescription(contentLog.getDescription());
                    problemDetailDialog.setPhotoList(contentLog.getProblemPhotoList());
                    problemDetailDialog.show(getFragmentManager(), "problemDetail");
                }
            }
        });
    }

    @Override
    protected void initView() {
        initToolBar();
        tvLogPoint.setText(mLog.getInspectPoint().getPointName());
        tvLogTime.setText(DateUtil.toAll(mLog.getOpDate()));
        tvLogResult.setText(mLog.getResult());
        tvLogInspector.setText(mLog.getOpInspectorName());

        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(contentDetailAdapter);
    }

    private void initToolBar() {
        toolbar.setTitle("记录详情");
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
