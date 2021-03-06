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
import org.greenrobot.greendao.annotation.Transient;

import com.miaxis.inspection.model.local.greenDao.gen.DaoSession;
import com.miaxis.inspection.model.local.greenDao.gen.ProblemPhotoDao;
import com.miaxis.inspection.model.local.greenDao.gen.ProblemTypeDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectContentDao;
import com.miaxis.inspection.model.local.greenDao.gen.InspectContentLogDao;

/**
 * Created by xu.nan on 2018/2/6.
 */
@Entity
public class InspectContentLog implements Serializable {

    private static final long serialVersionUID = 6551680523961308516L;

    @Id(autoincrement = true)
    private Long id;

    private String pointLogCode;
    private boolean uploaded;

    private Long contentId;
    @ToOne(joinProperty = "contentId")
    private InspectContent inspectContent;

    private Date opDate;

    private String resultType;          //检查结果类型
    private boolean hasProblem;         //检查结果是否有问题
    private String description;         //问题描述

    private Long problemTypeId;            //问题类型
    @ToOne(joinProperty = "problemTypeId")
    private ProblemType problemType;

    @ToMany(referencedJoinProperty = "contentLogId")
    private List<ProblemPhoto> problemPhotoList;

    @Transient
    private String photoName1;
    @Transient
    private String photoName2;
    @Transient
    private String photoName3;
    @Transient
    private String photoName4;
    @Transient
    private String photoName5;
    @Transient
    private String videoName;
    @Transient
    private String voiceName;


    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1054412929)
    private transient InspectContentLogDao myDao;

    @Generated(hash = 722504119)
    public InspectContentLog(Long id, String pointLogCode, boolean uploaded, Long contentId,
            Date opDate, String resultType, boolean hasProblem, String description,
            Long problemTypeId) {
        this.id = id;
        this.pointLogCode = pointLogCode;
        this.uploaded = uploaded;
        this.contentId = contentId;
        this.opDate = opDate;
        this.resultType = resultType;
        this.hasProblem = hasProblem;
        this.description = description;
        this.problemTypeId = problemTypeId;
    }

    @Generated(hash = 1620578972)
    public InspectContentLog() {
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

    public Long getContentId() {
        return this.contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Date getOpDate() {
        return this.opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }

    public String getResultType() {
        return this.resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public boolean getHasProblem() {
        return this.hasProblem;
    }

    public void setHasProblem(boolean hasProblem) {
        this.hasProblem = hasProblem;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProblemTypeId() {
        return this.problemTypeId;
    }

    public void setProblemTypeId(Long problemTypeId) {
        this.problemTypeId = problemTypeId;
    }

    @Generated(hash = 255919389)
    private transient Long inspectContent__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 383839586)
    public InspectContent getInspectContent() {
        Long __key = this.contentId;
        if (inspectContent__resolvedKey == null
                || !inspectContent__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            InspectContentDao targetDao = daoSession.getInspectContentDao();
            InspectContent inspectContentNew = targetDao.load(__key);
            synchronized (this) {
                inspectContent = inspectContentNew;
                inspectContent__resolvedKey = __key;
            }
        }
        return inspectContent;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1980103900)
    public void setInspectContent(InspectContent inspectContent) {
        synchronized (this) {
            this.inspectContent = inspectContent;
            contentId = inspectContent == null ? null : inspectContent.getId();
            inspectContent__resolvedKey = contentId;
        }
    }

    @Generated(hash = 1731027979)
    private transient Long problemType__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 827276694)
    public ProblemType getProblemType() {
        Long __key = this.problemTypeId;
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
    @Generated(hash = 1275189865)
    public void setProblemType(ProblemType problemType) {
        synchronized (this) {
            this.problemType = problemType;
            problemTypeId = problemType == null ? null : problemType.getId();
            problemType__resolvedKey = problemTypeId;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 2062331428)
    public List<ProblemPhoto> getProblemPhotoList() {
        if (problemPhotoList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProblemPhotoDao targetDao = daoSession.getProblemPhotoDao();
            List<ProblemPhoto> problemPhotoListNew = targetDao
                    ._queryInspectContentLog_ProblemPhotoList(id);
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
    @Generated(hash = 369015380)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getInspectContentLogDao() : null;
    }

    public boolean getUploaded() {
        return this.uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public String getPhotoName1() {
        return photoName1;
    }

    public void setPhotoName1(String photoName1) {
        this.photoName1 = photoName1;
    }

    public String getPhotoName2() {
        return photoName2;
    }

    public void setPhotoName2(String photoName2) {
        this.photoName2 = photoName2;
    }

    public String getPhotoName3() {
        return photoName3;
    }

    public void setPhotoName3(String photoName3) {
        this.photoName3 = photoName3;
    }

    public String getPhotoName4() {
        return photoName4;
    }

    public void setPhotoName4(String photoName4) {
        this.photoName4 = photoName4;
    }

    public String getPhotoName5() {
        return photoName5;
    }

    public void setPhotoName5(String photoName5) {
        this.photoName5 = photoName5;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVoiceName() {
        return voiceName;
    }

    public void setVoiceName(String voiceName) {
        this.voiceName = voiceName;
    }
}
