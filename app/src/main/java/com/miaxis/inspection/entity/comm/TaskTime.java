package com.miaxis.inspection.entity.comm;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by xu.nan on 2018/2/26.
 */
@Entity
public class TaskTime {

    private String  taskId;
    private Integer taskTimesType;
    private String  taskStartTime;
    private String  taskStartDay;
    private String  taskEndTime;
    private String  taskEndDay;
    private String  taskWarnTime;
    private String  taskWarnDay;
    private String  taskWarnWeekDay;
    private String  taskWarnMonth;
    @Generated(hash = 575945729)
    public TaskTime(String taskId, Integer taskTimesType, String taskStartTime,
            String taskStartDay, String taskEndTime, String taskEndDay,
            String taskWarnTime, String taskWarnDay, String taskWarnWeekDay,
            String taskWarnMonth) {
        this.taskId = taskId;
        this.taskTimesType = taskTimesType;
        this.taskStartTime = taskStartTime;
        this.taskStartDay = taskStartDay;
        this.taskEndTime = taskEndTime;
        this.taskEndDay = taskEndDay;
        this.taskWarnTime = taskWarnTime;
        this.taskWarnDay = taskWarnDay;
        this.taskWarnWeekDay = taskWarnWeekDay;
        this.taskWarnMonth = taskWarnMonth;
    }
    @Generated(hash = 1763742976)
    public TaskTime() {
    }
    public String getTaskId() {
        return this.taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public Integer getTaskTimesType() {
        return this.taskTimesType;
    }
    public void setTaskTimesType(Integer taskTimesType) {
        this.taskTimesType = taskTimesType;
    }
    public String getTaskStartTime() {
        return this.taskStartTime;
    }
    public void setTaskStartTime(String taskStartTime) {
        this.taskStartTime = taskStartTime;
    }
    public String getTaskStartDay() {
        return this.taskStartDay;
    }
    public void setTaskStartDay(String taskStartDay) {
        this.taskStartDay = taskStartDay;
    }
    public String getTaskEndTime() {
        return this.taskEndTime;
    }
    public void setTaskEndTime(String taskEndTime) {
        this.taskEndTime = taskEndTime;
    }
    public String getTaskEndDay() {
        return this.taskEndDay;
    }
    public void setTaskEndDay(String taskEndDay) {
        this.taskEndDay = taskEndDay;
    }
    public String getTaskWarnTime() {
        return this.taskWarnTime;
    }
    public void setTaskWarnTime(String taskWarnTime) {
        this.taskWarnTime = taskWarnTime;
    }
    public String getTaskWarnDay() {
        return this.taskWarnDay;
    }
    public void setTaskWarnDay(String taskWarnDay) {
        this.taskWarnDay = taskWarnDay;
    }
    public String getTaskWarnWeekDay() {
        return this.taskWarnWeekDay;
    }
    public void setTaskWarnWeekDay(String taskWarnWeekDay) {
        this.taskWarnWeekDay = taskWarnWeekDay;
    }
    public String getTaskWarnMonth() {
        return this.taskWarnMonth;
    }
    public void setTaskWarnMonth(String taskWarnMonth) {
        this.taskWarnMonth = taskWarnMonth;
    }

}
