package com.miaxis.inspection.entity.comm;

import com.miaxis.inspection.entity.Organization;

import java.util.List;

public class Task {
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;
	private String taskCode;          //任务编号
	private String taskName;          //任务名
	private Integer taskTimesType;    //任务执行日期（每日，每周，每月，每季度，每年）
	private String taskTimesName;     //任务执行日期名
	private String taskRole;          //任务执行角色
	private String taskProjectCode;   //任务关联的项目清单码
	private CheckProject project;     //任务对象
	private Integer status;           //状态
	private List<TaskTime> taskTime;      //任务时间
	private List<Organization> bank;         //任务银行
	private String taskWarnRate;      //任务提醒频次
	private String taskWarnRateType;  //任务提醒频次类型(分钟/次,小时/次,天/次)
	private String taskWarnTime;      //任务提醒时间(提前1-3天，当天)
	private String statusName;        //状态名（任务是否启用）
	private Integer excuteStatus;     //执行状态(重复、永不重复)
	private String excuteStatusName;  //执行状态名
	private String description;            //备注
	private String opDate;
	private String opUser;
	private String opUserName;
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Integer getTaskTimesType() {
		return taskTimesType;
	}
	public void setTaskTimesType(Integer taskTimesType) {
		this.taskTimesType = taskTimesType;
	}
	public String getTaskTimesName() {
		return taskTimesName;
	}
	public void setTaskTimesName(String taskTimesName) {
		this.taskTimesName = taskTimesName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public Integer getExcuteStatus() {
		return excuteStatus;
	}
	public void setExcuteStatus(Integer excuteStatus) {
		this.excuteStatus = excuteStatus;
	}
	public String getExcuteStatusName() {
		return excuteStatusName;
	}
	public void setExcuteStatusName(String excuteStatusName) {
		this.excuteStatusName = excuteStatusName;
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
	public String getTaskRole() {
		return taskRole;
	}
	public void setTaskRole(String taskRole) {
		this.taskRole = taskRole;
	}
	public String getTaskProjectCode() {
		return taskProjectCode;
	}
	public void setTaskProjectCode(String taskProjectCode) {
		this.taskProjectCode = taskProjectCode;
	}
	public List<TaskTime> getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(List<TaskTime> taskTime) {
		this.taskTime = taskTime;
	}
	public CheckProject getProject() {
		return project;
	}
	public void setProject(CheckProject project) {
		this.project = project;
	}
	public String getDescription() {
		return description;
	}
	public void Description(String description) {
		this.description = description;
	}
	public String getTaskWarnRate() {
		return taskWarnRate;
	}
	public void setTaskWarnRate(String taskWarnRate) {
		this.taskWarnRate = taskWarnRate;
	}
	public String getTaskWarnRateType() {
		return taskWarnRateType;
	}
	public void setTaskWarnRateType(String taskWarnRateType) {
		this.taskWarnRateType = taskWarnRateType;
	}
	public String getTaskWarnTime() {
		return taskWarnTime;
	}
	public void setTaskWarnTime(String taskWarnTime) {
		this.taskWarnTime = taskWarnTime;
	}
	public List<Organization> getBank() {
		return bank;
	}
	public void setBank(List<Organization> bank) {
		this.bank = bank;
	}
}

