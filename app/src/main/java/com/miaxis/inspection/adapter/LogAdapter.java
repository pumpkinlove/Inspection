package com.miaxis.inspection.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaxis.inspection.R;
import com.miaxis.inspection.entity.InspectPointLog;
import com.miaxis.inspection.utils.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 检查记录适配器
 * Created by xu.nan on 2018/1/31.
 */

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {

    public void setLogList(List<InspectPointLog> logList) {
        this.logList = logList;
    }

    private OnItemClickListener listener;
    private List<InspectPointLog> logList;
    private Context context;

    public LogAdapter(List<InspectPointLog> logList, Context context) {
        this.logList = logList;
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
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_log, parent, false);
        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InspectPointLog log = logList.get(position);
        if (log.getInspected()) {
            holder.tvLogPoint.setText(log.getInspectPoint().getPointName());
            holder.tvLogTime.setText(DateUtil.toHourMinString(log.getOpDate()));
            holder.tvLogResult.setText(log.getResult());
        }
    }

    @Override
    public int getItemCount() {
        if (logList != null) {
            return logList.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private OnItemClickListener listener;

        @BindView(R.id.tv_log_point)
        TextView tvLogPoint;
        @BindView(R.id.tv_log_time)
        TextView tvLogTime;
        @BindView(R.id.tv_log_result)
        TextView tvLogResult;

        ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.ll_log)
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
