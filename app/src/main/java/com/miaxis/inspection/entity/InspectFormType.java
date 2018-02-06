package com.miaxis.inspection.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by xu.nan on 2018/2/5.
 */
@Entity
public class InspectFormType {

    private Long type;
    private String typeName;
    @Generated(hash = 167567442)
    public InspectFormType(Long type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }
    @Generated(hash = 1086949282)
    public InspectFormType() {
    }
    public Long getType() {
        return this.type;
    }
    public void setType(Long type) {
        this.type = type;
    }
    public String getTypeName() {
        return this.typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
