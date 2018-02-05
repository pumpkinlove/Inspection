package com.miaxis.inspection.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by xu.nan on 2018/1/30.
 */
@Entity
public class ExcuteTime {

    @Id(autoincrement = true)
    private Long id;

    private Date beginTime;
    private Date endTime;
    private Date remindTime;

    @Generated(hash = 1583600965)
    public ExcuteTime(Long id, Date beginTime, Date endTime, Date remindTime) {
        this.id = id;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.remindTime = remindTime;
    }

    @Generated(hash = 1230255714)
    public ExcuteTime() {
    }

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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
