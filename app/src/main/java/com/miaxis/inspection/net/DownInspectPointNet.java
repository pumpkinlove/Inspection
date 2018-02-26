package com.miaxis.inspection.net;

import com.miaxis.inspection.entity.ResponseEntity;
import com.miaxis.inspection.entity.comm.CheckPoint;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xu.nan on 2018/2/26.
 */

public interface DownInspectPointNet {

    @GET("anbao/api/downPoint")
    Observable<ResponseEntity<CheckPoint>> downloadInspectPoint(@Query("bankcode") String orgCode);

}
