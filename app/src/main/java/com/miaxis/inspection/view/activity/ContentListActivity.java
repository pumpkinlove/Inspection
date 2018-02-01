package com.miaxis.inspection.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.SimpleContentAdapter;
import com.miaxis.inspection.entity.InspectContent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    private List<InspectContent> contentList;
    private SimpleContentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_list);
        ButterKnife.bind(this);
        initData();
        initView();

    }

    @Override
    protected void initData() {
        contentList = new ArrayList<>();
        InspectContent content1 = new InspectContent();
        content1.setName("测试检查内容1");
        contentList.add(content1);

        InspectContent content2 = new InspectContent();
        content2.setName("测试检查内容2");
        contentList.add(content2);

        InspectContent content3 = new InspectContent();
        content3.setName("测试检查内容3");
        contentList.add(content3);


        adapter = new SimpleContentAdapter(contentList, this);
        adapter.setListener(new SimpleContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(ContentListActivity.this, DoInspectContentActivity.class);
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
    }

    private void initToolBar() {
        toolbar.setTitle("检查内容");
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
