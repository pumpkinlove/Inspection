package com.miaxis.inspection.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.miaxis.inspection.model.local.greenDao.gen.DaoSession;
import com.miaxis.inspection.model.local.greenDao.gen.InspectItemDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectContentDao;

/**
 * 检查内容
 * Created by xu.nan on 2018/1/3.
 */
@Entity
public class InspectContent implements Serializable {

    private static final long serialVersionUID = -1561898683354745508L;

    @Id
    private Long id;                    //id
    private String name;                //检查内容名称

    private Long inspectItemId;         //所属检查项 id
    @ToOne(joinProperty = "inspectItemId")
    private InspectItem inspectItem;    //所属检查项

    private int resultType;             // 1 正常异常 2 损坏未损坏 3 清理未清理

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1747847796)
    private transient InspectContentDao myDao;

    @Generated(hash = 1218374517)
    public InspectContent(Long id, String name, Long inspectItemId,
            int resultType) {
        this.id = id;
        this.name = name;
        this.inspectItemId = inspectItemId;
        this.resultType = resultType;
    }

    @Generated(hash = 515515340)
    public InspectContent() {
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

    public Long getInspectItemId() {
        return this.inspectItemId;
    }

    public void setInspectItemId(Long inspectItemId) {
        this.inspectItemId = inspectItemId;
    }

    public int getResultType() {
        return this.resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
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
    @Generated(hash = 231353234)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getInspectContentDao() : null;
    }


}
