package com.miaxis.inspection.entity.comm;

/**
 * Created by xu.nan on 2018/2/27.
 */

public class CheckProjectContent {
    private Long id;
    private String cProjectName;
    private String cProjectContent;
    private String cProjectStatus;
    private Integer level;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getcProjectStatus() {
        return cProjectStatus;
    }

    public void setcProjectStatus(String cProjectStatus) {
        this.cProjectStatus = cProjectStatus;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
