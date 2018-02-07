package com.miaxis.inspection.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.InspectItemAdapter;
import com.miaxis.inspection.entity.InspectItem;
import com.miaxis.inspection.entity.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_item)
    RecyclerView rvItem;
    @BindView(R.id.srl_item)
    SwipeRefreshLayout srlItem;

    private InspectItemAdapter adapter;
    private List<InspectItem> itemList;

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
        itemList = new ArrayList<>();
        InspectItem i = new InspectItem();
        i.setId(1L);
        i.setName("门窗");
        itemList.add(i);

        InspectItem i2 = new InspectItem();
        i2.setId(2L);
        i2.setName("水电气-总行");
        itemList.add(i2);

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
            setTitle(task.getName());
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
                startActivity(new Intent(this, ScanPointActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
