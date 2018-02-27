package com.miaxis.inspection.entity.comm;

import java.util.List;

public class CheckProject {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String cProjectCode;          //检查项代码
	private String cProjectNode; 		  //检查项节点
	private Integer cProjectType;         //检查项类型
	private String cProjectTypeName;	  //检查项类型名称
	private String cProjectRole;          //检查角色
	private String cProjectName; 		  //检查项名称
	private String cProjectContent;       //检查项内容
	private Integer cProjectTimes;   	  //检查次数
	private List<CheckProjectTime> cProjectTime;      //检查时间
	private Integer cProjectTimesType;	  //检查次数类型(次/天,次/月)
	private String cProjectTimesTypeName; //检查次数名称
	private Integer cProjectStatus;		  //检查状态
	private String cProjectStatusName;    //检查状态名称
	private String level; 				  //检查项级别
	private String parentCode;			  //上级检查项
	private Integer cProjectComplete;     //任务完成度
	private String cProjectCompleteName;  //任务完成度名称
	private Integer subCount;		      //子检查项个数
	private String opUser;				  //操作员编码
	private String opUserName;			  //操作员名
	private String opDate;				  //操作时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getcProjectCode() {
		return cProjectCode;
	}
	public void setcProjectCode(String cProjectCode) {
		this.cProjectCode = cProjectCode;
	}
	public String getcProjectNode() {
		return cProjectNode;
	}
	public void setcProjectNode(String cProjectNode) {
		this.cProjectNode = cProjectNode;
	}
	public Integer getcProjectType() {
		return cProjectType;
	}
	public void setcProjectType(Integer cProjectType) {
		this.cProjectType = cProjectType;
	}
	public String getcProjectRole() {
		return cProjectRole;
	}
	public void setcProjectRole(String cProjectRole) {
		this.cProjectRole = cProjectRole;
	}
	public String getcProjectName() {
		return cProjectName;
	}
	public void setcProjectName(String cProjectName) {
		this.cProjectName = cProjectName;
	}
	public String getcProjectContent() {
		return cProjectContent;
	}
	public void setcProjectContent(String cProjectContent) {
		this.cProjectContent = cProjectContent;
	}
	public Integer getcProjectTimes() {
		return cProjectTimes;
	}
	public void setcProjectTimes(Integer cProjectTimes) {
		this.cProjectTimes = cProjectTimes;
	}
	public Integer getcProjectTimesType() {
		return cProjectTimesType;
	}
	public void setcProjectTimesType(Integer cProjectTimesType) {
		this.cProjectTimesType = cProjectTimesType;
	}
	public Integer getcProjectStatus() {
		return cProjectStatus;
	}
	public void setcProjectStatus(Integer cProjectStatus) {
		this.cProjectStatus = cProjectStatus;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public Integer getSubCount() {
		return subCount;
	}
	public void setSubCount(Integer subCount) {
		this.subCount = subCount;
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
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getcProjectTypeName() {
		return cProjectTypeName;
	}
	public void setcProjectTypeName(String cProjectTypeName) {
		this.cProjectTypeName = cProjectTypeName;
	}
	public String getcProjectStatusName() {
		return cProjectStatusName;
	}
	public void setcProjectStatusName(String cProjectStatusName) {
		this.cProjectStatusName = cProjectStatusName;
	}
	public List<CheckProjectTime> getcProjectTime() {
		return cProjectTime;
	}
	public void setcProjectTime(List<CheckProjectTime> cProjectTime) {
		this.cProjectTime = cProjectTime;
	}
	public Integer getcProjectComplete() {
		return cProjectComplete;
	}
	public void setcProjectComplete(Integer cProjectComplete) {
		this.cProjectComplete = cProjectComplete;
	}
	public String getcProjectTimesTypeName() {
		return cProjectTimesTypeName;
	}
	public void setcProjectTimesTypeName(String cProjectTimesTypeName) {
		this.cProjectTimesTypeName = cProjectTimesTypeName;
	}
	public String getcProjectCompleteName() {
		return cProjectCompleteName;
	}
	public void setcProjectCompleteName(String cProjectCompleteName) {
		this.cProjectCompleteName = cProjectCompleteName;
	}

}

