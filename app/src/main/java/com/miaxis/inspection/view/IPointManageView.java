package com.miaxis.inspection.view;

import com.miaxis.inspection.entity.InspectPoint;

import java.util.List;

/**
 * Created by xu.nan on 2018/3/1.
 */

public interface IPointManageView {

    void stopRefresh();

    void showInspectPoints(List<InspectPoint> inspectPointList);

    void showProgressDialog();

    void hideProgressDialog();

    void setProgressDialogMessage(String message);

    void setProgressDialogCancelable(boolean cancelable);

    void showUnboundDialog();

    void hideUnboundDialog();

}
