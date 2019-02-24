package com.example.demoimageapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import com.example.demoimageapp.R;
import com.example.demoimageapp.adapter.DataRecyclerAdapter;
import com.example.demoimageapp.adapter.DataRecyclerAdapter.DataOnClickListener;
import com.example.demoimageapp.databinding.MainActivityBinding;
import com.example.demoimageapp.models.Image;
import com.example.demoimageapp.viewmodel.ListViewModel;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LifecycleOwner,
    DataOnClickListener {

  public static final String TAG = MainActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final MainActivityBinding binding =
        DataBindingUtil.setContentView(this, R.layout.activity_main);

//    Toolbar toolbar = findViewById(R.id.toolbar);
//    setSupportActionBar(toolbar);
//    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    binding.setViewModel(new ListViewModel(getBaseContext()));
    binding.setLifecycleOwner(this);
    binding.getViewModel().getData();
    binding.getViewModel().getItems().observe(this, new Observer<ArrayList<Image>>() {
      @Override
      public void onChanged(ArrayList<Image> itemsItems) {
        Log.d(TAG, "onChanged: " + itemsItems.size());

        DataRecyclerAdapter adapter = new DataRecyclerAdapter(itemsItems, MainActivity.this);
        binding.rview.setAdapter(adapter);
      }
    });

    binding.rview.setLayoutManager(new GridLayoutManager(this, 2));


  }

  @Override
  public void onPointerCaptureChanged(boolean hasCapture) {

  }


  @Override
  public void onClickItem(String url) {
    Intent myIntent = new Intent(MainActivity.this, DetailActivity.class);
    myIntent.putExtra("url", url); //Optional parameters
    startActivity(myIntent);
  }
}
