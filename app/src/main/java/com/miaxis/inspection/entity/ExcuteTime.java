package com.miaxis.inspection.entity;

import java.util.Date;

/**
 * Created by xu.nan on 2018/1/30.
 */

public class ExcuteTime {

    private Date beginTime;
    private Date endTime;
    private Date remindTime;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
    }
}
