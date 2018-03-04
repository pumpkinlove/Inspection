package com.miaxis.inspection.entity.comm;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by xu.nan on 2018/2/26.
 */
@Entity
public class CheckProjectTime {

    @Id(autoincrement = true)
    private Long id;
    private String cProjectCode;
    private Integer cProjectTimesType;
    private String cProjectWarnWeekDay;
    private String cProjectWarnMonth;
    private String cProjectWrnday;
    private String cProjectStartTime;
    private String cProjectEndTime;
    private String cProjectWarnTime;
    @Generated(hash = 1507209356)
    public CheckProjectTime(Long id, String cProjectCode, Integer cProjectTimesType,
            String cProjectWarnWeekDay, String cProjectWarnMonth,
            String cProjectWrnday, String cProjectStartTime, String cProjectEndTime,
            String cProjectWarnTime) {
        this.id = id;
        this.cProjectCode = cProjectCode;
        this.cProjectTimesType = cProjectTimesType;
        this.cProjectWarnWeekDay = cProjectWarnWeekDay;
        this.cProjectWarnMonth = cProjectWarnMonth;
        this.cProjectWrnday = cProjectWrnday;
        this.cProjectStartTime = cProjectStartTime;
        this.cProjectEndTime = cProjectEndTime;
        this.cProjectWarnTime = cProjectWarnTime;
    }
    @Generated(hash = 1159443729)
    public CheckProjectTime() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCProjectCode() {
        return this.cProjectCode;
    }
    public void setCProjectCode(String cProjectCode) {
        this.cProjectCode = cProjectCode;
    }
    public Integer getCProjectTimesType() {
        return this.cProjectTimesType;
    }
    public void setCProjectTimesType(Integer cProjectTimesType) {
        this.cProjectTimesType = cProjectTimesType;
    }
    public String getCProjectWarnWeekDay() {
        return this.cProjectWarnWeekDay;
    }
    public void setCProjectWarnWeekDay(String cProjectWarnWeekDay) {
        this.cProjectWarnWeekDay = cProjectWarnWeekDay;
    }
    public String getCProjectWarnMonth() {
        return this.cProjectWarnMonth;
    }
    public void setCProjectWarnMonth(String cProjectWarnMonth) {
        this.cProjectWarnMonth = cProjectWarnMonth;
    }
    public String getCProjectWrnday() {
        return this.cProjectWrnday;
    }
    public void setCProjectWrnday(String cProjectWrnday) {
        this.cProjectWrnday = cProjectWrnday;
    }
    public String getCProjectStartTime() {
        return this.cProjectStartTime;
    }
    public void setCProjectStartTime(String cProjectStartTime) {
        this.cProjectStartTime = cProjectStartTime;
    }
    public String getCProjectEndTime() {
        return this.cProjectEndTime;
    }
    public void setCProjectEndTime(String cProjectEndTime) {
        this.cProjectEndTime = cProjectEndTime;
    }
    public String getCProjectWarnTime() {
        return this.cProjectWarnTime;
    }
    public void setCProjectWarnTime(String cProjectWarnTime) {
        this.cProjectWarnTime = cProjectWarnTime;
    }

}
