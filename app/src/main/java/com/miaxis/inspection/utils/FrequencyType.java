package com.miaxis.inspection.utils;

/**
 * Created by xu.nan on 2018/2/8.
 */

public interface FrequencyType {
    int NEVER = 0;      //永不
    int PER_DAY = 1;    //"次/天";
    int PER_WEEK = 2;   //"次/周";
    int PER_MONTH = 3;  //"次/月";
    int PER_SEASON = 4; //"次/季度";
    int PER_YEAR = 5;   //"次/年";
}
