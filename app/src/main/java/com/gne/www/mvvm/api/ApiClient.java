package com.gne.www.mvvm.api;

import android.content.Context;

import com.gne.www.mvvm.R;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static ApiInterface getClient(Context context){
        return new Retrofit.Builder()
                .client(new OkHttpClient.Builder().callTimeout(5, TimeUnit.SECONDS).addNetworkInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
//                        Request original=chain.request();
//
//                        Request request=original.newBuilder()
//                                .addHeader("","")
//                                .build();
//                        return chain.proceed(request);
                        return chain.proceed(chain.request());
                    }
                }).build())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .baseUrl(context.getResources().getString(R.string.base_url))
                .build()
                .create(ApiInterface.class);
    }
}
