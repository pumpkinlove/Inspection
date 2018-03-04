package com.miaxis.inspection.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaxis.inspection.R;
import com.miaxis.inspection.app.Inspection_App;
import com.miaxis.inspection.entity.InspectContent;
import com.miaxis.inspection.entity.InspectContentLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu.nan on 2018/1/3.
 */

public class SimpleContentAdapter extends RecyclerView.Adapter<SimpleContentAdapter.ViewHolder> {

    private OnItemClickListener listener;
    private List<InspectContent> contentList;
    private List<InspectContentLog> contentLogList;
    private Context context;

    public void setContentList(List<InspectContent> contentList) {
        this.contentList = contentList;
    }

    public void setContentLogList(List<InspectContentLog> contentLogList) {
        this.contentLogList = contentLogList;
    }

    public SimpleContentAdapter(List<InspectContent> contentList, List<InspectContentLog> contentLogList, Context context) {
        this.contentList = contentList;
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
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_content, parent, false);
        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InspectContent content = contentList.get(position);
        holder.tvContentName.setText(content.getName());
        if (contentLogList.size() == contentList.size()) {
            InspectContentLog contentLog = contentLogList.get(position);
            holder.tvStatus.setText(contentLog.getResultType());
            if (contentLog.getHasProblem()) {
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.red));
            } else {
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green_dark));
            }
        }
    }

    @Override
    public int getItemCount() {
        if (contentList != null) {
            return contentList.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private OnItemClickListener listener;

        @BindView(R.id.tv_content_name)
        TextView tvContentName;
        @BindView(R.id.tv_status)
        TextView tvStatus;

        ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.ll_content)
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
