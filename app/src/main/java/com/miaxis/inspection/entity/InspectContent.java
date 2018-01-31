package com.miaxis.inspection.entity;

import java.util.List;

/**
 * 检查内容
 * Created by xu.nan on 2018/1/3.
 */
public class InspectContent {

    private Long id;
    private String name;
    private InspectItem inspectItem;    //所属检查项
    private String roleName;

    private String result;              //检查结果
    private String description;         //问题描述
    private List<String> picUrls;       //照片路径
    private List<byte[]> picDataList;   //照片数据

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InspectItem getInspectItem() {
        return inspectItem;
    }

    public void setInspectItem(InspectItem inspectItem) {
        this.inspectItem = inspectItem;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(List<String> picUrls) {
        this.picUrls = picUrls;
    }

    public List<byte[]> getPicDataList() {
        return picDataList;
    }

    public void setPicDataList(List<byte[]> picDataList) {
        this.picDataList = picDataList;
    }
}
