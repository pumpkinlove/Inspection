package com.miaxis.inspection.presenter;

import com.miaxis.inspection.entity.Config;
import com.miaxis.inspection.view.fragment.ConfigFragment;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public interface IConfigPresenter {

    void saveConfig(String ip, String port, String orgCode, ConfigFragment.OnConfigClickListener listener);

    void showProgressDialog();

    void hideProgressDialog();

    void setProgressDialogMessage(String message);

    void setProgressDialogCancelable(boolean cancelable);

    void loadConfig();

    void fetchConfig(Config config);

}
