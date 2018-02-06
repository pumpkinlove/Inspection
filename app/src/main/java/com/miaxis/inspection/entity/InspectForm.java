package com.miaxis.inspection.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.DaoException;
import com.miaxis.inspection.dao.gen.DaoSession;
import com.miaxis.inspection.dao.gen.InspectItemDao;
import com.miaxis.inspection.dao.gen.InspectFormDao;

/**
 * 检查表单
 * Created by xu.nan on 2018/1/29.
 */
@Entity
public class InspectForm implements Serializable {

    private static final long serialVersionUID = 3026327254261470986L;

    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String code;
    private String roleName;
    private String roleCode;
    private int type;
    private String typeName;
    private String requirement;
    private int completionRate;

    @ToMany(referencedJoinProperty = "inspectFormId")
    private List<InspectItem> inspectItemList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 996973078)
    private transient InspectFormDao myDao;

    @Generated(hash = 1617805843)
    public InspectForm(Long id, String name, String code, String roleName,
            String roleCode, int type, String typeName, String requirement,
            int completionRate) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.roleName = roleName;
        this.roleCode = roleCode;
        this.type = type;
        this.typeName = typeName;
        this.requirement = requirement;
        this.completionRate = completionRate;
    }

    @Generated(hash = 1814758225)
    public InspectForm() {
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

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return this.roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getRequirement() {
        return this.requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public int getCompletionRate() {
        return this.completionRate;
    }

    public void setCompletionRate(int completionRate) {
        this.completionRate = completionRate;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1792122271)
    public List<InspectItem> getInspectItemList() {
        if (inspectItemList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            InspectItemDao targetDao = daoSession.getInspectItemDao();
            List<InspectItem> inspectItemListNew = targetDao
                    ._queryInspectForm_InspectItemList(id);
            synchronized (this) {
                if (inspectItemList == null) {
                    inspectItemList = inspectItemListNew;
                }
            }
        }
        return inspectItemList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 34748945)
    public synchronized void resetInspectItemList() {
        inspectItemList = null;
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
    @Generated(hash = 678017590)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getInspectFormDao() : null;
    }

}
