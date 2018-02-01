package com.miaxis.inspection.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.LogAdapter;
import com.miaxis.inspection.entity.InspectLog;
import com.miaxis.inspection.entity.InspectPoint;
import com.miaxis.inspection.view.activity.LogDetailActivity;
import com.miaxis.inspection.view.activity.PointListActivity;
import com.miaxis.inspection.view.activity.ScanPointActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import q.rorbin.badgeview.QBadgeView;

public class HomePageFragment extends Fragment {

    @BindView(R.id.rv_log)
    RecyclerView rvLog;
    Unbinder unbinder;
    @BindView(R.id.cv_scan)
    CardView cvScan;
    @BindView(R.id.cv_point_to_do)
    CardView cvPointToDo;
    @BindView(R.id.tv_log_time)
    TextView tvLogTime;
    @BindView(R.id.tv_log_result)
    TextView tvLogResult;
    @BindView(R.id.tv_to_do_point)
    TextView tvToDoPoint;

    private LogAdapter logAdapter;
    private List<InspectLog> logList;

    public HomePageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initData() {
        logList = new ArrayList<>();
        InspectLog log = new InspectLog();
        InspectPoint point = new InspectPoint();
        point.setPointName("检查点1");
        log.setInspectPoint(point);
        log.setOpDate(new Date());
        log.setResult("正常");
        logList.add(log);
        logAdapter = new LogAdapter(logList, getContext());
        logAdapter.setListener(new LogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), LogDetailActivity.class);
                intent.putExtra("log", logList.get(position));
                startActivity(intent);
            }
        });
    }

    private void initView() {
        rvLog.setAdapter(logAdapter);
        rvLog.setLayoutManager(new LinearLayoutManager(getContext()));
        QBadgeView badgeView = new QBadgeView(getContext());
        badgeView.setBadgeGravity(Gravity.CENTER | Gravity.END);
        badgeView.bindTarget(tvToDoPoint).setBadgeNumber(3);
        badgeView.setBadgeTextSize(16, true);

    }

    @OnClick(R.id.cv_scan)
    void onDoInspect() {
        startActivity(new Intent(getContext(), ScanPointActivity.class));
    }

    @OnClick(R.id.cv_point_to_do)
    void onToDoInspectPoint() {
        startActivity(new Intent(getContext(), PointListActivity.class));
    }

}
