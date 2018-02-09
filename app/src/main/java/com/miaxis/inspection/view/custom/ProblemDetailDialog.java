package com.miaxis.inspection.view.custom;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.miaxis.inspection.R;
import com.miaxis.inspection.adapter.ProblemPhotoAdapter;
import com.miaxis.inspection.entity.ProblemPhoto;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xu.nan on 2017/7/10.
 */

public class ProblemDetailDialog extends DialogFragment {

    Unbinder unbinder;
    @BindView(R.id.tv_problem_description)
    TextView tvProblemDescription;
    @BindView(R.id.rv_problem_pics)
    RecyclerView rvProblemPics;

    private String problemDescription;
    private List<ProblemPhoto> photoList;
    private ProblemPhotoAdapter photoAdapter;

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public void setPhotoList(List<ProblemPhoto> photoList) {
        this.photoList = photoList;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.9), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_problem_detail, container);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }

    private void initData() {
        photoAdapter = new ProblemPhotoAdapter(photoList, getActivity());
    }

    private void initView() {
        tvProblemDescription.setText(problemDescription);
        rvProblemPics.setAdapter(photoAdapter);
        rvProblemPics.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

}
