package com.mx.ganks.net;

import com.mx.ganks.constant.Constants;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by boobooL on 2016/4/29 0029
 * Created 邮箱 ：boobooMX@163.com
 */
public class BuildService {
    private static Retrofit sRetrofit;
    public static GankService getGankService(){
        if(sRetrofit==null){
            sRetrofit=new Retrofit.Builder()
                    .baseUrl(Constants.GANK_URL)
                    .client(defaultOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())//设置默认的解析库 Gson
                    .build();
        }
        return sRetrofit.create(GankService.class);
    }

    public static OkHttpClient defaultOkHttpClient(){
        OkHttpClient client=new OkHttpClient();
        client.setConnectTimeout(5, TimeUnit.SECONDS);
        client.setReadTimeout(5, TimeUnit.SECONDS);
        client.setWriteTimeout(5, TimeUnit.SECONDS);
        return  client;
    }
}
