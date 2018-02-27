package com.miaxis.inspection.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by xu.nan on 2018/1/30.
 */
@Entity
public class ExecuteTime {

    @Id(autoincrement = true)
    private Long id;

    private Long taskId;
    private Long inspectItemId;

    private Date beginTime;
    private Date endTime;
    private Date remindTime;

    @Generated(hash = 2057441948)
    public ExecuteTime(Long id, Long taskId, Long inspectItemId, Date beginTime,
            Date endTime, Date remindTime) {
        this.id = id;
        this.taskId = taskId;
        this.inspectItemId = inspectItemId;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.remindTime = remindTime;
    }
    @Generated(hash = 1153843091)
    public ExecuteTime() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getTaskId() {
        return this.taskId;
    }
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    public Long getInspectItemId() {
        return this.inspectItemId;
    }
    public void setInspectItemId(Long inspectItemId) {
        this.inspectItemId = inspectItemId;
    }
    public Date getBeginTime() {
        return this.beginTime;
    }
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
    public Date getEndTime() {
        return this.endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public Date getRemindTime() {
        return this.remindTime;
    }
    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
    }


}
