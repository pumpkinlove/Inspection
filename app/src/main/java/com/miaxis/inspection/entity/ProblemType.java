package com.miaxis.inspection.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by xu.nan on 2018/2/1.
 */
@Entity
public class ProblemType {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private Long type;
    private String typeName;

    @Generated(hash = 2136819376)
    public ProblemType(Long id, Long type, String typeName) {
        this.id = id;
        this.type = type;
        this.typeName = typeName;
    }

    @Generated(hash = 2034583002)
    public ProblemType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getType() {
        return this.type;
    }

    public void setType(Long type) {
        this.type = type;
    }
}
