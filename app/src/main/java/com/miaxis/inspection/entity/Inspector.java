package com.miaxis.inspection.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * 检察员
 * Created by xu.nan on 2018/1/3.
 */
@Entity
public class Inspector implements Serializable {

    private static final long serialVersionUID = -3685964985925754110L;

    @Id
    private Long id;
    private String name;
    private String code;
    @Generated(hash = 311462438)
    public Inspector(Long id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }
    @Generated(hash = 370751200)
    public Inspector() {
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

}
