package com.miaxis.inspection.entity;

import com.miaxis.inspection.entity.comm.Permission;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.ToMany;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.DaoException;
import com.miaxis.inspection.model.local.greenDao.gen.DaoSession;
import com.miaxis.inspection.model.local.greenDao.gen.PermissionDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectorDao;

/**
 * 检察员
 * 对应服务端的Cencor
 * Created by xu.nan on 2018/1/3.C
 */
@Entity
public class Inspector implements Serializable {

    private static final long serialVersionUID = -3685964985925754110L;

    @Id
    private Long id;
    private String censorName;
    private String censorCode;
    private String bankCode;
    private String roleId;
    private String phoneNo;
    private String idCard;
    private String opDate;
    private String opUser;
    private String opUserName;
    private String password;

    @ToMany(joinProperties = {@JoinProperty(name = "censorCode", referencedName = "inspectorCode")})
    private List<Permission> permissionList;

    private String finger0;
    private String finger1;
    private String finger2;
    private String finger3;
    private String finger4;
    private String finger5;
    private String finger6;
    private String finger7;
    private String finger8;
    private String finger9;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1614975461)
    private transient InspectorDao myDao;
    @Generated(hash = 1898726963)
    public Inspector(Long id, String censorName, String censorCode, String bankCode,
            String roleId, String phoneNo, String idCard, String opDate,
            String opUser, String opUserName, String password, String finger0,
            String finger1, String finger2, String finger3, String finger4,
            String finger5, String finger6, String finger7, String finger8,
            String finger9) {
        this.id = id;
        this.censorName = censorName;
        this.censorCode = censorCode;
        this.bankCode = bankCode;
        this.roleId = roleId;
        this.phoneNo = phoneNo;
        this.idCard = idCard;
        this.opDate = opDate;
        this.opUser = opUser;
        this.opUserName = opUserName;
        this.password = password;
        this.finger0 = finger0;
        this.finger1 = finger1;
        this.finger2 = finger2;
        this.finger3 = finger3;
        this.finger4 = finger4;
        this.finger5 = finger5;
        this.finger6 = finger6;
        this.finger7 = finger7;
        this.finger8 = finger8;
        this.finger9 = finger9;
    }
    @Generated(hash = 370751200)
    public Inspector() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCensorName() {
        return this.censorName;
    }
    public void setCensorName(String censorName) {
        this.censorName = censorName;
    }
    public String getCensorCode() {
        return this.censorCode;
    }
    public void setCensorCode(String censorCode) {
        this.censorCode = censorCode;
    }
    public String getBankCode() {
        return this.bankCode;
    }
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    public String getRoleId() {
        return this.roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getPhoneNo() {
        return this.phoneNo;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public String getIdCard() {
        return this.idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public String getOpDate() {
        return this.opDate;
    }
    public void setOpDate(String opDate) {
        this.opDate = opDate;
    }
    public String getOpUser() {
        return this.opUser;
    }
    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }
    public String getOpUserName() {
        return this.opUserName;
    }
    public void setOpUserName(String opUserName) {
        this.opUserName = opUserName;
    }
    public String getFinger0() {
        return this.finger0;
    }
    public void setFinger0(String finger0) {
        this.finger0 = finger0;
    }
    public String getFinger1() {
        return this.finger1;
    }
    public void setFinger1(String finger1) {
        this.finger1 = finger1;
    }
    public String getFinger2() {
        return this.finger2;
    }
    public void setFinger2(String finger2) {
        this.finger2 = finger2;
    }
    public String getFinger3() {
        return this.finger3;
    }
    public void setFinger3(String finger3) {
        this.finger3 = finger3;
    }
    public String getFinger4() {
        return this.finger4;
    }
    public void setFinger4(String finger4) {
        this.finger4 = finger4;
    }
    public String getFinger5() {
        return this.finger5;
    }
    public void setFinger5(String finger5) {
        this.finger5 = finger5;
    }
    public String getFinger6() {
        return this.finger6;
    }
    public void setFinger6(String finger6) {
        this.finger6 = finger6;
    }
    public String getFinger7() {
        return this.finger7;
    }
    public void setFinger7(String finger7) {
        this.finger7 = finger7;
    }
    public String getFinger8() {
        return this.finger8;
    }
    public void setFinger8(String finger8) {
        this.finger8 = finger8;
    }
    public String getFinger9() {
        return this.finger9;
    }
    public void setFinger9(String finger9) {
        this.finger9 = finger9;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1563937008)
    public List<Permission> getPermissionList() {
        if (permissionList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PermissionDao targetDao = daoSession.getPermissionDao();
            List<Permission> permissionListNew = targetDao._queryInspector_PermissionList(censorCode);
            synchronized (this) {
                if (permissionList == null) {
                    permissionList = permissionListNew;
                }
            }
        }
        return permissionList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1150459409)
    public synchronized void resetPermissionList() {
        permissionList = null;
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
    @Generated(hash = 591977457)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getInspectorDao() : null;
    }


}
