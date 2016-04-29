package com.mx.ganks.callback;

/**
 * Created by boobooL on 2016/4/29 0029
 * Created 邮箱 ：boobooMX@163.com
 */
public interface ICallBack<T> {
    void onSuccess(String flag,String key,T t);
    void onFailure(String flag,String key,String why);
}
