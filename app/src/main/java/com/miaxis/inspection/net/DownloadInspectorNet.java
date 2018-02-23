package com.miaxis.inspection.net;

import com.miaxis.inspection.entity.Inspector;
import com.miaxis.inspection.entity.ResponseEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * 下载机构信息
 * Created by xu.nan on 2018/2/23.
 */

public interface DownloadInspectorNet {

    @GET("anbao/workerHandler/downWorker")
    Observable<ResponseEntity<Inspector>> downloadInspector(@Query("bankcode") String orgCode);

}