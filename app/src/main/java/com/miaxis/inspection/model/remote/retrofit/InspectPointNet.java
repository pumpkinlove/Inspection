package com.miaxis.inspection.model.remote.retrofit;

import com.miaxis.inspection.entity.ResponseEntity;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by xu.nan on 2018/3/1.
 */

public interface InspectPointNet {

    @POST("anbao/api/modPoint")
    Observable<ResponseEntity> updateInspectPoint(@Query("jsonPoint") String checkPointJson);

}
