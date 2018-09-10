package com.example.smartserve.keymortgageapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartserve.keymortgageapp.Models.ImagesModel;
import com.example.smartserve.keymortgageapp.R;
import com.example.smartserve.keymortgageapp.Util.AsyncResult;

import java.util.ArrayList;


public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.MyViewHolder> {

    private ArrayList<ImagesModel> imgList;
    AsyncResult<ImagesModel> asyncResult;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public Button cancel;
        public ImageView image;
        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.img);
            cancel = (Button) view.findViewById(R.id.cancel);
        }
    }


    public ImagesAdapter(ArrayList<ImagesModel> imgList, AsyncResult<ImagesModel> asyncResult) {
        this.imgList = imgList;
        this.asyncResult=asyncResult;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ImagesModel movie = imgList.get(position);
        holder.image.setImageBitmap(movie.getBm());

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Size"," "+imgList.size());
                asyncResult.success(imgList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }
}