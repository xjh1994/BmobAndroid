package com.xjh1994.bmob.service;

import com.xjh1994.bmob.Config;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by XJH on 16/3/28.
 * Blog: http://www.xjh1994.com
 */
public class BmobAppInfoManger {

    private String appId;
    private String masterKey;
    private Retrofit retrofit;
    private BmobAppService bmobAppService;

    public BmobAppInfoManger(String appId, String masterKey) {
        this.appId = appId;
        this.masterKey = masterKey;
        retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Config.BMOB_REST_API_URL)
                .client(generateClient(appId, masterKey))
                .build();
        bmobAppService = retrofit.create(BmobAppService.class);
    }

    private interface BmobAppService {

        @GET("schemas")
        public Call<String> getAppInfo();
    }

    public Observable<String> getAppInfo() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Call call = bmobAppService.getAppInfo();
                    Response response = call.execute();
                    String bmobAppInfo = (String) response.body();
                    subscriber.onNext(bmobAppInfo);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    public static OkHttpClient generateClient(final String appId, final String masterKey) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("X-Bmob-Application-Id", appId)
                                .addHeader("X-Bmob-Master-Key", masterKey)
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();

        return httpClient;
    }

}
