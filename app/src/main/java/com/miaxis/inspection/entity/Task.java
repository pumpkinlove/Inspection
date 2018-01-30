package com.miaxis.inspection.entity;

import java.io.Serializable;

/**
 * 检查任务
 * Created by xu.nan on 2018/1/3.
 */

public class Task implements Serializable {

    private String name;
    private String beginTime;
    private String endTime;
    private int frequency;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
