package com.miaxis.inspection.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaxis.inspection.R;
import com.miaxis.inspection.entity.Inspector;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu.nan on 2018/1/3.
 */

public class InspectorAdapter extends RecyclerView.Adapter<InspectorAdapter.ViewHolder> {

    private OnItemClickListener listener;

    private List<Inspector> inspectorList;
    private Context context;

    public InspectorAdapter(List<Inspector> inspectorList, Context context) {
        this.inspectorList = inspectorList;
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
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_inspector, parent, false);
        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Inspector inspector = inspectorList.get(position);
        holder.tvInspectorName.setText(inspector.getCensorName());
        holder.tvInspectorCode.setText(inspector.getCensorCode());
    }

    @Override
    public int getItemCount() {
        if (inspectorList != null) {
            return inspectorList.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private OnItemClickListener listener;

        @BindView(R.id.iv_inspector_photo)
        ImageView ivInspectorPhoto;
        @BindView(R.id.tv_inspector_name)
        TextView tvInspectorName;
        @BindView(R.id.tv_inspector_code)
        TextView tvInspectorCode;
        @BindView(R.id.tv_inspector_role)
        TextView tvInspectorRole;
        @BindView(R.id.cv_inspector)
        CardView cvInspector;

        ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.cv_inspector)
        void onItemClick(View view) {
            if (listener != null) {
                listener.onItemClick(view, getPosition());
            }
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setInspectorList(List<Inspector> inspectorList) {
        this.inspectorList = inspectorList;
    }


}
