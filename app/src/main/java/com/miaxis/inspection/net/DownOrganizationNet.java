package com.miaxis.inspection.net;

import com.miaxis.inspection.entity.Organization;
import com.miaxis.inspection.entity.ResponseEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * 下载机构信息
 * Created by xu.nan on 2018/2/23.
 */

public interface DownOrganizationNet {

    @GET("anbao/api/downBank")
    Observable<ResponseEntity<Organization>> downloadOrgnization(@Query("bankcode") String orgCode);

}
