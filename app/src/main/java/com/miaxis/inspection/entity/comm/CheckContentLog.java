package com.miaxis.inspection.entity.comm;

/**
 * Created by xu.nan on 2018/3/2.
 */

public class CheckContentLog {
    private Integer id;                //检查内容日志Id
    private String cPointLogCode;       //检查项日志编号
    private String projectCode;        //检查项编码
    private String projectFormCode;    //检查表单编码
    private String projectContent;     //检查内容
    private Integer status;            //检查状态
    private String result;
    private Integer errorType;          //问题类型
    private String description;        //问题描述
    private String picture0Url;       //图片地址1
    private String picture1Url;       //图片地址2
    private String picture2Url;       //图片地址3
    private String picture3Url;       //图片地址4
    private String picture4Url;       //图片地址5
    private String videoUrl;           //视频地址
    private String voiceUrl;           //语音地址
    private String opDate;             //检查时间
    private String opUser;             //检查用户

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getcPointLogCode() {
        return cPointLogCode;
    }

    public void setcPointLogCode(String cPointLogCode) {
        this.cPointLogCode = cPointLogCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectFormCode() {
        return projectFormCode;
    }

    public void setProjectFormCode(String projectFormCode) {
        this.projectFormCode = projectFormCode;
    }

    public String getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture0Url() {
        return picture0Url;
    }

    public void setPicture0Url(String picture0Url) {
        this.picture0Url = picture0Url;
    }

    public String getPicture1Url() {
        return picture1Url;
    }

    public void setPicture1Url(String picture1Url) {
        this.picture1Url = picture1Url;
    }

    public String getPicture2Url() {
        return picture2Url;
    }

    public void setPicture2Url(String picture2Url) {
        this.picture2Url = picture2Url;
    }

    public String getPicture3Url() {
        return picture3Url;
    }

    public void setPicture3Url(String picture3Url) {
        this.picture3Url = picture3Url;
    }

    public String getPicture4Url() {
        return picture4Url;
    }

    public void setPicture4Url(String picture4Url) {
        this.picture4Url = picture4Url;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public String getOpDate() {
        return opDate;
    }

    public void setOpDate(String opDate) {
        this.opDate = opDate;
    }

    public String getOpUser() {
        return opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    public Integer getErrorType() {
        return errorType;
    }

    public void setErrorType(Integer errorType) {
        this.errorType = errorType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
