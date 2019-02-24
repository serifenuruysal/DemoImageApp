package com.example.demoimageapp.viewmodel;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.demoimageapp.R;
import com.example.demoimageapp.datasource.RepositoryDataSource;
import com.example.demoimageapp.models.Image;
import com.example.demoimageapp.models.MainResponse;
import com.example.demoimageapp.serverapi.ApiClient;
import com.example.demoimageapp.serverapi.JSONResourceReader;
import com.example.demoimageapp.serverapi.NetworkState;
import java.util.ArrayList;
import java.util.Collections;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListViewModel {

  public static final String TAG = ListViewModel.class.getSimpleName();
  Context mContext;
  MutableLiveData<ArrayList<Image>> items = new MutableLiveData<>();
  MainResponse jsonObj;
  private LiveData<NetworkState> networkStateLiveData;
  private LiveData<RepositoryDataSource> dataSource;

  public ListViewModel(Context baseContext) {
    mContext = baseContext;
//    getData();
//    RepositoryDataSourceFactory factory = new RepositoryDataSourceFactory();
//    dataSource = factory.getMutableLiveData();
//
//    PagedList.Config pageConfig = (new PagedList.Config.Builder())
//        .setEnablePlaceholders(false)
//        .setInitialLoadSizeHint(20)
//        .setPageSize(20).build();
//
//    items = (new LivePagedListBuilder<Long, Image>(factory, pageConfig))
//        .setFetchExecutor(Executors.newFixedThreadPool(5))
//        .build();

  }

  public void getData() {
    Log.d(TAG, "getData: ");

    ApiClient.getInstance().getInfo().enqueue(new Callback<MainResponse>() {
      @Override
      public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
        if (response.isSuccessful() && response.code() == 200) {
          MainResponse result = response.body();
          ArrayList<Image> list =
              (ArrayList<Image>) (result != null ? result.getImages()
                  : Collections.<Image>emptyList());
          items.setValue(list);
        }else
          Log.d(TAG, "response: "+response.code());
      }

      @Override
      public void onFailure(Call<MainResponse> call, Throwable t) {

        String errorMessage = t == null ? "unknown error" : t.getMessage();
        Log.d(TAG, "error: "+errorMessage);
      }
    });
  }
//  public void setUp() {
//    // Load our JSON file.
//    JSONResourceReader
//        reader = new JSONResourceReader(mContext.getResources(), R.raw.jsonfile);
//     jsonObj = reader.constructUsingGson(MainResponse.class);
//     items.setValue((ArrayList<Image>) jsonObj.getImages());
//  }
  public MutableLiveData<ArrayList<Image>> getItems() {
    return items;
  }

  public LiveData<NetworkState> getNetworkStateLiveData() {
    return networkStateLiveData;
  }

}
