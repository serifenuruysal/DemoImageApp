package com.example.demoimageapp.datasource;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import com.example.demoimageapp.models.Image;
import com.example.demoimageapp.models.MainResponse;
import com.example.demoimageapp.serverapi.ApiClient;
import com.example.demoimageapp.serverapi.NetworkState;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by nuruysal on 09,February,2019
 */
public class RepositoryDataSource extends PageKeyedDataSource<Long, Image> {

  private static final long FIRST_PAGE = 1;
  private static final String TAG = RepositoryDataSource.class.getSimpleName();

  /*
   *  Initialize the ApiService.
   * The networkState and initialLoading variables
   * are for updating the UI when data is being fetched
   * by displaying a progress bar
   */

  private MutableLiveData networkState;
  private MutableLiveData initialLoading;

  public RepositoryDataSource() {
    networkState = new MutableLiveData();
    initialLoading = new MutableLiveData();
  }

  /*
   *  This method is responsible to load the data initially
   * when app screen is launched for the first time.
   * We are fetching the first page data from the api
   * and passing it via the callback method to the UI.
   */
  @Override
  public void loadInitial(@NonNull LoadInitialParams<Long> params, final @NonNull LoadInitialCallback<Long, Image> callback) {
    initialLoading.postValue(NetworkState.LOADING);
    networkState.postValue(NetworkState.LOADING);

    ApiClient.getInstance().getInfo().enqueue(new Callback<MainResponse>() {
      @Override
      public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
        if (response.isSuccessful() && response.code() == 200) {
          MainResponse result = response.body();
          List<Image> list = result != null ? result.getImages() : Collections.<Image>emptyList();
          callback.onResult(list, null, FIRST_PAGE+1);
          initialLoading.postValue(NetworkState.LOADED);
          networkState.postValue(NetworkState.LOADED);

        } else {
          initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
          networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
        }

      }

      @Override
      public void onFailure(Call<MainResponse> call, Throwable t) {

        String errorMessage = t == null ? "unknown error" : t.getMessage();
        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
      }
    });
  }

  @Override
  public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Image> callback) {

  }

  /*
   * This method it is responsible for the subsequent call to load the data page wise.
   * This method is executed in the background thread
   * We are fetching the next page data from the api
   * and passing it via the callback method to the UI.
   * The "params.key" variable will have the updated value.
   */
  @Override
  public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, Image> callback) {
    Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize);
    networkState.postValue(NetworkState.LOADING);
    ApiClient.getInstance().getInfo().enqueue(new Callback<MainResponse>() {
      @Override
      public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
        if (response.isSuccessful() && response.code() == 200) {
          MainResponse result = response.body();
          List<Image> list = result != null ? result.getImages() : Collections.<Image>emptyList();
          callback.onResult(list, (params.key) + 1);
          networkState.postValue(NetworkState.LOADED);

        } else {
          initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
          networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
        }

      }

      @Override
      public void onFailure(Call<MainResponse> call, Throwable t) {

        String errorMessage = t == null ? "unknown error" : t.getMessage();
        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
      }
    });
  }


  public MutableLiveData getNetworkState() {
    return networkState;
  }



  public MutableLiveData getInitialLoading() {
    return initialLoading;
  }


}
