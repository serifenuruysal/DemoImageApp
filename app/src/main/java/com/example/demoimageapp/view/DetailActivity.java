package com.example.demoimageapp.view;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.demoimageapp.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    ImageView image=findViewById(R.id.imageView);
    Bundle extra = getIntent().getExtras();
    if (extra != null){
      String url = (String) extra.get("url"); // get a object
      Picasso.get()
          .load(url)
          .placeholder(R.drawable.ic_car)
          .error(R.drawable.ic_404)
          .fit().centerInside()
          .into(image);
    }


  }

}
