package com.miaxis.inspection.net;

import android.database.Observable;

import com.miaxis.inspection.entity.ResponseEntity;
import com.miaxis.inspection.entity.comm.Task;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xu.nan on 2018/2/26.
 */

public interface DownTaskNet {

    @GET("anbao/api/downTask")
    Observable<ResponseEntity<Task>> downTask(@Query("bankcode")String orgCode);

}