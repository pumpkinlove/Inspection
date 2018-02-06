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
import com.miaxis.inspection.entity.InspectItem;
import com.miaxis.inspection.entity.InspectPoint;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DoInspectActivity extends BaseActivity {

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
    private List<InspectContent> contentList;
    private SimpleContentAdapter adapter;

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

        inspectPoint = (InspectPoint) getIntent().getSerializableExtra("point");
        inspectPoint.__setDaoSession(Inspection_App.getInstance().getDaoSession());
        inspectItem = inspectPoint.getInspectItem();
        contentList = inspectItem.getInspectContentList();

        adapter = new SimpleContentAdapter(contentList, this);
        adapter.setListener(new SimpleContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(DoInspectActivity.this, DoInspectContentActivity.class);
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

    }

}
