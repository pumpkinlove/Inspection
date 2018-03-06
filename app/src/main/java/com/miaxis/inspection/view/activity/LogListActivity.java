package com.miaxis.inspection.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.LogAdapter;
import com.miaxis.inspection.app.Inspection_App;
import com.miaxis.inspection.entity.Config;
import com.miaxis.inspection.entity.InspectItem;
import com.miaxis.inspection.entity.InspectPoint;
import com.miaxis.inspection.entity.InspectPointLog;
import com.miaxis.inspection.entity.ResponseEntity;
import com.miaxis.inspection.entity.comm.CheckPointLog;
import com.miaxis.inspection.model.local.greenDao.gen.ConfigDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectItemDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectPointDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectPointLogDao;
import com.miaxis.inspection.model.remote.retrofit.LogNet;
import com.miaxis.inspection.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_log)
    RecyclerView rvLog;
    @BindView(R.id.srl_log)
    SwipeRefreshLayout srlLog;

    private List<InspectPointLog> logList;
    private LogAdapter logAdapter;
    private InspectPointLogDao pointLogDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_list);
        ButterKnife.bind(this);

        initData();
        initView();
        initToolBar();
    }

    private void initToolBar() {
        toolbar.setTitle("检查日志");
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
    protected void initData() {
        pointLogDao = Inspection_App.getInstance().getDaoSession().getInspectPointLogDao();
        logList = new ArrayList<>();
        logAdapter = new LogAdapter(logList, this);
        logAdapter.setListener(new LogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(LogListActivity.this, LogDetailActivity.class);
                intent.putExtra("log", logList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView() {
        rvLog.setAdapter(logAdapter);
        rvLog.setLayoutManager(new LinearLayoutManager(this));

        srlLog.setOnRefreshListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                srlLog.setRefreshing(true);
                refresh();
            }
        }, 100);
    }

    private void refresh() {
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        List<InspectPointLog> logList = pointLogDao.queryBuilder().orderDesc(InspectPointLogDao.Properties.OpDate).list();
                        if (logList == null || logList.size() == 0) {
                            e.onNext("");
                        } else {
                            e.onNext(DateUtil.dateToStr(logList.get(0).getOpDate()));
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<ResponseEntity<CheckPointLog>>>() {
                    @Override
                    public ObservableSource<ResponseEntity<CheckPointLog>> apply(String date) throws Exception {
                        ConfigDao configDao = Inspection_App.getInstance().getDaoSession().getConfigDao();
                        Config config = configDao.load(1L);
                        Retrofit retrofit = new Retrofit.Builder()
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                                .build();

                        LogNet net = retrofit.create(LogNet.class);

                        return net.downPointLog(config.getOrgCode(), date);
                    }
                })
                .doOnNext(new Consumer<ResponseEntity<CheckPointLog>>() {
                    @Override
                    public void accept(ResponseEntity<CheckPointLog> responseEntity) throws Exception {
                        if (TextUtils.equals("200", responseEntity.getCode())) {
                            saveCheckPointLogs(responseEntity.getListData());
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity<CheckPointLog>>() {
                    @Override
                    public void accept(ResponseEntity<CheckPointLog> responseEntity) throws Exception {
                        if (TextUtils.equals("200", responseEntity.getCode())) {
                            logList = pointLogDao.loadAll();
                            logAdapter.setLogList(logList);
                            logAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(LogListActivity.this, responseEntity.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        srlLog.setRefreshing(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(LogListActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        logList = pointLogDao.loadAll();
                        logAdapter.setLogList(logList);
                        logAdapter.notifyDataSetChanged();
                        srlLog.setRefreshing(false);
                    }
                });
    }

    private void saveCheckPointLogs(List<CheckPointLog> checkPointLogList) {
        InspectPointDao pointDao = Inspection_App.getInstance().getDaoSession().getInspectPointDao();
        InspectItemDao itemDao = Inspection_App.getInstance().getDaoSession().getInspectItemDao();
        List<InspectPointLog> inspectPointLogList = new ArrayList<>();
        for (int i = 0; i < checkPointLogList.size(); i++) {
            InspectPointLog inspectPointLog = new InspectPointLog();
            CheckPointLog checkPointLog = checkPointLogList.get(i);
            inspectPointLog.setId(checkPointLog.getId());
            inspectPointLog.setOpInspectorCode(checkPointLog.getCensorCode());
            inspectPointLog.setOpInspectorName(checkPointLog.getCensorName());
            inspectPointLog.setResultType(Integer.valueOf(checkPointLog.getStatus()));
            switch (inspectPointLog.getResultType()) {
                case 0:
                    inspectPointLog.setResult("正常");
                    break;
                case 1:
                    inspectPointLog.setResult("异常");
                    break;
            }
            inspectPointLog.setPointLogCode(checkPointLog.getcPointLogCode());
            InspectPoint p = pointDao.queryBuilder().where(InspectPointDao.Properties.Code.eq(checkPointLog.getcPointCode())).unique();
            inspectPointLog.setInspectPointId(p.getId());
            InspectItem item = itemDao.queryBuilder().where(InspectItemDao.Properties.Code.eq(checkPointLog.getProjectCode())).unique();
            inspectPointLog.setInspectItemId(item.getId());
            inspectPointLog.setOpDate(DateUtil.strToDate(checkPointLog.getOpDate(), "yyyy-MM-dd hh:mm:ss"));
            inspectPointLog.setInspected(true);
            inspectPointLogList.add(inspectPointLog);
        }
        pointLogDao.insertOrReplaceInTx(inspectPointLogList);
    }

    @Override
    public void onRefresh() {
        refresh();
    }
}
