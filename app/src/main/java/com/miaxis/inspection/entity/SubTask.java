package com.miaxis.inspection.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/3/5 0005.
 */
@Entity
public class SubTask {

    @Id(autoincrement = true)
    private Long id;

    private String parentCode;

    private Date beginTime;
    private Date remindTime;
    private Date endTime;

    private int status;
    private String statusName;
    @Generated(hash = 1897280705)
    public SubTask(Long id, String parentCode, Date beginTime, Date remindTime,
            Date endTime, int status, String statusName) {
        this.id = id;
        this.parentCode = parentCode;
        this.beginTime = beginTime;
        this.remindTime = remindTime;
        this.endTime = endTime;
        this.status = status;
        this.statusName = statusName;
    }
    @Generated(hash = 115995235)
    public SubTask() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getParentCode() {
        return this.parentCode;
    }
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
    public Date getBeginTime() {
        return this.beginTime;
    }
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
    public Date getRemindTime() {
        return this.remindTime;
    }
    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
    }
    public Date getEndTime() {
        return this.endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getStatusName() {
        return this.statusName;
    }
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }


}
