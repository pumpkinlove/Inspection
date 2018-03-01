package com.miaxis.inspection.model;

import com.miaxis.inspection.entity.InspectPoint;

/**
 * Created by xu.nan on 2018/3/1.
 */

public interface IPointManageModel {

    void updatePoint(InspectPoint inspectPoint);

    void downloadPoint();

}
