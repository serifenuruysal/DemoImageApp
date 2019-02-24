package com.example.demoimageapp.serverapi;
import android.util.Log;
import com.example.demoimageapp.BuildConfig;
import java.io.IOException;

import java.nio.charset.Charset;
import java.util.logging.Logger;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by nuruysal on 09,February,2019
 */
public class ApiClient {


  private static final String TAG = "ApiClient";
  private static final String BASE_URL = "https://m.mobile.de/";
  private static final OkHttpClient client;
  private static ApiService mService;
  static {

    client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(Level.BODY))
//        .addInterceptor(new Interceptor() {
//          @Override
//          public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//            okhttp3.Response response = chain.proceed(request);
//            Log.d(TAG, "response: "+response.body().string());
//            return null;
//          }
//        })
        .build();

  }
  public static ApiService getInstance() {
    synchronized (new Object()) {
      if (mService == null) {
        mService = getRetrofitInstance().create(ApiService.class);
      }
      return mService;
    }
  }

  private static Retrofit getRetrofitInstance() {
    return new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build();
  }

  public ApiService getService() {
    return mService;
  }



}
