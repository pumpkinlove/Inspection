package com.miaxis.inspection.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 检查项
 * Created by xu.nan on 2018/1/3.
 */

public class InspectItem implements Serializable {

    private Long id;
    private String name;
    private InspectForm inspectForm;
    private InspectItem inspectItem;
    private int count;
    private int type;
    private List<ExcuteTime> timeList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InspectForm getInspectForm() {
        return inspectForm;
    }

    public void setInspectForm(InspectForm inspectForm) {
        this.inspectForm = inspectForm;
    }

    public InspectItem getInspectItem() {
        return inspectItem;
    }

    public void setInspectItem(InspectItem inspectItem) {
        this.inspectItem = inspectItem;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ExcuteTime> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<ExcuteTime> timeList) {
        this.timeList = timeList;
    }
}
