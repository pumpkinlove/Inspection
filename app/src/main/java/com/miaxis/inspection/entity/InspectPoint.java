package com.miaxis.inspection.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import java.util.Date;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Unique;

import com.miaxis.inspection.model.local.greenDao.gen.DaoSession;
import com.miaxis.inspection.model.local.greenDao.gen.OrganizationDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectItemDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectPointDao;

/**
 * 检查点
 * Created by xu.nan on 2018/1/3.
 */
@Entity
public class InspectPoint implements Serializable {

    private static final long serialVersionUID = -7547122795682104835L;

    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String code;
    @Unique
    private String rfid;
    private String pointName;
    private String itemName;

    private boolean bound;
    private String bindCode;

    private Date opDate;
    private String opUserCode;
    private String opUserName;

    private Long inspectItemId;
    @ToOne(joinProperty = "inspectItemId")
    private InspectItem inspectItem;        //所属检查项

    private Long organizationId;
    @ToOne(joinProperty = "organizationId")
    private Organization organization;      //所属机构

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 2085032999)
    private transient InspectPointDao myDao;
    @Generated(hash = 778774051)
    public InspectPoint(Long id, String code, String rfid, String pointName, String itemName,
            boolean bound, String bindCode, Date opDate, String opUserCode, String opUserName,
            Long inspectItemId, Long organizationId) {
        this.id = id;
        this.code = code;
        this.rfid = rfid;
        this.pointName = pointName;
        this.itemName = itemName;
        this.bound = bound;
        this.bindCode = bindCode;
        this.opDate = opDate;
        this.opUserCode = opUserCode;
        this.opUserName = opUserName;
        this.inspectItemId = inspectItemId;
        this.organizationId = organizationId;
    }
    @Generated(hash = 2114527326)
    public InspectPoint() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getRfid() {
        return this.rfid;
    }
    public void setRfid(String rfid) {
        this.rfid = rfid;
    }
    public String getPointName() {
        return this.pointName;
    }
    public void setPointName(String pointName) {
        this.pointName = pointName;
    }
    public String getItemName() {
        return this.itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public boolean getBound() {
        return this.bound;
    }
    public void setBound(boolean bound) {
        this.bound = bound;
    }
    public String getBindCode() {
        return this.bindCode;
    }
    public void setBindCode(String bindCode) {
        this.bindCode = bindCode;
    }
    public Long getInspectItemId() {
        return this.inspectItemId;
    }
    public void setInspectItemId(Long inspectItemId) {
        this.inspectItemId = inspectItemId;
    }
    public Long getOrganizationId() {
        return this.organizationId;
    }
    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
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
    @Generated(hash = 223225407)
    private transient Long organization__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1198735628)
    public Organization getOrganization() {
        Long __key = this.organizationId;
        if (organization__resolvedKey == null
                || !organization__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            OrganizationDao targetDao = daoSession.getOrganizationDao();
            Organization organizationNew = targetDao.load(__key);
            synchronized (this) {
                organization = organizationNew;
                organization__resolvedKey = __key;
            }
        }
        return organization;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2011904140)
    public void setOrganization(Organization organization) {
        synchronized (this) {
            this.organization = organization;
            organizationId = organization == null ? null : organization.getId();
            organization__resolvedKey = organizationId;
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
    @Generated(hash = 1281840772)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getInspectPointDao() : null;
    }
    public Date getOpDate() {
        return this.opDate;
    }
    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }
    public String getOpUserCode() {
        return this.opUserCode;
    }
    public void setOpUserCode(String opUserCode) {
        this.opUserCode = opUserCode;
    }
    public String getOpUserName() {
        return this.opUserName;
    }
    public void setOpUserName(String opUserName) {
        this.opUserName = opUserName;
    }

}
