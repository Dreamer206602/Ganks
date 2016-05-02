package com.mx.ganks.net;

import android.util.Log;

import com.mx.ganks.callback.ICallBack;
import com.mx.ganks.model.CommonDate;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by boobooL on 2016/4/29 0029
 * Created 邮箱 ：boobooMX@163.com
 */
public class GankApi {
    public static Call<CommonDate>getCommonData(final String type, final int count, final int pageIndex, final ICallBack<CommonDate>callBack){
        final Call<CommonDate>commonDate=BuildService.getGankService().getCommonDate(type,count,pageIndex);
        final String key=type+count+pageIndex;
//        commonDate.enqueue(new Callback<CommonDate>() {
//            @Override
//            public void onResponse(Response<CommonDate> response, Retrofit retrofit) {
//               if(response.isSuccess()){
//                   CommonDate commonDate=response.body();
//                   if(!commonDate.isError()){
//                       //数据正确，把数据返回
//                       Log.d("getCommonData",commonDate.toString());
//                       callBack.onSuccess(type,key,commonDate);
//                   }else{
//                       //数据错误
//                       Log.d("getCommonData",commonDate.toString());
//                       callBack.onFailure(type,key,"数据错误");
//                   }
//               }
//
//
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Log.e("getCommonData-onFailure",t.toString());
//                callBack.onFailure(type,key,"请求失败");
//
//            }
//        });
        commonDate.enqueue(new Callback<CommonDate>() {
            @Override
            public void onResponse(Response<CommonDate> response, Retrofit retrofit) {
                Log.d("Data",response.body().toString());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("getCommonData-onFailure",t.toString());
            }
        });

        return commonDate;
    }

    public static Call<CommonDate>getCommonData2(final String type, final int count, final int pageIndex) {
        final Call<CommonDate> commonDate = BuildService.getGankService().getCommonDate(type, count, pageIndex);
        final String key = type + count + pageIndex;
        commonDate.enqueue(new Callback<CommonDate>() {
            @Override
            public void onResponse(Response<CommonDate> response, Retrofit retrofit) {
                Log.d("Data",response.body().toString());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("getCommonData-onFailure",t.toString());
            }
        });

        return commonDate;
    }


}
