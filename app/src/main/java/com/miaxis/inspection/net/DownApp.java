package com.miaxis.inspection.net;

import android.database.Observable;

import com.miaxis.inspection.entity.ResponseEntity;

import retrofit2.http.GET;

/**
 * Created by xu.nan on 2018/2/26.
 */

public interface DownApp {

    @GET("anbao/api/getDeviceVersion")
    Observable<ResponseEntity<String>> getDeviceVersion();

    @GET("anbao/api/getAppLength")
    Observable<ResponseEntity<String>> getAppLength();

}