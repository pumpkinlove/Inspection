package com.miaxis.inspection.model.remote.retrofit;

import com.miaxis.inspection.entity.ResponseEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public interface InspectorNet {

    @FormUrlEncoded
    @POST("anbao/api/delWorker")
    Observable<ResponseEntity> deleteInspector(@Field("workCode") String workCode);

    @FormUrlEncoded
    @POST("anbao/api/addWorker")
    Observable<ResponseEntity> addInspector(@Field("jsonWorker") String jsonWorker);

    @FormUrlEncoded
    @POST("anbao/api/modWorker")
    Observable<ResponseEntity> modInspector(@Field("jsonWorker") String jsonWorker);

}
