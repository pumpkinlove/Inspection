package com.miaxis.inspection.presenter;

import com.miaxis.inspection.entity.InspectPoint;
import com.miaxis.inspection.model.IPointManageModel;
import com.miaxis.inspection.model.PointManageModelImpl;
import com.miaxis.inspection.view.IPointManageView;

import java.util.List;

/**
 * Created by xu.nan on 2018/3/1.
 */

public class PointManagePresenterImpl implements IPointManagePresenter {

    private IPointManageView pointManageView;
    private IPointManageModel pointManageModel;

    public PointManagePresenterImpl(IPointManageView pointManageView) {
        this.pointManageView = pointManageView;
        pointManageModel = new PointManageModelImpl(this);
    }

    @Override
    public void stopRefresh() {
        pointManageView.stopRefresh();
    }

    @Override
    public void updatePoint(InspectPoint point) {
        pointManageModel.updatePoint(point);
    }

    @Override
    public void loadInspectPoints() {
        pointManageModel.downloadPoint();
    }

    @Override
    public void showInspectPoints(List<InspectPoint> inspectPointList) {
        pointManageView.showInspectPoints(inspectPointList);
    }

    @Override
    public void showProgressDialog() {
        pointManageView.showProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        pointManageView.hideProgressDialog();
    }

    @Override
    public void setProgressDialogMessage(String message) {
        pointManageView.setProgressDialogMessage(message);
    }

    @Override
    public void setProgressDialogCancelable(boolean cancelable) {
        pointManageView.setProgressDialogCancelable(cancelable);
    }

    @Override
    public void showUnboundDialog() {
        pointManageView.showUnboundDialog();
    }

    @Override
    public void hideUnboundDialog() {
        pointManageView.hideUnboundDialog();
    }
}
