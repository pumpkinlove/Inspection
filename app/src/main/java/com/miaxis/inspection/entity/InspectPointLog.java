package com.miaxis.inspection.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.miaxis.inspection.model.local.greenDao.gen.DaoSession;
import com.miaxis.inspection.model.local.greenDao.gen.InspectContentLogDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectPointDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectItemDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectPointLogDao;

/**
 * 历史检查记录
 * Created by xu.nan on 2018/1/29.
 */
@Entity
public class InspectPointLog implements Serializable {

    private static final long serialVersionUID = -1999623990756333415L;

    @Id(autoincrement = true)
    private Long id;

    private String pointLogCode;

    private Long inspectItemId;         //对应检查项id
    @ToOne(joinProperty = "inspectItemId")
    private InspectItem inspectItem;

    private Date opDate;                //操作时间
    private String opInspectorCode;     //操作检查员编号
    private String opInspectorName;     //操作检查员姓名
    private int resultType;             //0正常 1异常
    private String result;              //检查结果

    private boolean uploaded;           //是否已上传
    private boolean inspected;

    private Long inspectPointId;        //对应检查点id
    @ToOne(joinProperty = "inspectPointId")
    private InspectPoint inspectPoint;

    @ToMany(joinProperties = {@JoinProperty(name = "pointLogCode", referencedName = "pointLogCode")})
    private List<InspectContentLog> contentList;    //下属检查内容日志

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 385790236)
    private transient InspectPointLogDao myDao;

    @Generated(hash = 1806650746)
    public InspectPointLog(Long id, String pointLogCode, Long inspectItemId, Date opDate,
            String opInspectorCode, String opInspectorName, int resultType, String result,
            boolean uploaded, boolean inspected, Long inspectPointId) {
        this.id = id;
        this.pointLogCode = pointLogCode;
        this.inspectItemId = inspectItemId;
        this.opDate = opDate;
        this.opInspectorCode = opInspectorCode;
        this.opInspectorName = opInspectorName;
        this.resultType = resultType;
        this.result = result;
        this.uploaded = uploaded;
        this.inspected = inspected;
        this.inspectPointId = inspectPointId;
    }

    @Generated(hash = 971602257)
    public InspectPointLog() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPointLogCode() {
        return this.pointLogCode;
    }

    public void setPointLogCode(String pointLogCode) {
        this.pointLogCode = pointLogCode;
    }

    public Long getInspectItemId() {
        return this.inspectItemId;
    }

    public void setInspectItemId(Long inspectItemId) {
        this.inspectItemId = inspectItemId;
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

    public Long getInspectPointId() {
        return this.inspectPointId;
    }

    public void setInspectPointId(Long inspectPointId) {
        this.inspectPointId = inspectPointId;
    }

    @Generated(hash = 1166922348)
    private transient Long inspectItem__resolvedKey;

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

    @Generated(hash = 101199454)
    private transient Long inspectPoint__resolvedKey;

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
    @Generated(hash = 1476747840)
    public List<InspectContentLog> getContentList() {
        if (contentList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            InspectContentLogDao targetDao = daoSession.getInspectContentLogDao();
            List<InspectContentLog> contentListNew = targetDao
                    ._queryInspectPointLog_ContentList(pointLogCode);
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
    @Generated(hash = 122657080)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getInspectPointLogDao() : null;
    }

    public String getOpInspectorCode() {
        return this.opInspectorCode;
    }

    public void setOpInspectorCode(String opInspectorCode) {
        this.opInspectorCode = opInspectorCode;
    }

    public int getResultType() {
        return this.resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
    }




}
