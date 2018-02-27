package com.miaxis.inspection.model.remote.retrofit;

import com.miaxis.inspection.entity.ProblemType;
import com.miaxis.inspection.entity.ResponseEntity;
import com.miaxis.inspection.entity.ResultType;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by xu.nan on 2018/2/27.
 */

public interface DownDictNet {

    @GET("anbao/api/downResultDict")
    Observable<ResponseEntity<ResultType>> downResultTypeDict();

    @GET("anbao/api/downErrorDict")
    Observable<ResponseEntity<ProblemType>> downProblemTypeDict();

}