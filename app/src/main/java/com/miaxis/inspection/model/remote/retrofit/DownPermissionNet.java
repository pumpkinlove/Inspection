package com.miaxis.inspection.model.remote.retrofit;

import com.miaxis.inspection.entity.ResponseEntity;
import com.miaxis.inspection.entity.comm.Permission;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xu.nan on 2018/2/26.
 */

public interface DownPermissionNet {

    @GET("anbao/api/downPermission")
    Observable<ResponseEntity<Permission>> downPermission(@Query("censorCode")String censorCode);

}
