package com.miaxis.inspection.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 机构
 * Created by xu.nan on 2018/1/3.
 */
@Entity
public class Organization implements Serializable {

    private static final long serialVersionUID = -8675263714285505708L;

    @Id
    private Long id;
    private String bankcode;
    private String bankno;
    private String bankname;
    private String banktype;
    private String banklevel;
    private String phoneno;
    private String bankaddress;
    private String parentcode;
    private String opuser;
    private String opdate;
    private String remark;
    private String subcount;
    private String opusername;
    private String syscode;
    @Generated(hash = 1269790825)
    public Organization(Long id, String bankcode, String bankno, String bankname,
            String banktype, String banklevel, String phoneno, String bankaddress,
            String parentcode, String opuser, String opdate, String remark,
            String subcount, String opusername, String syscode) {
        this.id = id;
        this.bankcode = bankcode;
        this.bankno = bankno;
        this.bankname = bankname;
        this.banktype = banktype;
        this.banklevel = banklevel;
        this.phoneno = phoneno;
        this.bankaddress = bankaddress;
        this.parentcode = parentcode;
        this.opuser = opuser;
        this.opdate = opdate;
        this.remark = remark;
        this.subcount = subcount;
        this.opusername = opusername;
        this.syscode = syscode;
    }
    @Generated(hash = 27039612)
    public Organization() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBankcode() {
        return this.bankcode;
    }
    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }
    public String getBankno() {
        return this.bankno;
    }
    public void setBankno(String bankno) {
        this.bankno = bankno;
    }
    public String getBankname() {
        return this.bankname;
    }
    public void setBankname(String bankname) {
        this.bankname = bankname;
    }
    public String getBanktype() {
        return this.banktype;
    }
    public void setBanktype(String banktype) {
        this.banktype = banktype;
    }
    public String getBanklevel() {
        return this.banklevel;
    }
    public void setBanklevel(String banklevel) {
        this.banklevel = banklevel;
    }
    public String getPhoneno() {
        return this.phoneno;
    }
    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
    public String getBankaddress() {
        return this.bankaddress;
    }
    public void setBankaddress(String bankaddress) {
        this.bankaddress = bankaddress;
    }
    public String getParentcode() {
        return this.parentcode;
    }
    public void setParentcode(String parentcode) {
        this.parentcode = parentcode;
    }
    public String getOpuser() {
        return this.opuser;
    }
    public void setOpuser(String opuser) {
        this.opuser = opuser;
    }
    public String getOpdate() {
        return this.opdate;
    }
    public void setOpdate(String opdate) {
        this.opdate = opdate;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getSubcount() {
        return this.subcount;
    }
    public void setSubcount(String subcount) {
        this.subcount = subcount;
    }
    public String getOpusername() {
        return this.opusername;
    }
    public void setOpusername(String opusername) {
        this.opusername = opusername;
    }
    public String getSyscode() {
        return this.syscode;
    }
    public void setSyscode(String syscode) {
        this.syscode = syscode;
    }
}
