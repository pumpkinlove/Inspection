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
import retrofit2.http.PartMap;

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

    @FormUrlEncoded
    @POST("anbao/api/downProjectLog")
    Observable<ResponseEntity<CheckPointLog>> downPointLog(@Field("bankcode") String orgCode, @Field("date") String date);


    @Multipart
    @POST("anbao/api/uploadVideo")
    Observable<ResponseEntity> uploadVideo(@Part MultipartBody.Part file);

    @Multipart
    @POST("anbao/api/uploadVoice")
    Observable<ResponseEntity> uploadVoice(@Part MultipartBody.Part file);

    @Multipart
    @POST("anbao/api/uploadPicture")
    Observable<ResponseEntity> uploadPictures(@Part List<MultipartBody.Part> file);
}
