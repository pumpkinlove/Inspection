package com.miaxis.inspection.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.miaxis.inspection.model.local.greenDao.gen.DaoSession;
import com.miaxis.inspection.model.local.greenDao.gen.InspectContentDao;
import com.miaxis.inspection.model.local.greenDao.gen.ExecuteTimeDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectItemDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectFormDao;

/**
 * 检查项
 * Created by xu.nan on 2018/1/3.
 */
@Entity
public class InspectItem implements Serializable {

    private static final long serialVersionUID = -6132873465831158607L;

    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String code;

    private String inspectFormCode;            //所属表单id

    private Long inspectFormId;
    @ToOne(joinProperty = "inspectFormId")
    private InspectForm inspectForm;

    private Long inspectItemId;            //所属项目id
    @ToOne(joinProperty = "inspectItemId")
    private InspectItem inspectItem;

    @ToMany(referencedJoinProperty = "inspectItemId")
    private List<ExecuteTime> timeList;

    @ToMany(referencedJoinProperty = "inspectItemId")
    private List<InspectContent> inspectContentList;

    private int count;
    private String frequencyType;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 624423172)
    private transient InspectItemDao myDao;
    @Generated(hash = 1674190670)
    public InspectItem(Long id, String name, String code, String inspectFormCode, Long inspectFormId,
            Long inspectItemId, int count, String frequencyType) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.inspectFormCode = inspectFormCode;
        this.inspectFormId = inspectFormId;
        this.inspectItemId = inspectItemId;
        this.count = count;
        this.frequencyType = frequencyType;
    }
    @Generated(hash = 71777420)
    public InspectItem() {
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
    public String getInspectFormCode() {
        return this.inspectFormCode;
    }
    public void setInspectFormCode(String inspectFormCode) {
        this.inspectFormCode = inspectFormCode;
    }
    public Long getInspectItemId() {
        return this.inspectItemId;
    }
    public void setInspectItemId(Long inspectItemId) {
        this.inspectItemId = inspectItemId;
    }
    public int getCount() {
        return this.count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getFrequencyType() {
        return this.frequencyType;
    }
    public void setFrequencyType(String frequencyType) {
        this.frequencyType = frequencyType;
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
    @Generated(hash = 1166922348)
    private transient Long inspectItem__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1160851564)
    public InspectItem getInspectItem() {
        Long __key = this.inspectItemId;
        if (inspectItem__resolvedKey == null
                || !inspectItem__resolvedKey.equals(__key)) {
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
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 958694251)
    public List<ExecuteTime> getTimeList() {
        if (timeList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ExecuteTimeDao targetDao = daoSession.getExecuteTimeDao();
            List<ExecuteTime> timeListNew = targetDao
                    ._queryInspectItem_TimeList(id);
            synchronized (this) {
                if (timeList == null) {
                    timeList = timeListNew;
                }
            }
        }
        return timeList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 495182591)
    public synchronized void resetTimeList() {
        timeList = null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 903090465)
    public List<InspectContent> getInspectContentList() {
        if (inspectContentList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            InspectContentDao targetDao = daoSession.getInspectContentDao();
            List<InspectContent> inspectContentListNew = targetDao
                    ._queryInspectItem_InspectContentList(id);
            synchronized (this) {
                if (inspectContentList == null) {
                    inspectContentList = inspectContentListNew;
                }
            }
        }
        return inspectContentList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1073990061)
    public synchronized void resetInspectContentList() {
        inspectContentList = null;
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
    @Generated(hash = 79688771)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getInspectItemDao() : null;
    }
    public Long getInspectFormId() {
        return this.inspectFormId;
    }
    public void setInspectFormId(Long inspectFormId) {
        this.inspectFormId = inspectFormId;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }



}
