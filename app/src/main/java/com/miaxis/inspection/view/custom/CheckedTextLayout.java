package com.miaxis.inspection.view.custom;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miaxis.inspection.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class CheckedTextLayout extends LinearLayout {

    private Context mContext;
    private List<String> textList;

    private int unCheckedTextColor;
    private int checkedTextColor;

    private int unCheckedDrawableId;
    private int checkedDrawableId;

    private int checkedPosition;
    private int colCount;


    @BindView(R.id.gl_checked_text)
    GridLayout glCheckedText;

    public CheckedTextLayout(Context context) {
        super(context);
        init();
    }

    public CheckedTextLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CheckedTextLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    private void init() {
        View v = inflate(getContext(), R.layout.view_checked_text, this);
        ButterKnife.bind(this, v);

        initView();


    }

    private void initView() {

    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public List<String> getTextList() {
        return textList;
    }

    public void setTextList(List<String> textList) {
        this.textList = textList;
        for (int i = 0; i < textList.size(); i ++) {
            TextView tv = new TextView(mContext);
            tv.setText(textList.get(i));
            tv.setTextColor(unCheckedTextColor);
            tv.setGravity(Gravity.CENTER);
            glCheckedText.addView(tv);
        }
    }

    public int getUnCheckedTextColor() {
        return unCheckedTextColor;
    }

    public void setUnCheckedTextColor(int unCheckedTextColor) {
        this.unCheckedTextColor = unCheckedTextColor;
    }

    public int getCheckedTextColor() {
        return checkedTextColor;
    }

    public void setCheckedTextColor(int checkedTextColor) {
        this.checkedTextColor = checkedTextColor;
    }

    public int getUnCheckedDrawableId() {
        return unCheckedDrawableId;
    }

    public void setUnCheckedDrawableId(int unCheckedDrawableId) {
        this.unCheckedDrawableId = unCheckedDrawableId;
    }

    public int getCheckedDrawableId() {
        return checkedDrawableId;
    }

    public void setCheckedDrawableId(int checkedDrawableId) {
        this.checkedDrawableId = checkedDrawableId;
    }

    public int getCheckedPosition() {
        return checkedPosition;
    }

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
    }

    public int getColCount() {
        return colCount;
    }

    public void setColCount(int colCount) {
        this.colCount = colCount;
    }
}
