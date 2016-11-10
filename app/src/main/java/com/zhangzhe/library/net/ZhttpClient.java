package com.zhangzhe.library.net;

import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by 张哲 on 2016/11/10.
 * 邮箱:williamzz2015@163.com
 */

public class ZhttpClient {
    public static final long DEFAULT_TIMEOUT = 5000;
    private static long connectTimeout=DEFAULT_TIMEOUT;
    private static long writeTimeout=DEFAULT_TIMEOUT;
    private static long readTimeout=DEFAULT_TIMEOUT;
    private static File  httpCacheDirectory;
    private  static long cacheSize =10 * 1024 * 1024;

    protected static String BASE_URL;
    private static  Context sContext;
    private static ZhttpClient zhttpClient;
    private static OkHttpClient okHttpClient;
    private boolean debug=false;

    public static ZhttpClient builder(String baseUrl, Context context) {
        zhttpClient=Inner.zhttpClient;
        BASE_URL=baseUrl;
        sContext  = context;
      return   zhttpClient;
    }
    private ZhttpClient() {
    }

    private static class  Inner{
       private static ZhttpClient zhttpClient=new ZhttpClient();
    }
    private  OkHttpClient getOkHttpClient(Context application) {
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //设置超时时间
        httpClientBuilder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(readTimeout, TimeUnit.SECONDS);
        //设置缓存
        if (httpCacheDirectory==null){
            httpCacheDirectory = new File(application.getCacheDir(), "ZCLIENT_LIBRARY_CACHE");
        }
        if (debug){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(httpLoggingInterceptor);
        }
        httpClientBuilder.cache(new Cache(httpCacheDirectory, cacheSize));

        return httpClientBuilder.build();
    }

    /**
     * 设置连接超时时间
     * @param time
     * @return
     */
    public ZhttpClient connectTimeout(long time){
        connectTimeout=time;
        return this;
    }

    /**
     * 设置写入超时时间
     * @param time
     * @return
     */
    public ZhttpClient writeTimeout(long time){
        writeTimeout=time;
        return this;
    }

    /**
     * 设置读的超时时间
     * @param time
     * @return
     */
    public ZhttpClient readTimeout(long time){
        readTimeout=time;
        return this;
    }

    /**
     * 设置缓存文件
     * @param cacheFile
     * @return
     */
    public ZhttpClient cache(File cacheFile){
        httpCacheDirectory =cacheFile;
        return this;
    }

    public ZhttpClient log(boolean b){
        this.debug =b;
        return this;
    }

    public void build() throws Exception {
        if (okHttpClient!=null){
            throw  new Exception(" build() 已经被调用过");
        }
        okHttpClient = getOkHttpClient(sContext);
    }
    protected static OkHttpClient getOkHttpClient()  {
        if (okHttpClient==null){
            try {
                throw  new Exception("ZhttpClient 还未构建 ,请在builder()进行配置后调用builder() 或直接调用 build();");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return okHttpClient;
    }
}
