package com.miaxis.inspection.entity;

/**
 * Created by xu.nan on 2018/2/1.
 */

public class ProblemType {

    private Long id;

    private int type;
    private String typeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
