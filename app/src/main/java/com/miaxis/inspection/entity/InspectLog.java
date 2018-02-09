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
import com.miaxis.inspection.dao.gen.DaoSession;
import com.miaxis.inspection.dao.gen.InspectContentLogDao;
import com.miaxis.inspection.dao.gen.InspectPointDao;
import com.miaxis.inspection.dao.gen.InspectLogDao;
import com.miaxis.inspection.dao.gen.TaskDao;
import com.miaxis.inspection.dao.gen.InspectItemDao;

/**
 * 历史检查记录
 * Created by xu.nan on 2018/1/29.
 */
@Entity
public class InspectLog implements Serializable {

    private static final long serialVersionUID = -1999623990756333415L;

    @Id(autoincrement = true)
    private Long id;

    private Long taskId;
    @ToOne(joinProperty = "taskId")
    private Task task;

    private Long inspectItemId;
    @ToOne(joinProperty = "inspectItemId")
    private InspectItem inspectItem;

    private Date opDate;
    private String opInspectorName;
    private String result;

    private boolean uploaded;
    private boolean inspected;

    private Long inspectPointId;
    @ToOne(joinProperty = "inspectPointId")
    private InspectPoint inspectPoint;

    @ToMany(referencedJoinProperty = "inspectLogId")
    private List<InspectContentLog> contentList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 2012460397)
    private transient InspectLogDao myDao;

    @Generated(hash = 100676365)
    private transient Long task__resolvedKey;

    @Generated(hash = 101199454)
    private transient Long inspectPoint__resolvedKey;

    @Generated(hash = 1166922348)
    private transient Long inspectItem__resolvedKey;

    @Generated(hash = 1184517621)
    public InspectLog(Long id, Long taskId, Long inspectItemId, Date opDate, String opInspectorName,
            String result, boolean uploaded, boolean inspected, Long inspectPointId) {
        this.id = id;
        this.taskId = taskId;
        this.inspectItemId = inspectItemId;
        this.opDate = opDate;
        this.opInspectorName = opInspectorName;
        this.result = result;
        this.uploaded = uploaded;
        this.inspected = inspected;
        this.inspectPointId = inspectPointId;
    }

    @Generated(hash = 7584571)
    public InspectLog() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOpDate() {
        return this.opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }

    public String getOpInspectorName() {
        return this.opInspectorName;
    }

    public void setOpInspectorName(String opInspectorName) {
        this.opInspectorName = opInspectorName;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getInspectPointId() {
        return this.inspectPointId;
    }

    public void setInspectPointId(Long inspectPointId) {
        this.inspectPointId = inspectPointId;
    }

    public Long getTaskId() {
        return this.taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public boolean getUploaded() {
        return this.uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public boolean getInspected() {
        return this.inspected;
    }

    public void setInspected(boolean inspected) {
        this.inspected = inspected;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 410192089)
    public Task getTask() {
        Long __key = this.taskId;
        if (task__resolvedKey == null || !task__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TaskDao targetDao = daoSession.getTaskDao();
            Task taskNew = targetDao.load(__key);
            synchronized (this) {
                task = taskNew;
                task__resolvedKey = __key;
            }
        }
        return task;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 711336367)
    public void setTask(Task task) {
        synchronized (this) {
            this.task = task;
            taskId = task == null ? null : task.getId();
            task__resolvedKey = taskId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 619315470)
    public InspectPoint getInspectPoint() {
        Long __key = this.inspectPointId;
        if (inspectPoint__resolvedKey == null || !inspectPoint__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            InspectPointDao targetDao = daoSession.getInspectPointDao();
            InspectPoint inspectPointNew = targetDao.load(__key);
            synchronized (this) {
                inspectPoint = inspectPointNew;
                inspectPoint__resolvedKey = __key;
            }
        }
        return inspectPoint;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1673863055)
    public void setInspectPoint(InspectPoint inspectPoint) {
        synchronized (this) {
            this.inspectPoint = inspectPoint;
            inspectPointId = inspectPoint == null ? null : inspectPoint.getId();
            inspectPoint__resolvedKey = inspectPointId;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 118991638)
    public List<InspectContentLog> getContentList() {
        if (contentList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            InspectContentLogDao targetDao = daoSession.getInspectContentLogDao();
            List<InspectContentLog> contentListNew = targetDao._queryInspectLog_ContentList(id);
            synchronized (this) {
                if (contentList == null) {
                    contentList = contentListNew;
                }
            }
        }
        return contentList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 233512463)
    public synchronized void resetContentList() {
        contentList = null;
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
    @Generated(hash = 1734926653)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getInspectLogDao() : null;
    }

    public Long getInspectItemId() {
        return this.inspectItemId;
    }

    public void setInspectItemId(Long inspectItemId) {
        this.inspectItemId = inspectItemId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1160851564)
    public InspectItem getInspectItem() {
        Long __key = this.inspectItemId;
        if (inspectItem__resolvedKey == null || !inspectItem__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            InspectItemDao targetDao = daoSession.getInspectItemDao();
            InspectItem inspectItemNew = targetDao.load(__key);
            synchronized (this) {
                inspectItem = inspectItemNew;
                inspectItem__resolvedKey = __key;
            }
        }
        return inspectItem;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1759562310)
    public void setInspectItem(InspectItem inspectItem) {
        synchronized (this) {
            this.inspectItem = inspectItem;
            inspectItemId = inspectItem == null ? null : inspectItem.getId();
            inspectItem__resolvedKey = inspectItemId;
        }
    }


}
