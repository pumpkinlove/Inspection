package com.miaxis.inspection.view.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.miaxis.inspection.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoPlayActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vv_play)
    VideoView vvPlay;
    @BindView(R.id.btn_play)
    Button btnPlay;
    @BindView(R.id.btn_pause)
    Button btnPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        ButterKnife.bind(this);
        initData();
        initView();

    }

    @Override
    protected void initData() {
        Uri uri = getIntent().getParcelableExtra("uri");
        vvPlay.setVideoURI(uri);
    }

    @Override
    protected void initView() {
        initToolBar();
        vvPlay.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                btnPause.setVisibility(View.GONE);
                btnPlay.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initToolBar() {
        toolbar.setTitle("播放视频");
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

    @OnClick(R.id.btn_play)
    void onPlayClick() {
        vvPlay.start();
        vvPlay.requestFocus();
        btnPause.setVisibility(View.VISIBLE);
        btnPlay.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_pause)
    void onPauseClick() {
        btnPause.setVisibility(View.GONE);
        btnPlay.setVisibility(View.VISIBLE);
        vvPlay.pause();
    }

}
