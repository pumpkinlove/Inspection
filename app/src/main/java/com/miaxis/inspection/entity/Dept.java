package com.miaxis.inspection.entity;

/**
 * Created by xu.nan on 2018/1/3.
 */

public class Dept {

    private long id;
    private String name;
    private String code;
    private String node;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }
}
