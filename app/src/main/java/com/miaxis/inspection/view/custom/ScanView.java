package com.miaxis.inspection.view.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.miaxis.inspection.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu.nan on 2017/6/6.
 */

public class ScanView extends LinearLayout {

    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.iv_green_line)
    ImageView ivGreenLine;
    private Animation mScanningAnimation;

    private Bitmap bitmap;

    public ScanView(Context context) {
        super(context);
        init();
    }

    public ScanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ScanView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        if (ivScan != null) {
            ivScan.setImageBitmap(bitmap);
        }
    }

    private void init() {
        View v = inflate(getContext(), R.layout.view_scan, this);
        ButterKnife.bind(this, v);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        int left = ivScan.getLeft();
        int right = ivScan.getRight();
        int top = ivScan.getTop();
        int bottom = ivScan.getBottom();

        int height = ivGreenLine.getHeight()/2;

        mScanningAnimation = new TranslateAnimation(left, left, top - height, bottom - height);
        mScanningAnimation.setDuration(3000); // 动画持续时间
        mScanningAnimation.setRepeatCount(Animation.INFINITE); // 无限循环

        // 播放动画
        ivGreenLine.setAnimation(mScanningAnimation);
        mScanningAnimation.startNow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mScanningAnimation.cancel();
    }
}
