package com.miaxis.inspection.model;

import com.miaxis.inspection.entity.Inspector;

import java.util.List;

/**
 * Created by Administrator on 2018/2/27 0027.
 */

public interface IInspectorModel {

    /**
     * 新增检查员
     * @param inspector
     */
    void addInspector(Inspector inspector);

    /**
     * 删除检查员
     * @param inspectorCode
     */
    void delInspector(String inspectorCode);

    /**
     * 更新检查员
     * @param inspector
     */
    void updateInspector(Inspector inspector);

    /**
     * 查询全部检查员
     * @return
     */
    List<Inspector> findAllInspector(String orgCode, String ip, String port);

}
