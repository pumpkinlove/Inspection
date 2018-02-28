package com.miaxis.inspection.presenter;

import com.miaxis.inspection.entity.Config;
import com.miaxis.inspection.entity.Inspector;

import java.util.List;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public interface ILoginPresenter {

    void checkConfig();

    void syncInspector(Config config);

    void onShowInspectorList(List<Inspector> inspectorList);

    void doLogin(Inspector inspector, String password);

    void onClearPassword();

    void onLoginSucceed();

    void onLoginFailed();

    void onShowConfig();

    void onHideConfig();

    void showProgressMessage(String message);

    void showProgressDialog();

    void hideProgressDialog();

    void setProgressDialogCancelable(boolean cancelable);

}
