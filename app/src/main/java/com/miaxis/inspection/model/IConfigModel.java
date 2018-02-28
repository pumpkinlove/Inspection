package com.miaxis.inspection.model;

import com.miaxis.inspection.view.fragment.ConfigFragment;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public interface IConfigModel {

    void saveConfig(String ip, String port, String orgCode, ConfigFragment.OnConfigClickListener listener);

    void fetchConfig();

}
