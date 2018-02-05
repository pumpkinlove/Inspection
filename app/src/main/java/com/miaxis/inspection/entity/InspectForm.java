package com.miaxis.inspection.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 检查表单
 * Created by xu.nan on 2018/1/29.
 */
@Entity
public class InspectForm implements Serializable {

    private static final long serialVersionUID = 3026327254261470986L;

    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String code;
    private String roleName;
    private String roleCode;
    private int type;
    private String typeName;
    private String requirement;
    private int completionRate;

    @Generated(hash = 1617805843)
    public InspectForm(Long id, String name, String code, String roleName,
            String roleCode, int type, String typeName, String requirement,
            int completionRate) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.roleName = roleName;
        this.roleCode = roleCode;
        this.type = type;
        this.typeName = typeName;
        this.requirement = requirement;
        this.completionRate = completionRate;
    }

    @Generated(hash = 1814758225)
    public InspectForm() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public int getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(int completionRate) {
        this.completionRate = completionRate;
    }
}
