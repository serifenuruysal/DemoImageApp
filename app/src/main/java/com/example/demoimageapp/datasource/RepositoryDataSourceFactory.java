package com.example.demoimageapp.datasource;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
/**
 * Created by nuruysal on 09,February,2019
 */
public class RepositoryDataSourceFactory extends DataSource.Factory {
  private static final String TAG = RepositoryDataSourceFactory.class.getSimpleName();
  RepositoryDataSource dataSource;
  MutableLiveData<RepositoryDataSource> mutableLiveData;

  public RepositoryDataSourceFactory() {

    mutableLiveData = new MutableLiveData<>();
  }

  @Override
  public DataSource create() {
    Log.d(TAG, "create: ");
    dataSource = new RepositoryDataSource();
    mutableLiveData.postValue(dataSource);
    return dataSource;
  }

  public MutableLiveData<RepositoryDataSource> getMutableLiveData() {
    return mutableLiveData;
  }
}
