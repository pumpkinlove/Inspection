package com.miaxis.inspection.entity.comm;

public class CheckPoint {

	private Long id;
	private String cpCode;              //检查点编码
	private String cpName;				//检查点名称
	private Integer cpType;				//检查点类型
	private String cProjectCode;        //检查项编码
	private CheckProject project;       //检查项
	private String cpRfid;				//检查点Rfid
	private String cpCreateDate;		//检查创建日期
	private Integer bankId;             //检查点银行Id
	private String bankCode;			//检查点银行
	private String bankNode;			//检查点银行节点
	private String bankName;            //所属银行名称
	private String opUser; 				//操作员编码
	private String opUserName;			//操作员名
	private String opDate;				//操作时间
	private String remark;				//备注
	private Integer isBind;             //是否绑定(0：否，1:是)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpCode() {
		return cpCode;
	}
	public void setCpCode(String cpCode) {
		this.cpCode = cpCode;
	}
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	public Integer getCpType() {
		return cpType;
	}
	public void setCpType(Integer cpType) {
		this.cpType = cpType;
	}
	public String getCpRfid() {
		return cpRfid;
	}
	public void setCpRfid(String cpRfid) {
		this.cpRfid = cpRfid;
	}
	public String getCpCreateDate() {
		return cpCreateDate;
	}
	public void setCpCreateDate(String cpCreateDate) {
		this.cpCreateDate = cpCreateDate;
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
	public String getOpDate() {
		return opDate;
	}
	public void setOpDate(String opDate) {
		this.opDate = opDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public CheckProject getProject() {
		return project;
	}
	public void setProject(CheckProject project) {
		this.project = project;
	}
	public Integer getBankId() {
		return bankId;
	}
	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}
	public String getcProjectCode() {
		return cProjectCode;
	}
	public void setcProjectCode(String cProjectCode) {
		this.cProjectCode = cProjectCode;
	}
	public Integer getIsBind() {
		return isBind;
	}
	public void setIsBind(Integer isBind) {
		this.isBind = isBind;
	}

}

