package com.miaxis.inspection.model.remote.retrofit;

import com.miaxis.inspection.entity.ResponseEntity;
import com.miaxis.inspection.entity.comm.CheckPointLog;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by xu.nan on 2018/3/2.
 */

public interface LogNet {

    @FormUrlEncoded
    @POST("anbao/api/pointLog")
    Observable<ResponseEntity<String>> uploadPointLog(@Field("jsonPointLog") String jsonPointLog);

    @Multipart
    @POST("anbao/api/contentLog")
    Observable<ResponseEntity<String>> uploadContentLog(@Query("jsonContentLog") String jsonContentLog, @Part List<MultipartBody.Part> file);

    @FormUrlEncoded
    @POST("anbao/api/downProjectLog")
    Observable<ResponseEntity<CheckPointLog>> downPointLog(@Field("bankcode") String orgCode, @Field("date") String date);

}
