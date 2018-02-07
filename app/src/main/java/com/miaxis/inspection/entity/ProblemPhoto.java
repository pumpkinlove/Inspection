package com.miaxis.inspection.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by xu.nan on 2018/2/5.
 */
@Entity
public class ProblemPhoto {

    @Id(autoincrement = true)
    private Long id;

    private Long contentLogId;
    private String picUrl;
    private byte[] picData;

    @Generated(hash = 104425425)
    public ProblemPhoto(Long id, Long contentLogId, String picUrl, byte[] picData) {
        this.id = id;
        this.contentLogId = contentLogId;
        this.picUrl = picUrl;
        this.picData = picData;
    }
    @Generated(hash = 501364626)
    public ProblemPhoto() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getContentLogId() {
        return this.contentLogId;
    }
    public void setContentLogId(Long contentLogId) {
        this.contentLogId = contentLogId;
    }
    public String getPicUrl() {
        return this.picUrl;
    }
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
    public byte[] getPicData() {
        return this.picData;
    }
    public void setPicData(byte[] picData) {
        this.picData = picData;
    }

}
