package com.miaxis.inspection.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaxis.inspection.R;
import com.miaxis.inspection.entity.InspectItem;
import com.miaxis.inspection.entity.comm.CheckProjectTime;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu.nan on 2018/1/3.
 */

public class InspectItemAdapter extends RecyclerView.Adapter<InspectItemAdapter.ViewHolder> {

    private OnItemClickListener listener;
    private List<InspectItem> inspectItemList;
    private Context context;

    public InspectItemAdapter(List<InspectItem> inspectItemList, Context context) {
        this.inspectItemList = inspectItemList;
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
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_inspect_item, parent, false);
        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InspectItem item = inspectItemList.get(position);
        List<CheckProjectTime> itemTime = item.getCheckProjectTime();

        holder.tvItemName.setText(item.getName());

//        holder.tvItemProgress.setText(String.format(context.getString(R.string.item_progress_format),2, 1));
    }

    @Override
    public int getItemCount() {
        if (inspectItemList != null) {
            return inspectItemList.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private OnItemClickListener listener;

        @BindView(R.id.tv_item_name)
        TextView tvItemName;
        @BindView(R.id.tv_item_progress)
        TextView tvItemProgress;

        ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.cl_item)
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
