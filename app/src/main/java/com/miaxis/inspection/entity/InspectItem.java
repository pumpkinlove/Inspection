package com.miaxis.inspection.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.miaxis.inspection.dao.gen.DaoSession;
import com.miaxis.inspection.dao.gen.ExcuteTimeDao;
import com.miaxis.inspection.dao.gen.InspectItemDao;
import com.miaxis.inspection.dao.gen.InspectFormDao;

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

    private Long formId;            //所属表单id
    @ToOne(joinProperty = "formId")
    private InspectForm inspectForm;

    private Long itemId;            //所属项目id
    @ToOne(joinProperty = "itemId")
    private InspectItem inspectItem;

    private int count;
    private int type;
    @ToMany(referencedJoinProperty = "id")
    private List<ExcuteTime> timeList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 624423172)
    private transient InspectItemDao myDao;
    @Generated(hash = 672043110)
    public InspectItem(Long id, String name, Long formId, Long itemId, int count,
            int type) {
        this.id = id;
        this.name = name;
        this.formId = formId;
        this.itemId = itemId;
        this.count = count;
        this.type = type;
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
    public Long getFormId() {
        return this.formId;
    }
    public void setFormId(Long formId) {
        this.formId = formId;
    }
    public Long getItemId() {
        return this.itemId;
    }
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    public int getCount() {
        return this.count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    @Generated(hash = 1912765686)
    private transient Long inspectForm__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1515207477)
    public InspectForm getInspectForm() {
        Long __key = this.formId;
        if (inspectForm__resolvedKey == null
                || !inspectForm__resolvedKey.equals(__key)) {
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
    @Generated(hash = 953842671)
    public void setInspectForm(InspectForm inspectForm) {
        synchronized (this) {
            this.inspectForm = inspectForm;
            formId = inspectForm == null ? null : inspectForm.getId();
            inspectForm__resolvedKey = formId;
        }
    }
    @Generated(hash = 1166922348)
    private transient Long inspectItem__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 818146559)
    public InspectItem getInspectItem() {
        Long __key = this.itemId;
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
    @Generated(hash = 989801292)
    public void setInspectItem(InspectItem inspectItem) {
        synchronized (this) {
            this.inspectItem = inspectItem;
            itemId = inspectItem == null ? null : inspectItem.getId();
            inspectItem__resolvedKey = itemId;
        }
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 732464161)
    public List<ExcuteTime> getTimeList() {
        if (timeList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ExcuteTimeDao targetDao = daoSession.getExcuteTimeDao();
            List<ExcuteTime> timeListNew = targetDao._queryInspectItem_TimeList(id);
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

}
