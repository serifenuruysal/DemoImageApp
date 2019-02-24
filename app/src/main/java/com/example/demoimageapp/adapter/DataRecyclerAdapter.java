package com.example.demoimageapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demoimageapp.R;
import com.example.demoimageapp.models.Image;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by nuruysal on 06,February,2019
 */
public class DataRecyclerAdapter
    extends RecyclerView.Adapter<DataRecyclerAdapter.ShortcutViewHolder> {

  private ArrayList<Image> mItems;
  private DataOnClickListener mListener;

  public DataRecyclerAdapter(ArrayList<Image> items,
      DataOnClickListener listener) {
    mItems = items;
    mListener = listener;
  }


  @Override
  public ShortcutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.row_layout, parent, false);
    ShortcutViewHolder vh = new ShortcutViewHolder(v);
    return vh;
  }


  @Override
  public void onBindViewHolder(@NonNull ShortcutViewHolder holder, int position) {
    Image image = mItems.get(position);
    final String url = "https://" + image.getUri() + "_" + position + ".jpg";
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.ic_car)
        .error(R.drawable.ic_404)
        .fit().centerInside()
        .into(holder.mImageView);
    holder.mImageView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        mListener.onClickItem(url);
      }
    });

  }

  @Override
  public int getItemCount() {
    return mItems != null ? mItems.size() : 0;
  }


  public void submitList(ArrayList<Image> itemsItems) {
    mItems = itemsItems;
  }

  public class ShortcutViewHolder extends RecyclerView.ViewHolder {

    // each data item is just a string in this case
    public ImageView mImageView;

    public ShortcutViewHolder(View v) {
      super(v);
      mImageView = v.findViewById(R.id.ivItemGridImage);

    }
  }

  public interface DataOnClickListener {

    public void onClickItem(String url);
  }

}
