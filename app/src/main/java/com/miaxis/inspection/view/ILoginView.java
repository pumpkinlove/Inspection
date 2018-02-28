package com.miaxis.inspection.view;

import com.miaxis.inspection.entity.Inspector;

import java.util.List;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public interface ILoginView {

    void onClearPassword();

    void onLoginSucceed();

    void onLoginFailed();

    void onShowConfig();

    void onHideConfig();

    void onShowInspectorList(List<Inspector> inspectorList);

    void showProgressMessage(String message);

    void showProgressDialog();

    void hideProgressDialog();

    void setProgressDialogCancelable(boolean cancelable);
}
