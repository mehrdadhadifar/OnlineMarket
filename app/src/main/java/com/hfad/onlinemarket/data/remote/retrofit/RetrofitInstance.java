package com.hfad.onlinemarket.data.remote.retrofit;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.hfad.onlinemarket.data.remote.NetworkParams.BASE_URL;

public class RetrofitInstance {
    public static final String CONSUMER_KEY = "ck_c861fcab5b71d1251a46ae06f976b58e142084db";
    public static final String CONSUMER_SECRET = "cs_a0c53a079b4416512c5bc6db7cb311b640f75cde";
    private static Retrofit sRetrofit;

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static final HttpLoggingInterceptor logger = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);


    public static Retrofit getInstance() {

        httpClient.addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original
                        .newBuilder()
                        .url(original.url().newBuilder().addQueryParameter(
                                "consumer_key", CONSUMER_KEY)
                                .addQueryParameter("consumer_secret", CONSUMER_SECRET)
                                .build())
//                        .addHeader("consumer_key", "ck_c861fcab5b71d1251a46ae06f976b58e142084db")
//                        .addHeader("consumer_secret", "cs_a0c53a079b4416512c5bc6db7cb311b640f75cde")
                        .build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.addNetworkInterceptor(logger).build();


        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return sRetrofit;
    }

    public RetrofitInstance() {
    }
}
