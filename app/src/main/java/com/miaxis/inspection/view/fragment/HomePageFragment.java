package com.miaxis.inspection.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.LogAdapter;
import com.miaxis.inspection.app.Inspection_App;
import com.miaxis.inspection.entity.InspectItem;
import com.miaxis.inspection.entity.InspectPointLog;
import com.miaxis.inspection.entity.InspectPoint;
import com.miaxis.inspection.entity.Task;
import com.miaxis.inspection.entity.comm.CheckProjectTime;
import com.miaxis.inspection.entity.comm.TaskTime;
import com.miaxis.inspection.model.local.greenDao.gen.InspectPointDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectPointLogDao;
import com.miaxis.inspection.model.local.greenDao.gen.TaskDao;
import com.miaxis.inspection.utils.DateUtil;
import com.miaxis.inspection.utils.RemindFrequencyType;
import com.miaxis.inspection.view.activity.DoInspectItemActivity;
import com.miaxis.inspection.view.activity.LogDetailActivity;
import com.miaxis.inspection.view.activity.LogListActivity;
import com.miaxis.inspection.view.activity.PointListActivity;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
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

    private static final int REQ_CODE_DO_INSPECT = 2;

    private QBadgeView badgeView;
    private LogAdapter logAdapter;
    private List<InspectPointLog> logList;
    private List<InspectPoint> toDoPointList;

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
        logList = Inspection_App.getInstance().getDaoSession().getInspectPointLogDao().queryBuilder().where(InspectPointLogDao.Properties.Inspected.eq(true)).list();
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
        badgeView = new QBadgeView(getContext());
        rvLog.setAdapter(logAdapter);
        rvLog.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void initBadge() {
        countToDoPoint();
        if (toDoPointList.size() == 0) {
            badgeView.setVisibility(View.GONE);
        } else {
            badgeView.setVisibility(View.VISIBLE);
            badgeView.setBadgeGravity(Gravity.CENTER | Gravity.END);
            badgeView.bindTarget(tvToDoPoint).setBadgeNumber(toDoPointList.size());
            badgeView.setBadgeTextSize(16, true);
        }
    }

    @OnClick(R.id.cv_scan)
    void onDoInspect() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},1);
            return;
        }
        startActivityForResult(new Intent(getContext(), CaptureActivity.class), REQ_CODE_DO_INSPECT);
    }

    @OnClick(R.id.cv_point_to_do)
    void onToDoInspectPoint() {
        Intent intent = new Intent(getContext(), PointListActivity.class);
        intent.putExtra("toDoPointCount", toDoPointList.size());
        for (int i = 0; i < toDoPointList.size(); i ++) {
            intent.putExtra("toDoPoint" + i, toDoPointList.get(i));
        }
        startActivity(intent);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQ_CODE_DO_INSPECT) {
            return;
        }
        if (null != data) {
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                String rfid = bundle.getString(CodeUtils.RESULT_STRING);
                scanPointResult(rfid);
            } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                Toast.makeText(getContext(), "解析二维码失败", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void scanPointResult(String rfid) {
        Observable
                .just(rfid)
                .subscribeOn(Schedulers.io())
                .map(new Function<String, InspectPoint>() {
                    @Override
                    public InspectPoint apply(String rfid) throws Exception {
                        InspectPointDao pointDao = Inspection_App.getInstance().getDaoSession().getInspectPointDao();
                        return pointDao.queryBuilder().where(InspectPointDao.Properties.Rfid.eq(rfid)).unique();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<InspectPoint>() {
                    @Override
                    public void accept(InspectPoint inspectPoint) throws Exception {
                        Intent i = new Intent(getContext(), DoInspectItemActivity.class);
                        i.putExtra("point", inspectPoint);
                        startActivity(i);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getContext(), "无效的编码", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onResume() {
        super.onResume();
        logList = Inspection_App.getInstance().getDaoSession().getInspectPointLogDao().loadAll();
        logAdapter.setLogList(logList);
        logAdapter.notifyDataSetChanged();

        initBadge();

    }

    @OnClick(R.id.tv_more_log)
    void moreLog() {
        startActivity(new Intent(getActivity(), LogListActivity.class));
    }

    private void countToDoPoint() {
        toDoPointList = new ArrayList<>();
        InspectPointDao pointDao = Inspection_App.getInstance().getDaoSession().getInspectPointDao();
        List<InspectPoint> pointList = pointDao.loadAll();
        for (int i = 0; i < pointList.size(); i ++) {
            InspectPoint point = pointList.get(i);
            InspectItem inspectItem = point.getInspectItem();
            List<CheckProjectTime> checkProjectTimes = inspectItem.getCheckProjectTime();
            switch (inspectItem.getFrequencyType()) {
                case RemindFrequencyType.PER_DAY:
                    if (checkProjectTimes != null && checkProjectTimes.size() > 0) {
                        for (int j = 0; j < checkProjectTimes.size(); j ++) {
                            CheckProjectTime cpt = checkProjectTimes.get(j);
                            String st = DateUtil.getTimeOfDay(cpt.getCProjectStartTime());
                            Date std = DateUtil.strToDate(st, "yyyy-MM-dd HH:mm:ss");

                            String et = DateUtil.getTimeOfDay(cpt.getCProjectEndTime());
                            Date etd = DateUtil.strToDate(et, "yyyy-MM-dd HH:mm:ss");

                            Long c = System.currentTimeMillis();

                            if (c <= etd.getTime() && c >= std.getTime()) {
                                InspectPointLogDao pointLogDao = Inspection_App.getInstance().getDaoSession().getInspectPointLogDao();
                                List<InspectPointLog> pointLogs = pointLogDao.queryBuilder()
                                        .where(InspectPointLogDao.Properties.InspectPointId.eq(point.getId()))
                                        .where(InspectPointLogDao.Properties.OpDate.between(std, etd))
                                        .list();
                                if (pointLogs == null || pointLogs.size() == 0) {
                                    toDoPointList.add(point);
                                    break;
                                }
                            }
                        }
                    }

                    break;
                case RemindFrequencyType.PER_WEEK:
                    if (checkProjectTimes != null && checkProjectTimes.size() > 0) {
                        for (int j = 0; j < checkProjectTimes.size(); j ++) {
                            CheckProjectTime cpt = checkProjectTimes.get(j);
                            Calendar c = Calendar.getInstance();

                            Date bd = DateUtil.getDayOfThisWeek0(1);
                            Date ed = DateUtil.getDayOfThisWeek0(7);

                            Long cur = System.currentTimeMillis();

                            if (cur <= ed.getTime() && cur >= bd.getTime()) {
                                InspectPointLogDao pointLogDao = Inspection_App.getInstance().getDaoSession().getInspectPointLogDao();
                                List<InspectPointLog> pointLogs = pointLogDao.queryBuilder()
                                        .where(InspectPointLogDao.Properties.InspectPointId.eq(point.getId()))
                                        .where(InspectPointLogDao.Properties.OpDate.between(bd, ed))
                                        .list();
                                if (pointLogs == null || pointLogs.size() == 0) {
                                    toDoPointList.add(point);
                                    break;
                                }
                            }
                        }
                    }

                    break;
                case RemindFrequencyType.PER_MONTH:
                    if (checkProjectTimes != null && checkProjectTimes.size() > 0) {
                        for (int j = 0; j < checkProjectTimes.size(); j ++) {

                            Date bd = DateUtil.getFirstDayOfThisMonth();
                            Date ed = DateUtil.getLastDayOfThisMonth();

                            Long cur = System.currentTimeMillis();

                            if (cur <= ed.getTime() && cur >= bd.getTime()) {
                                InspectPointLogDao pointLogDao = Inspection_App.getInstance().getDaoSession().getInspectPointLogDao();
                                List<InspectPointLog> pointLogs = pointLogDao.queryBuilder()
                                        .where(InspectPointLogDao.Properties.InspectPointId.eq(point.getId()))
                                        .where(InspectPointLogDao.Properties.OpDate.between(bd, ed))
                                        .list();
                                if (pointLogs == null || pointLogs.size() == 0) {
                                    toDoPointList.add(point);
                                    break;
                                }
                            }
                        }
                    }

                    break;
                case RemindFrequencyType.PER_SEASON:
                    if (checkProjectTimes != null && checkProjectTimes.size() > 0) {
                        for (int j = 0; j < checkProjectTimes.size(); j ++) {

                            Date bd = DateUtil.getDayOfThisWeek0(1);
                            Date ed = DateUtil.getDayOfThisWeek0(7);

                            Long cur = System.currentTimeMillis();

                            if (cur <= ed.getTime() && cur >= bd.getTime()) {
                                InspectPointLogDao pointLogDao = Inspection_App.getInstance().getDaoSession().getInspectPointLogDao();
                                List<InspectPointLog> pointLogs = pointLogDao.queryBuilder()
                                        .where(InspectPointLogDao.Properties.InspectPointId.eq(point.getId()))
                                        .where(InspectPointLogDao.Properties.OpDate.between(bd, ed))
                                        .list();
                                if (pointLogs == null || pointLogs.size() == 0) {
                                    toDoPointList.add(point);
                                    break;
                                }
                            }
                        }
                    }

                    break;
                case RemindFrequencyType.PER_YEAR:
                    if (checkProjectTimes != null && checkProjectTimes.size() > 0) {
                        for (int j = 0; j < checkProjectTimes.size(); j ++) {

                            Date bd = DateUtil.getFirstDayOfThisYear();
                            Date ed = DateUtil.getLastDayOfThisYear();

                            Long cur = System.currentTimeMillis();

                            if (cur <= ed.getTime() && cur >= bd.getTime()) {
                                InspectPointLogDao pointLogDao = Inspection_App.getInstance().getDaoSession().getInspectPointLogDao();
                                List<InspectPointLog> pointLogs = pointLogDao.queryBuilder()
                                        .where(InspectPointLogDao.Properties.InspectPointId.eq(point.getId()))
                                        .where(InspectPointLogDao.Properties.OpDate.between(bd, ed))
                                        .list();
                                if (pointLogs == null || pointLogs.size() == 0) {
                                    toDoPointList.add(point);
                                    break;
                                }
                            }
                        }
                    }

                    break;
            }
        }

    }


}
