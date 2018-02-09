package com.miaxis.inspection.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miaxis.inspection.R;
import com.miaxis.inspection.entity.InspectContent;
import com.miaxis.inspection.entity.InspectContentLog;
import com.miaxis.inspection.entity.ResultType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 检查日志详情页面，检查内容适配器
 * Created by xu.nan on 2018/1/3.
 */

public class LogContentDetailAdapter extends RecyclerView.Adapter<LogContentDetailAdapter.ViewHolder> {

    private OnItemClickListener listener;
    private List<InspectContentLog> contentLogList;
    private Context context;

    public LogContentDetailAdapter(List<InspectContentLog> contentLogList, Context context) {
        this.contentLogList = contentLogList;
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
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_log_content, parent, false);
        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InspectContentLog contentLog = contentLogList.get(position);
        InspectContent content = contentLog.getInspectContent();
        holder.tvContentName.setText(content.getName());
        ResultType resultType = contentLog.getResult();
        if (resultType.getIsProblem()) {
            holder.tvContentResult.setTextColor(context.getResources().getColor(R.color.red));
        } else {
            holder.tvContentResult.setTextColor(context.getResources().getColor(R.color.green_dark));
        }
        holder.tvContentResult.setText(resultType.getResultName());
    }

    @Override
    public int getItemCount() {
        if (contentLogList != null) {
            return contentLogList.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private OnItemClickListener listener;

        @BindView(R.id.tv_content_name)
        TextView tvContentName;
        @BindView(R.id.tv_content_result)
        TextView tvContentResult;

        ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.ll_log_content)
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
