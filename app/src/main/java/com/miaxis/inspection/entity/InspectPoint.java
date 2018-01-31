package com.miaxis.inspection.entity;

import java.io.Serializable;

/**
 * 检查点
 * Created by xu.nan on 2018/1/3.
 */

public class InspectPoint implements Serializable {

    private Long id;

    private String pointName;
    private String itemName;

    private boolean bound;
    private String bindCode;

    private InspectItem inspectItem;        //所属检查项
    private Organization organization;      //所属机构

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isBound() {
        return bound;
    }

    public void setBound(boolean bound) {
        this.bound = bound;
    }

    public String getBindCode() {
        return bindCode;
    }

    public void setBindCode(String bindCode) {
        this.bindCode = bindCode;
    }

    public InspectItem getInspectItem() {
        return inspectItem;
    }

    public void setInspectItem(InspectItem inspectItem) {
        this.inspectItem = inspectItem;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
