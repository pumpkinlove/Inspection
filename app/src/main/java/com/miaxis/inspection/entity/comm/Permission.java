package com.miaxis.inspection.entity.comm;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by xu.nan on 2018/2/26.
 */
@Entity
public class Permission {
    @Id
    private Long id;
    private String ename;
    private String cname;
    private String projectFormCode;
    private String inspectorCode;
    @Generated(hash = 643757416)
    public Permission(Long id, String ename, String cname, String projectFormCode,
            String inspectorCode) {
        this.id = id;
        this.ename = ename;
        this.cname = cname;
        this.projectFormCode = projectFormCode;
        this.inspectorCode = inspectorCode;
    }
    @Generated(hash = 600656733)
    public Permission() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEname() {
        return this.ename;
    }
    public void setEname(String ename) {
        this.ename = ename;
    }
    public String getCname() {
        return this.cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }
    public String getProjectFormCode() {
        return this.projectFormCode;
    }
    public void setProjectFormCode(String projectFormCode) {
        this.projectFormCode = projectFormCode;
    }
    public String getInspectorCode() {
        return this.inspectorCode;
    }
    public void setInspectorCode(String inspectorCode) {
        this.inspectorCode = inspectorCode;
    }

}
