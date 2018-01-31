package com.miaxis.inspection.view.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.miaxis.inspection.R;
import com.miaxis.inspection.entity.InspectLog;
import com.miaxis.inspection.utils.DateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogDetailActivity extends BaseActivity {

    @BindView(R.id.tv_log_point)
    TextView tvLogPoint;
    @BindView(R.id.tv_log_time)
    TextView tvLogTime;
    @BindView(R.id.tv_log_result)
    TextView tvLogResult;
    private InspectLog mLog;

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
        mLog = (InspectLog) getIntent().getSerializableExtra("log");
    }

    @Override
    protected void initView() {
        tvLogPoint.setText(mLog.getInspectPoint().getPointName());
        tvLogTime.setText(DateUtil.toAll(mLog.getOpDate()));
        tvLogResult.setText(mLog.getResult());
    }
}
