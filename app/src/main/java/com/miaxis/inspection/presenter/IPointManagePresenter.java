package com.miaxis.inspection.presenter;

import com.miaxis.inspection.entity.InspectPoint;

import java.util.List;

/**
 * Created by xu.nan on 2018/3/1.
 */

public interface IPointManagePresenter {

    void stopRefresh();

    void updatePoint(InspectPoint point);

    void loadInspectPoints();

    void showInspectPoints(List<InspectPoint> inspectPointList);

    void showProgressDialog();

    void hideProgressDialog();

    void setProgressDialogMessage(String message);

    void setProgressDialogCancelable(boolean cancelable);

    void showUnboundDialog();

    void hideUnboundDialog();

}