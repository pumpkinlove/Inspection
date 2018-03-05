package com.miaxis.inspection.model.remote.retrofit;

import com.miaxis.inspection.entity.ResponseEntity;
import com.miaxis.inspection.entity.comm.CheckPoint;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by xu.nan on 2018/3/1.
 */

public interface InspectPointNet {

    @FormUrlEncoded
    @POST("anbao/api/modPoint")
    Observable<ResponseEntity> updateInspectPoint(@Field("jsonPoint") String jsonPoint);

}
