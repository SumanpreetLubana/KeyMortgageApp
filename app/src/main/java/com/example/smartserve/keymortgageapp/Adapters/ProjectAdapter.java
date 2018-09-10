package com.example.smartserve.keymortgageapp.Adapters;

import android.content.Context;
import android.media.projection.MediaProjectionManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartserve.keymortgageapp.CustomImageView;
import com.example.smartserve.keymortgageapp.Models.ImagesModel;
import com.example.smartserve.keymortgageapp.Models.PropertyModel;
import com.example.smartserve.keymortgageapp.R;
import com.example.smartserve.keymortgageapp.Util.AsyncResult;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyViewHolder> {
    private List<PropertyModel> nList;
    Context context;
    AsyncResult<PropertyModel> asyncResult;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, location, cat, cost, size, description;
        LinearLayout line;
       // ImageView imageView;
        //CustomImageView imageView;
        RoundedImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            location = (TextView) view.findViewById(R.id.location);
            cat = (TextView) view.findViewById(R.id.category);
            size = (TextView) view.findViewById(R.id.cost_amount);
            description = (TextView) view.findViewById(R.id.cost_amount);
            imageView =  (RoundedImageView ) view.findViewById(R.id.imageView);
            line =  ( LinearLayout) view.findViewById(R.id.linear);
        }
    }


    public ProjectAdapter(Context context, List<PropertyModel> nList, AsyncResult<PropertyModel> asyncResult) {
        this.nList = nList;
        this.context = context;
        this.asyncResult=asyncResult;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_projects, parent, false);

        return new MyViewHolder(itemView);

    }

    public void setAdapterData(List<PropertyModel> nList){
        this.nList=nList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        PropertyModel notif = nList.get(position);
        //  name,location,cat,cost,size,description;
        holder.name.setText(notif.getName());
        holder.location.setText(notif.getLocation());
        holder.size.setText(notif.getSize());
        holder.cat.setText(notif.getCat_name());
     //   iconImage.setImageBitmap(bitmap);
      //  holder.description.setText(notif.getDescription());
        /*if(notif.getProject_image().equals(""))
        {

        }*/
       // Glide.with(context).load(notif.getProject_image()).into(holder.imageView);

        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.property_default)
                        .error(R.drawable.property_default))
                .load(notif.getProject_image())
                .into(holder.imageView);
        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Size"," "+nList.size());
                asyncResult.success(nList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return nList.size();
    }
}
