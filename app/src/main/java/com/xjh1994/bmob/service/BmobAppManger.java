package com.xjh1994.bmob.service;

import com.xjh1994.bmob.Config;
import com.xjh1994.bmob.bean.BmobApp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by XJH on 16/3/28.
 * Blog: http://www.xjh1994.com
 */
public class BmobAppManger {

    private String email;
    private String password;
    private Retrofit retrofit;
    private BmobAppService bmobAppService;

    public BmobAppManger(String email, String password) {
        this.email = email;
        this.password = password;
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Config.BMOB_REST_API_URL)
                .client(generateClient(email, password))
                .build();
        bmobAppService = retrofit.create(BmobAppService.class);
    }

    private interface BmobAppService {

        @GET("apps")
        public Call<BmobApp> getApps();
    }

    public Observable<BmobApp> getApps() {
        return Observable.create(new Observable.OnSubscribe<BmobApp>() {
            @Override
            public void call(Subscriber<? super BmobApp> subscriber) {
                try {
                    Call call = bmobAppService.getApps();
                    Response response = call.execute();
                    BmobApp bmobApp = (BmobApp) response.body();
                    subscriber.onNext(bmobApp);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    public static OkHttpClient generateClient(final String email, final String password) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("X-Bmob-Email", email)
                                .addHeader("X-Bmob-Password", password)
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();

        return httpClient;
    }

}
