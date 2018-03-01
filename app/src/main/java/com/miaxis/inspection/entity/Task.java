package com.miaxis.inspection.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.miaxis.inspection.model.local.greenDao.gen.DaoSession;
import com.miaxis.inspection.model.local.greenDao.gen.ExecuteTimeDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectFormDao;
import com.miaxis.inspection.model.local.greenDao.gen.TaskDao;

/**
 * 检查任务
 * Created by xu.nan on 2018/1/3.
 */
@Entity
public class Task implements Serializable {

    private static final long serialVersionUID = 8625953958144822983L;

    @Id
    private Long id;
    private String taskName;        //任务名称
    private String taskCode;        //任务编号

    private int circleType;         //重复类型  1 次/日 2 次/周 3 次/月 4 次/季 5 次/年
    private String circleTypeName;

    private int status;             // 0 待执行 1 执行中 2 已完成  区别于服务端任务状态 1 未启动 2 已启动 3 重复
    private String statusName;

    private Long inspectFormId;
    @ToOne(joinProperty = "inspectFormId")
    private InspectForm inspectForm;

    @ToMany(referencedJoinProperty = "taskId")
    private List<ExecuteTime> excuteTimes;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1469429066)
    private transient TaskDao myDao;

    @Generated(hash = 1179177462)
    public Task(Long id, String taskName, String taskCode, int circleType,
            String circleTypeName, int status, String statusName, Long inspectFormId) {
        this.id = id;
        this.taskName = taskName;
        this.taskCode = taskCode;
        this.circleType = circleType;
        this.circleTypeName = circleTypeName;
        this.status = status;
        this.statusName = statusName;
        this.inspectFormId = inspectFormId;
    }

    @Generated(hash = 733837707)
    public Task() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public int getCircleType() {
        return this.circleType;
    }

    public void setCircleType(int circleType) {
        this.circleType = circleType;
    }

    public String getCircleTypeName() {
        return this.circleTypeName;
    }

    public void setCircleTypeName(String circleTypeName) {
        this.circleTypeName = circleTypeName;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusName() {
        return this.statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getInspectFormId() {
        return this.inspectFormId;
    }

    public void setInspectFormId(Long inspectFormId) {
        this.inspectFormId = inspectFormId;
    }

    @Generated(hash = 1912765686)
    private transient Long inspectForm__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1950508874)
    public InspectForm getInspectForm() {
        Long __key = this.inspectFormId;
        if (inspectForm__resolvedKey == null || !inspectForm__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            InspectFormDao targetDao = daoSession.getInspectFormDao();
            InspectForm inspectFormNew = targetDao.load(__key);
            synchronized (this) {
                inspectForm = inspectFormNew;
                inspectForm__resolvedKey = __key;
            }
        }
        return inspectForm;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1765005669)
    public void setInspectForm(InspectForm inspectForm) {
        synchronized (this) {
            this.inspectForm = inspectForm;
            inspectFormId = inspectForm == null ? null : inspectForm.getId();
            inspectForm__resolvedKey = inspectFormId;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1698233468)
    public List<ExecuteTime> getExcuteTimes() {
        if (excuteTimes == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ExecuteTimeDao targetDao = daoSession.getExecuteTimeDao();
            List<ExecuteTime> excuteTimesNew = targetDao._queryTask_ExcuteTimes(id);
            synchronized (this) {
                if (excuteTimes == null) {
                    excuteTimes = excuteTimesNew;
                }
            }
        }
        return excuteTimes;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1585256792)
    public synchronized void resetExcuteTimes() {
        excuteTimes = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1442741304)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTaskDao() : null;
    }




}
