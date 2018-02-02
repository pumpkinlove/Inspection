package com.miaxis.inspection.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaxis.inspection.R;
import com.miaxis.inspection.entity.InspectPoint;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu.nan on 2018/1/3.
 */

public class PointManageAdapter extends RecyclerView.Adapter<PointManageAdapter.ViewHolder> {

    private OnItemClickListener listener;
    private List<InspectPoint> pointList;
    private Context context;

    public PointManageAdapter(List<InspectPoint> pointList, Context context) {
        this.pointList = pointList;
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
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_point_manage, parent, false);
        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InspectPoint point = pointList.get(position);
        holder.tvPointName.setText(point.getPointName());
        if (point.isBound()) {
            holder.tvAddRfid.setVisibility(View.GONE);
            holder.tvModRfid.setVisibility(View.VISIBLE);
            holder.tvDelRfid.setVisibility(View.VISIBLE);
        } else {
            holder.tvAddRfid.setVisibility(View.VISIBLE);
            holder.tvModRfid.setVisibility(View.GONE);
            holder.tvDelRfid.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (pointList != null) {
            return pointList.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private OnItemClickListener listener;

        @BindView(R.id.tv_point_name)
        TextView tvPointName;
        @BindView(R.id.tv_add_rfid)
        TextView tvAddRfid;
        @BindView(R.id.tv_mod_rfid)
        TextView tvModRfid;
        @BindView(R.id.tv_del_rfid)
        TextView tvDelRfid;

        ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.tv_add_rfid)
        void onAddRfid(View view) {
            if (listener != null) {
                listener.onAddRfid(view, getPosition());
            }
        }

        @OnClick(R.id.tv_mod_rfid)
        void onModRfid(View view) {
            if (listener != null) {
                listener.onModRfid(view, getPosition());
            }
        }

        @OnClick(R.id.tv_del_rfid)
        void onDelRfid(View view) {
            if (listener != null) {
                listener.onDelRfid(view, getPosition());
            }
        }

    }

    public interface OnItemClickListener {
        void onAddRfid(View view, int position);
        void onModRfid(View view, int position);
        void onDelRfid(View view, int position);
    }

}
