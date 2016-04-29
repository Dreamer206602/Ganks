package com.mx.ganks.net;

import com.mx.ganks.model.CommonDate;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by boobooL on 2016/4/29 0029
 * Created 邮箱 ：boobooMX@163.com
 */
public interface GankService {
    //http://gank.io/api/data/Android/10/1
    @GET("dada/{type}/{count}/{pageIndex}")
    Call<CommonDate> getCommonDate(@Path("type") String type,
                                   @Path("count") int count,
                                   @Path("pageIndex") int pageIndex);
}
