package com.miaxis.inspection.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.TextView;

import com.miaxis.inspection.R;
import com.miaxis.inspection.entity.InspectContent;
import com.miaxis.inspection.view.custom.CheckedTextLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DoInspectContentActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_content_name)
    TextView tvContentName;
    @BindView(R.id.gl_pic_description)
    GridLayout glPicDescription;

    @BindColor(R.color.red)
    int red;
    @BindColor(R.color.green_dark)
    int greenDark;

    @BindColor(R.color.blue_band_dark2)
    int blueDark2;

    private InspectContent inspectContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_insepct_content);
        ButterKnife.bind(this);

        initData();
        initView();

    }

    private void initToolBar() {
        toolbar.setTitle("检查内容");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_submit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {
        inspectContent = (InspectContent) getIntent().getSerializableExtra("content");

    }

    @Override
    protected void initView() {
        initToolBar();
        tvContentName.setText(inspectContent.getName());

    }
}
