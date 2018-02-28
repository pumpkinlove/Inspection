package com.miaxis.inspection.presenter;

import com.miaxis.inspection.entity.Config;
import com.miaxis.inspection.model.ConfigModelImpl;
import com.miaxis.inspection.model.IConfigModel;
import com.miaxis.inspection.view.IConfigView;
import com.miaxis.inspection.view.fragment.ConfigFragment;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public class ConfigPresenterImpl implements IConfigPresenter {

    private IConfigView configView;
    private IConfigModel configModel;

    public ConfigPresenterImpl(IConfigView configView) {
        this.configView = configView;
        configModel = new ConfigModelImpl(this);
    }

    @Override
    public void saveConfig(String ip, String port, String orgCode, ConfigFragment.OnConfigClickListener listener) {
        configModel.saveConfig(ip, port, orgCode, listener);
    }

    @Override
    public void showProgressDialog() {
        configView.showProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        configView.hideProgressDialog();
    }

    @Override
    public void setProgressDialogMessage(String message) {
        configView.setProgressDialogMessage(message);
    }

    @Override
    public void setProgressDialogCancelable(boolean cancelable) {
        configView.setProgressDialogCancelable(cancelable);
    }

    @Override
    public void loadConfig() {
        configModel.fetchConfig();
    }

    @Override
    public void fetchConfig(Config config) {
        configView.fetchConfig(config);
    }
}
