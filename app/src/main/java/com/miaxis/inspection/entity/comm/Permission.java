package com.miaxis.inspection.entity.comm;

/**
 * Created by xu.nan on 2018/2/26.
 */

public class Permission {

    private Integer id;
    private String ename;
    private String cname;
    private String projectFormCode;
    private String leaf;
    private String porder;
    private String purl;
    private String description;
    private String icon;
    private String enable;
    private String mtype;
    private String parentEname;
    private String treelevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getProjectFormCode() {
        return projectFormCode;
    }

    public void setProjectFormCode(String projectFormCode) {
        this.projectFormCode = projectFormCode;
    }

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    public String getPorder() {
        return porder;
    }

    public void setPorder(String porder) {
        this.porder = porder;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public String getParentEname() {
        return parentEname;
    }

    public void setParentEname(String parentEname) {
        this.parentEname = parentEname;
    }

    public String getTreelevel() {
        return treelevel;
    }

    public void setTreelevel(String treelevel) {
        this.treelevel = treelevel;
    }
}
