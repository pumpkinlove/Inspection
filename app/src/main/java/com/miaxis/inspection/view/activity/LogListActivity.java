package com.miaxis.inspection.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.LogAdapter;
import com.miaxis.inspection.app.Inspection_App;
import com.miaxis.inspection.dao.gen.InspectLogDao;
import com.miaxis.inspection.entity.InspectLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_log)
    RecyclerView rvLog;

    private List<InspectLog> logList;
    private LogAdapter logAdapter;

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
        logList = Inspection_App.getInstance().getDaoSession().getInspectLogDao().queryBuilder().where(InspectLogDao.Properties.Inspected.eq(true)).list();
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
    }
}
