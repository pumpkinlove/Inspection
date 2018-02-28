package com.miaxis.inspection.presenter;

import com.miaxis.inspection.entity.Config;
import com.miaxis.inspection.entity.Inspector;
import com.miaxis.inspection.model.ILoginModel;
import com.miaxis.inspection.model.LoginModelImpl;
import com.miaxis.inspection.view.ILoginView;

import java.util.List;

/**
 * 登录模块presenter实现类
 * Created by Administrator on 2018/2/28 0028.
 */

public class LoginPresenterImpl implements ILoginPresenter {

    private ILoginView loginView;
    private ILoginModel loginModel;

    public LoginPresenterImpl(ILoginView loginView) {
        this.loginView = loginView;
        loginModel = new LoginModelImpl(this);
    }

    @Override
    public void checkConfig() {
        loginModel.checkConfig();
    }

    @Override
    public void syncInspector(Config config) {
        loginModel.syncInspector(config);
    }

    @Override
    public void onShowInspectorList(List<Inspector> inspectorList) {
        loginView.onShowInspectorList(inspectorList);
    }

    @Override
    public void doLogin(Inspector inspector, String password) {
        loginModel.checkPassword(inspector, password);
    }

    @Override
    public void onClearPassword() {
        loginView.onClearPassword();
    }

    @Override
    public void onLoginSucceed() {
        loginView.onLoginSucceed();
    }

    @Override
    public void onLoginFailed() {
        loginView.onLoginFailed();
    }

    @Override
    public void onShowConfig() {
        loginView.onShowConfig();
    }

    @Override
    public void onHideConfig() {
        loginView.onHideConfig();
    }

    @Override
    public void showProgressMessage(String message) {
        loginView.showProgressMessage(message);
    }

    @Override
    public void showProgressDialog() {
        loginView.showProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        loginView.hideProgressDialog();
    }

    @Override
    public void setProgressDialogCancelable(boolean cancelable) {
        loginView.setProgressDialogCancelable(cancelable);
    }
}
