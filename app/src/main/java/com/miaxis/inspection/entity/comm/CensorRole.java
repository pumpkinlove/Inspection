package com.miaxis.inspection.entity.comm;

/**
 * Created by xu.nan on 2018/2/26.
 */

public class CensorRole {

    private String roleName;//检查员角色
    private String opUser;//操作人
    private String opDate;//操作时间
    private String description;//岗位描述

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getOpUser() {
        return opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    public String getOpDate() {
        return opDate;
    }

    public void setOpDate(String opDate) {
        this.opDate = opDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
