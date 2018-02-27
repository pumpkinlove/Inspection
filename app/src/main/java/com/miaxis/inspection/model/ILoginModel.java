package com.miaxis.inspection.model;

import com.miaxis.inspection.entity.Inspector;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public interface ILoginModel {

    /**
     * 验证检查员密码
     * @param inspector
     * @param password
     * @return
     */
    boolean checkInspector(Inspector inspector, String password);

    /**
     * 下载检查员
     * @param orgCode
     * @param ip
     * @param port
     */
    void syncInspector(String orgCode, String ip, String port);
}
