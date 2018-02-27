package com.miaxis.inspection.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class InspectorSpinnerAdapter extends BaseAdapter {

    private List<Inspector> inspectorList;
    private Context mContext;

    public InspectorSpinnerAdapter(List<Inspector> inspectorList, Context mContext) {
        this.inspectorList = inspectorList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if (inspectorList != null) {
            return inspectorList.size();
        }
        return 0;
    }

    @Override
    public Inspector getItem(int i) {
        if (inspectorList != null) {
            return inspectorList.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_spinner_inspector, null);

        } else {

        }

        View view = inflater.inflate(R.layout.item_spinner_inspector,null);



        return view;


    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_inspector_name)
        TextView tvInspectorName;
        @BindView(R.id.tv_inspector_code)
        TextView tvInspectorCode;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setInspectorList(List<Inspector> inspectorList) {
        this.inspectorList = inspectorList;
    }


}
