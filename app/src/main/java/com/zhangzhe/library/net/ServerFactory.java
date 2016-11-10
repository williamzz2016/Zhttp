package com.zhangzhe.library.net;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 张哲 on 2016/11/9.
 * 邮箱:williamzz2015@163.com
 * 服务工厂类
 */

public class ServerFactory {
    private static long DEFAULT_TIMEOUT=5000;

    private ServerFactory (){

        }
    /**
     * 静态内部类java虚拟机底层实现的单例
     */
    private static class SingletonHolder{
        private static final ServerFactory INSTANCE=new ServerFactory();
        private static final Gson mGsonDateFormat = new GsonBuilder()
        .setDateFormat("yyyy-MM-dd hh:mm:ss")
        .create();
       private static Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ZhttpClient.BASE_URL)
                .client(ZhttpClient.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(mGsonDateFormat))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    /**
     * 获取本类单例
     * @return
     */
    public static <S>S create(Class<S> serviceClass){
        return SingletonHolder.retrofit.create(serviceClass);
    }
}
