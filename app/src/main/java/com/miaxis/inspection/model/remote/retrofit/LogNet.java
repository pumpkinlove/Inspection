package com.miaxis.inspection.model.remote.retrofit;

import com.miaxis.inspection.entity.ResponseEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by xu.nan on 2018/3/2.
 */

public interface LogNet {

    @FormUrlEncoded
    @POST("anbao/api/pointLog")
    Observable<ResponseEntity<String>> uploadPointLog(@Field("jsonPointLog") String jsonPointLog);

    @FormUrlEncoded
    @POST("anbao/api/contentLog")
    Observable<ResponseEntity<String>> uploadContentLog(@Field("jsonContentLog") String jsonContentLog);

}
