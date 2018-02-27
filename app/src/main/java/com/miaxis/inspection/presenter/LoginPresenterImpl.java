package com.miaxis.inspection.presenter;

import com.miaxis.inspection.entity.Inspector;
import com.miaxis.inspection.model.IInspectorModel;
import com.miaxis.inspection.view.ILoginView;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public class LoginPresenterImpl implements ILoginPresenter {

    ILoginView loginView;

    IInspectorModel inspectorModel;

    public LoginPresenterImpl(ILoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void syncInspector(String orgCode) {
    }

    @Override
    public void doLogin(Inspector inspector, String password) {

    }

    @Override
    public void onClearPassword() {

    }

    @Override
    public void onLogin() {

    }

    @Override
    public void onLoginSucceed() {

    }

    @Override
    public void onLoginFailed() {

    }

    @Override
    public void onShowConfig() {

    }

    @Override
    public void onHideConfig() {

    }
}
