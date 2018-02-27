package com.miaxis.inspection.presenter;

import com.miaxis.inspection.entity.Inspector;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public interface ILoginPresenter {

    void syncInspector(String orgCode);

    void doLogin(Inspector inspector, String password);

    void onClearPassword();

    void onLogin();

    void onLoginSucceed();

    void onLoginFailed();

    void onShowConfig();

    void onHideConfig();

}
