package com.miaxis.inspection.entity.comm;

/**
 * Created by Administrator on 2018/3/4 0004.
 */

public class CheckPointLog {

    private Long id;                       //日志ID
    private String cPointLogCode;             //检查项日志编号
    private String cPointCode;                //检查点编码
    private String cPointName;                //检查点名称
    private String projectCode;               //检查项编码
    private String projectFormCode;           //检查清单编码
    private String censorName;                //检查员名称
    private String censorCode;                //检查员编码
    private String bankCode;                  //银行编码
    private String bankNode;                  //银行节点
    private String bankName;                  //银行名称
    private String bankId;                    //银行ID
    private String opDate;                    //日志记录时间
    private String status;                    //状态(0.正常,1.异常)


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getcPointLogCode() {
        return cPointLogCode;
    }

    public void setcPointLogCode(String cPointLogCode) {
        this.cPointLogCode = cPointLogCode;
    }

    public String getcPointCode() {
        return cPointCode;
    }

    public void setcPointCode(String cPointCode) {
        this.cPointCode = cPointCode;
    }

    public String getcPointName() {
        return cPointName;
    }

    public void setcPointName(String cPointName) {
        this.cPointName = cPointName;
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

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getOpDate() {
        return opDate;
    }

    public void setOpDate(String opDate) {
        this.opDate = opDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
