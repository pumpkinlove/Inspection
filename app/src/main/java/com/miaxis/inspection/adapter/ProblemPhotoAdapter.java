package com.miaxis.inspection.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaxis.inspection.R;
import com.miaxis.inspection.entity.InspectContent;
import com.miaxis.inspection.entity.ProblemPhoto;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu.nan on 2018/2/7.
 */

public class ProblemPhotoAdapter extends RecyclerView.Adapter<ProblemPhotoAdapter.ViewHolder> {

    private OnItemClickListener listener;
    private List<ProblemPhoto> photoList;
    private Context context;

    public ProblemPhotoAdapter(List<ProblemPhoto> photoList, Context context) {
        this.photoList = photoList;
        this.context = context;
    }

    public OnItemClickListener getListener() {
        return listener;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_problem_photo, parent, false);
        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProblemPhoto photo = photoList.get(photoList.size() - position - 1);
        Bitmap bitmap = BitmapFactory.decodeFile(photo.getPicUrl());
        if (bitmap == null && photo.getPicData() != null) {
            bitmap = BitmapFactory.decodeByteArray(photo.getPicData(), 0, photo.getPicData().length);
        }
        if (bitmap != null) {
            holder.ivProblemPhoto.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        if (photoList != null) {
            return photoList.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private OnItemClickListener listener;

        @BindView(R.id.iv_problem_photo)
        ImageView ivProblemPhoto;

        ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.iv_problem_photo)
        void onItemClick(View view) {
            if (listener != null) {
                listener.onItemClick(view, getPosition());
            }
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
