package com.miaxis.inspection.entity.comm;

/**
 * Created by xu.nan on 2018/2/26.
 */

public class Censor {

    private Integer id;                //Id
    private String censorName;         //检查员姓名
    private String censorCode;         //检查员编码
    private String censorStatus;      //检查员状态编号
    private String censorStatusName;   //检查员状态名称
    private Integer bankId;             //机构ID
    private String bankCode;           //机构编码
    private String bankNode;           //机构节点
    private String bankName;           //机构名称
    private Integer roleId;            //角色id
    private String roleName;            //角色名称
    private String phoneNo;            //电话号码
    private String idCard;             //身份证
    private String opDate;              //操作日期
    private String opUser;              //操作用户编码
    private String opUserName;          //操作用户名
    private String remark;              //备注

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCensorName() {
        return censorName;
    }

    public void setCensorName(String censorName) {
        this.censorName = censorName;
    }

    public String getCensorCode() {
        return censorCode;
    }

    public void setCensorCode(String censorCode) {
        this.censorCode = censorCode;
    }

    public String getCensorStatus() {
        return censorStatus;
    }

    public void setCensorStatus(String censorStatus) {
        this.censorStatus = censorStatus;
    }

    public String getCensorStatusName() {
        return censorStatusName;
    }

    public void setCensorStatusName(String censorStatusName) {
        this.censorStatusName = censorStatusName;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankNode() {
        return bankNode;
    }

    public void setBankNode(String bankNode) {
        this.bankNode = bankNode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
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

    public String getOpUserName() {
        return opUserName;
    }

    public void setOpUserName(String opUserName) {
        this.opUserName = opUserName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
