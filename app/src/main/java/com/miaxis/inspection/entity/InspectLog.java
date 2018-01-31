package com.miaxis.inspection.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 历史检查记录
 * Created by xu.nan on 2018/1/29.
 */

public class InspectLog implements Serializable {

    private Long id;
    private Date opDate;
    private String opInspectorName;
    private String result;

    private InspectPoint inspectPoint;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }

    public String getOpInspectorName() {
        return opInspectorName;
    }

    public void setOpInspectorName(String opInspectorName) {
        this.opInspectorName = opInspectorName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public InspectPoint getInspectPoint() {
        return inspectPoint;
    }

    public void setInspectPoint(InspectPoint inspectPoint) {
        this.inspectPoint = inspectPoint;
    }
}
