package com.miaxis.inspection.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * 检察员
 * 对应服务端的Cencor
 * Created by xu.nan on 2018/1/3.C
 */
@Entity
public class Inspector implements Serializable {

    private static final long serialVersionUID = -3685964985925754110L;

    @Id
    private Long id;
    private String censorName;
    private String censorCode;
    private String bankCode;
    private String roleId;
    private String phoneNo;
    private String idCard;
    private String opDate;
    private String opUser;
    private String opUserName;

    private String finger0;
    private String finger1;
    private String finger2;
    private String finger3;
    private String finger4;
    private String finger5;
    private String finger6;
    private String finger7;
    private String finger8;
    private String finger9;
    @Generated(hash = 1047909453)
    public Inspector(Long id, String censorName, String censorCode, String bankCode,
            String roleId, String phoneNo, String idCard, String opDate,
            String opUser, String opUserName, String finger0, String finger1,
            String finger2, String finger3, String finger4, String finger5,
            String finger6, String finger7, String finger8, String finger9) {
        this.id = id;
        this.censorName = censorName;
        this.censorCode = censorCode;
        this.bankCode = bankCode;
        this.roleId = roleId;
        this.phoneNo = phoneNo;
        this.idCard = idCard;
        this.opDate = opDate;
        this.opUser = opUser;
        this.opUserName = opUserName;
        this.finger0 = finger0;
        this.finger1 = finger1;
        this.finger2 = finger2;
        this.finger3 = finger3;
        this.finger4 = finger4;
        this.finger5 = finger5;
        this.finger6 = finger6;
        this.finger7 = finger7;
        this.finger8 = finger8;
        this.finger9 = finger9;
    }
    @Generated(hash = 370751200)
    public Inspector() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCensorName() {
        return this.censorName;
    }
    public void setCensorName(String censorName) {
        this.censorName = censorName;
    }
    public String getCensorCode() {
        return this.censorCode;
    }
    public void setCensorCode(String censorCode) {
        this.censorCode = censorCode;
    }
    public String getBankCode() {
        return this.bankCode;
    }
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    public String getRoleId() {
        return this.roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getPhoneNo() {
        return this.phoneNo;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public String getIdCard() {
        return this.idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public String getOpDate() {
        return this.opDate;
    }
    public void setOpDate(String opDate) {
        this.opDate = opDate;
    }
    public String getOpUser() {
        return this.opUser;
    }
    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }
    public String getOpUserName() {
        return this.opUserName;
    }
    public void setOpUserName(String opUserName) {
        this.opUserName = opUserName;
    }
    public String getFinger0() {
        return this.finger0;
    }
    public void setFinger0(String finger0) {
        this.finger0 = finger0;
    }
    public String getFinger1() {
        return this.finger1;
    }
    public void setFinger1(String finger1) {
        this.finger1 = finger1;
    }
    public String getFinger2() {
        return this.finger2;
    }
    public void setFinger2(String finger2) {
        this.finger2 = finger2;
    }
    public String getFinger3() {
        return this.finger3;
    }
    public void setFinger3(String finger3) {
        this.finger3 = finger3;
    }
    public String getFinger4() {
        return this.finger4;
    }
    public void setFinger4(String finger4) {
        this.finger4 = finger4;
    }
    public String getFinger5() {
        return this.finger5;
    }
    public void setFinger5(String finger5) {
        this.finger5 = finger5;
    }
    public String getFinger6() {
        return this.finger6;
    }
    public void setFinger6(String finger6) {
        this.finger6 = finger6;
    }
    public String getFinger7() {
        return this.finger7;
    }
    public void setFinger7(String finger7) {
        this.finger7 = finger7;
    }
    public String getFinger8() {
        return this.finger8;
    }
    public void setFinger8(String finger8) {
        this.finger8 = finger8;
    }
    public String getFinger9() {
        return this.finger9;
    }
    public void setFinger9(String finger9) {
        this.finger9 = finger9;
    }


}
