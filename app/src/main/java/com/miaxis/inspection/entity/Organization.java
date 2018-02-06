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
    private String name;
    private String code;
    private String node;

    @Generated(hash = 1856813228)
    public Organization(Long id, String name, String code, String node) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.node = node;
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
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getNode() {
        return this.node;
    }
    public void setNode(String node) {
        this.node = node;
    }

}
