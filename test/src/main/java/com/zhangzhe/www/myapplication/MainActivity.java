package com.zhangzhe.www.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhangzhe.library.net.ServerFactory;
import com.zhangzhe.library.net.ZhttpClient;

import rx.Observable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            ZhttpClient.builder("",this)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Server server = ServerFactory.create(Server.class);
        Observable<Object> observable = server.get();
    }
}
