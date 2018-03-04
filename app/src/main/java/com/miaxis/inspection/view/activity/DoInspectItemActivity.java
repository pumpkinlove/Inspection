package com.miaxis.inspection.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.SimpleContentAdapter;
import com.miaxis.inspection.app.Inspection_App;
import com.miaxis.inspection.entity.Config;
import com.miaxis.inspection.entity.InspectContent;
import com.miaxis.inspection.entity.InspectContentLog;
import com.miaxis.inspection.entity.InspectForm;
import com.miaxis.inspection.entity.InspectItem;
import com.miaxis.inspection.entity.InspectPointLog;
import com.miaxis.inspection.entity.InspectPoint;
import com.miaxis.inspection.entity.Organization;
import com.miaxis.inspection.entity.ResponseEntity;
import com.miaxis.inspection.entity.Task;
import com.miaxis.inspection.entity.comm.CheckPointLog;
import com.miaxis.inspection.model.local.greenDao.gen.DaoSession;
import com.miaxis.inspection.model.local.greenDao.gen.InspectContentLogDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectFormDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectPointLogDao;
import com.miaxis.inspection.model.local.greenDao.gen.OrganizationDao;
import com.miaxis.inspection.model.local.greenDao.gen.TaskDao;
import com.miaxis.inspection.model.remote.retrofit.LogNet;
import com.miaxis.inspection.utils.DateUtil;
import com.miaxis.inspection.view.custom.SimpleDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DoInspectItemActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.tv_point_name)
    TextView tvPointName;
    @BindView(R.id.tv_item_name)
    TextView tvItemName;

    private static final int REQ_CODE_INSPECT_CONTENT = 1;

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

        contentList = inspectItem.getInspectContentList();

        pointLogCode = inspectPoint.getCode() + "_" + new Date().getTime();

        pointLog = new InspectPointLog();
        pointLog.setInspectItemId(inspectItem.getId());
        pointLog.setInspectItem(inspectItem);
        pointLog.setPointLogCode(pointLogCode);
        pointLog.setInspectPointId(inspectPoint.getId());
        pointLog.setInspectPoint(inspectPoint);

        contentLogList = new ArrayList<>();

        adapter = new SimpleContentAdapter(contentList, contentLogList, this);
        adapter.setListener(new SimpleContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(DoInspectItemActivity.this, DoInspectContentActivity.class);
                i.putExtra("pointLogCode", pointLogCode);
                i.putExtra("content", contentList.get(position));
                startActivityForResult(i, REQ_CODE_INSPECT_CONTENT);
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
                pointLog.setResultType(0);
                for (int i = 0; i < contentLogList.size(); i++) {
                    if (contentLogList.get(i).getHasProblem()) {
                        pointLog.setResult("异常");
                        pointLog.setResultType(1);
                    }
                }
                uploadPointLog(pointLog);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_INSPECT_CONTENT) {
            switch (resultCode) {
                case 111:
                    pointLog.__setDaoSession(mDaoSession);
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
                    pointLogDao.insertOrReplace(pointLog);
                    contentLogList = pointLog.getContentList();
                    adapter.setContentLogList(contentLogList);
                    adapter.notifyDataSetChanged();
                    break;
                case -111:
                    pointLog.__setDaoSession(mDaoSession);
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
                    pointLogDao.insertOrReplace(pointLog);
                    contentLogList = pointLog.getContentList();
                    adapter.setContentLogList(contentLogList);
                    adapter.notifyDataSetChanged();
                    break;
            }

        }

    }


    private void uploadPointLog(final InspectPointLog pointLog) {
        Config config = Inspection_App.getInstance().getDaoSession().getConfigDao().load(1L);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();
        LogNet net = retrofit.create(LogNet.class);

        net.uploadPointLog(fetchPointLogJson(pointLog, config))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity<String>>() {
                    @Override
                    public void accept(ResponseEntity<String> responseEntity) throws Exception {
                        if (TextUtils.equals("200", responseEntity.getCode())) {
                            pointLog.setUploaded(true);
                        } else {
                            pointLog.setUploaded(false);
                        }
                        pointLogDao.insertOrReplace(pointLog);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        pointLog.setUploaded(false);
                        pointLogDao.insertOrReplace(pointLog);
                    }
                });

    }

    private String fetchPointLogJson(InspectPointLog pointLog, Config config) {
        Organization org = mDaoSession.getOrganizationDao().queryBuilder().where(OrganizationDao.Properties.Bankcode.eq(config.getOrgCode())).unique();
        CheckPointLog checkPointLog = new CheckPointLog();
        checkPointLog.setBankId(String.valueOf(org.getId()));
        checkPointLog.setBankCode(org.getBankcode());
        checkPointLog.setBankNode(org.getBankno());
        checkPointLog.setBankName(org.getBankname());
        checkPointLog.setCensorCode(pointLog.getOpInspectorCode());
        checkPointLog.setCensorName(pointLog.getOpInspectorName());
        checkPointLog.setcPointLogCode(pointLog.getPointLogCode());
        checkPointLog.setcPointCode(pointLog.getInspectPoint().getCode());
        checkPointLog.setcPointName(pointLog.getInspectPoint().getPointName());
        checkPointLog.setProjectCode(pointLog.getInspectItem().getCode());
        checkPointLog.setProjectFormCode(pointLog.getInspectItem().getInspectFormCode());
        checkPointLog.setOpDate(DateUtil.toAll(pointLog.getOpDate()));
        checkPointLog.setStatus(String.valueOf(pointLog.getResultType()));
        return new Gson().toJson(checkPointLog);
    }

}
