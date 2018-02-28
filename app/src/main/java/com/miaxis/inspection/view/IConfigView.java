package com.miaxis.inspection.view;

import com.miaxis.inspection.entity.Config;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public interface IConfigView {

    void showProgressDialog();

    void hideProgressDialog();

    void setProgressDialogMessage(String message);

    void setProgressDialogCancelable(boolean cancelable);

    void fetchConfig(Config config);

}
