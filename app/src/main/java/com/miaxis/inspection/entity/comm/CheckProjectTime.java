package com.miaxis.inspection.entity.comm;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by xu.nan on 2018/2/26.
 */
@Entity
public class CheckProjectTime {

    private String cProjectCode;
    private Integer cProjectTimesType;
    private String cProjectwarnWeekDay;
    private String cProjectwarnMonth;
    private String cProjectwarnday;
    private String cProjectstartTime;
    private String cProjectendTime;
    private String cProjectwarnTime;
    @Generated(hash = 1496780006)
    public CheckProjectTime(String cProjectCode, Integer cProjectTimesType,
            String cProjectwarnWeekDay, String cProjectwarnMonth,
            String cProjectwarnday, String cProjectstartTime,
            String cProjectendTime, String cProjectwarnTime) {
        this.cProjectCode = cProjectCode;
        this.cProjectTimesType = cProjectTimesType;
        this.cProjectwarnWeekDay = cProjectwarnWeekDay;
        this.cProjectwarnMonth = cProjectwarnMonth;
        this.cProjectwarnday = cProjectwarnday;
        this.cProjectstartTime = cProjectstartTime;
        this.cProjectendTime = cProjectendTime;
        this.cProjectwarnTime = cProjectwarnTime;
    }
    @Generated(hash = 1159443729)
    public CheckProjectTime() {
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
    public String getCProjectwarnWeekDay() {
        return this.cProjectwarnWeekDay;
    }
    public void setCProjectwarnWeekDay(String cProjectwarnWeekDay) {
        this.cProjectwarnWeekDay = cProjectwarnWeekDay;
    }
    public String getCProjectwarnMonth() {
        return this.cProjectwarnMonth;
    }
    public void setCProjectwarnMonth(String cProjectwarnMonth) {
        this.cProjectwarnMonth = cProjectwarnMonth;
    }
    public String getCProjectwarnday() {
        return this.cProjectwarnday;
    }
    public void setCProjectwarnday(String cProjectwarnday) {
        this.cProjectwarnday = cProjectwarnday;
    }
    public String getCProjectstartTime() {
        return this.cProjectstartTime;
    }
    public void setCProjectstartTime(String cProjectstartTime) {
        this.cProjectstartTime = cProjectstartTime;
    }
    public String getCProjectendTime() {
        return this.cProjectendTime;
    }
    public void setCProjectendTime(String cProjectendTime) {
        this.cProjectendTime = cProjectendTime;
    }
    public String getCProjectwarnTime() {
        return this.cProjectwarnTime;
    }
    public void setCProjectwarnTime(String cProjectwarnTime) {
        this.cProjectwarnTime = cProjectwarnTime;
    }

}
