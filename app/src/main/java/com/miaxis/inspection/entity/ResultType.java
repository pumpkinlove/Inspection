package com.miaxis.inspection.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 检查结果类型
 * Created by xu.nan on 2018/2/6.
 */
@Entity
public class ResultType {

    @Id(autoincrement = true)
    private Long id;
    private String resultName;
    private boolean isProblem;
    @Generated(hash = 1226106436)
    public ResultType(Long id, String resultName, boolean isProblem) {
        this.id = id;
        this.resultName = resultName;
        this.isProblem = isProblem;
    }
    @Generated(hash = 2133814157)
    public ResultType() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getResultName() {
        return this.resultName;
    }
    public void setResultName(String resultName) {
        this.resultName = resultName;
    }
    public boolean getIsProblem() {
        return this.isProblem;
    }
    public void setIsProblem(boolean isProblem) {
        this.isProblem = isProblem;
    }

}
