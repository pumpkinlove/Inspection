package com.miaxis.inspection.model.remote.retrofit;

import com.miaxis.inspection.entity.ResponseEntity;
import com.miaxis.inspection.entity.comm.CommTask;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xu.nan on 2018/2/26.
 */

public interface DownTaskNet {

    @GET("anbao/api/downTask")
    Observable<ResponseEntity<CommTask>> downTask(@Query("bankcode")String orgCode);

}