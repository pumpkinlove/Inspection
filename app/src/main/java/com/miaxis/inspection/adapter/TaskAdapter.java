package com.miaxis.inspection.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaxis.inspection.R;
import com.miaxis.inspection.entity.Task;
import com.miaxis.inspection.utils.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu.nan on 2018/1/3.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private OnTaskClickListener listener;
    private List<Task> taskList;
    private Context context;

    public TaskAdapter(List<Task> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
    }

    public OnTaskClickListener getListener() {
        return listener;
    }

    public void setListener(OnTaskClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.tvTaskName.setText(task.getName());
        holder.tvTaskBeginTime.setText(DateUtil.toAll(task.getBeginTime()));
        holder.tvTaskEndTime.setText(DateUtil.toAll(task.getEndTime()));
    }

    @Override
    public int getItemCount() {
        if (taskList != null) {
            return taskList.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private OnTaskClickListener listener;

        @BindView(R.id.tv_task_name)
        TextView tvTaskName;
        @BindView(R.id.tv_begin_time)
        TextView tvTaskBeginTime;
        @BindView(R.id.tv_end_time)
        TextView tvTaskEndTime;

        public ViewHolder(View itemView, OnTaskClickListener listener) {
            super(itemView);
            this.listener = listener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.cv_task)
        void onItemClick(View view) {
            if (listener != null) {
                listener.onTaskClick(view, getPosition());
            }
        }


    }

    public interface OnTaskClickListener {
        void onTaskClick(View view, int position);
    }

}
