package com.zhangzhe.www.myapplication;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by 张哲 on 2016/11/10.
 * 邮箱:williamzz2015@163.com
 */

public interface Server {
    @GET
    Observable<Object> get();
}
