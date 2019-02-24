package com.example.demoimageapp.serverapi;


import com.example.demoimageapp.models.MainResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by nuruysal on 09,February,2019
 */
public interface ApiService {


  @GET("svc/a/271007089")
  Call<MainResponse> getInfo();

}
