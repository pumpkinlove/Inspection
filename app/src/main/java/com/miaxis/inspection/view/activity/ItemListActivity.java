package com.miaxis.inspection.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.InspectItemAdapter;
import com.miaxis.inspection.app.Inspection_App;
import com.miaxis.inspection.entity.InspectForm;
import com.miaxis.inspection.entity.InspectItem;
import com.miaxis.inspection.entity.InspectPoint;
import com.miaxis.inspection.entity.Task;
import com.miaxis.inspection.model.local.greenDao.gen.InspectPointDao;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ItemListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_item)
    RecyclerView rvItem;
    @BindView(R.id.srl_item)
    SwipeRefreshLayout srlItem;

    private InspectItemAdapter adapter;
    private List<InspectItem> itemList;
    private Task mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ButterKnife.bind(this);

        initData();
        initView();
        initToolBar();
    }


    @Override
    protected void initData() {
        mTask = (Task) getIntent().getSerializableExtra("task");
        mTask.__setDaoSession(Inspection_App.getInstance().getDaoSession());

        InspectForm inspectForm = mTask.getInspectForm();
        itemList = inspectForm.getInspectItemList();
        adapter = new InspectItemAdapter(itemList, this);

    }

    @Override
    protected void initView() {
        rvItem.setAdapter(adapter);
        rvItem.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initToolBar() {
        Task task = (Task) getIntent().getSerializableExtra("task");
        if (task != null) {
            setTitle(task.getTaskName());
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_list_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_scan:
                startActivityForResult(new Intent(this, CaptureActivity.class), 1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != data) {
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                String rfid = bundle.getString(CodeUtils.RESULT_STRING);
                scanPointResult(rfid);
            } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                Toast.makeText(ItemListActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
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
                        Intent i = new Intent(ItemListActivity.this, DoInspectItemActivity.class);
                        i.putExtra("point", inspectPoint);
                        startActivity(i);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(ItemListActivity.this, "无效的编码", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
