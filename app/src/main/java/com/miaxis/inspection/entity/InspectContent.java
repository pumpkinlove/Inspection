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
import com.miaxis.inspection.dao.gen.ProblemPhotoDao;
import com.miaxis.inspection.dao.gen.ProblemTypeDao;
import com.miaxis.inspection.dao.gen.InspectItemDao;
import com.miaxis.inspection.dao.gen.InspectContentDao;

/**
 * 检查内容
 * Created by xu.nan on 2018/1/3.
 */
@Entity
public class InspectContent implements Serializable {

    private static final long serialVersionUID = -1561898683354745508L;

    @Id(autoincrement = true)
    private Long id;
    private String name;

    @ToOne(joinProperty = "")
    private InspectItem inspectItem;    //所属检查项
    private String roleName;
    private String result;              //检查结果
    private String description;         //问题描述

    private Long problemTypeCode;            //问题类型

    @ToOne(joinProperty = "problemTypeCode")
    private ProblemType problemType;

    @ToMany(referencedJoinProperty = "contentId")
    private List<ProblemPhoto> problemPhotoList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1747847796)
    private transient InspectContentDao myDao;

    @Generated(hash = 563736016)
    public InspectContent(Long id, String name, String roleName, String result,
            String description, Long problemTypeCode) {
        this.id = id;
        this.name = name;
        this.roleName = roleName;
        this.result = result;
        this.description = description;
        this.problemTypeCode = problemTypeCode;
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

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProblemTypeCode() {
        return this.problemTypeCode;
    }

    public void setProblemTypeCode(Long problemTypeCode) {
        this.problemTypeCode = problemTypeCode;
    }

    @Generated(hash = 206725759)
    private transient boolean inspectItem__refreshed;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1289166528)
    public InspectItem getInspectItem() {
        if (inspectItem != null || !inspectItem__refreshed) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            InspectItemDao targetDao = daoSession.getInspectItemDao();
            targetDao.refresh(inspectItem);
            inspectItem__refreshed = true;
        }
        return inspectItem;
    }

    /** To-one relationship, returned entity is not refreshed and may carry only the PK property. */
    @Generated(hash = 359321951)
    public InspectItem peakInspectItem() {
        return inspectItem;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 491242744)
    public void setInspectItem(InspectItem inspectItem) {
        synchronized (this) {
            this.inspectItem = inspectItem;
            inspectItem__refreshed = true;
        }
    }

    @Generated(hash = 1731027979)
    private transient Long problemType__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 84456161)
    public ProblemType getProblemType() {
        Long __key = this.problemTypeCode;
        if (problemType__resolvedKey == null
                || !problemType__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProblemTypeDao targetDao = daoSession.getProblemTypeDao();
            ProblemType problemTypeNew = targetDao.load(__key);
            synchronized (this) {
                problemType = problemTypeNew;
                problemType__resolvedKey = __key;
            }
        }
        return problemType;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 214294994)
    public void setProblemType(ProblemType problemType) {
        synchronized (this) {
            this.problemType = problemType;
            problemTypeCode = problemType == null ? null : problemType.getId();
            problemType__resolvedKey = problemTypeCode;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 663668006)
    public List<ProblemPhoto> getProblemPhotoList() {
        if (problemPhotoList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProblemPhotoDao targetDao = daoSession.getProblemPhotoDao();
            List<ProblemPhoto> problemPhotoListNew = targetDao
                    ._queryInspectContent_ProblemPhotoList(id);
            synchronized (this) {
                if (problemPhotoList == null) {
                    problemPhotoList = problemPhotoListNew;
                }
            }
        }
        return problemPhotoList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1927184112)
    public synchronized void resetProblemPhotoList() {
        problemPhotoList = null;
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
