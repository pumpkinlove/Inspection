package com.miaxis.inspection.model;

import com.miaxis.inspection.entity.Config;
import com.miaxis.inspection.entity.Inspector;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public interface ILoginModel {

    /**
     * 检查是否有设置信息，没有则显示设置界面
     */
    void checkConfig();

    /**
     * 验证检查员密码
     * @param inspector
     * @param password
     */
    void checkPassword(Inspector inspector, String password);

    /**
     * 同步检查员
     * @param config
     */
    void syncInspector(Config config);

    /**
     * 下载检查点（包含检查项、检查内容）
     * @param config
     */
    void downloadInspectPoint(Config config);

    /**
     * 下载登入检查员的权限
     * @param inspector
     * @param config
     */
    void downloadPermission(Inspector inspector, Config config);

    /**
     * 下载任务（包含检查清单）
     * @param config
     */
    void downloadTask(Config config);

}
