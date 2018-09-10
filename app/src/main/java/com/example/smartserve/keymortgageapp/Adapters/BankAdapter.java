package com.example.smartserve.keymortgageapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartserve.keymortgageapp.Models.BankModel;
import com.example.smartserve.keymortgageapp.R;
import com.example.smartserve.keymortgageapp.Util.AsyncResult;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jwick on 8/20/18.
 */

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.MyViewHolder>{
    private List<BankModel> nList;
    AsyncResult<BankModel> asyncResult_addNewConnection;
    Context context;


    public BankAdapter(Context context,List<BankModel> nList, AsyncResult<BankModel> asyncResult_addNewConnection ) {
        this.context=context;
        this.nList = nList;
        this.asyncResult_addNewConnection=asyncResult_addNewConnection;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        CircleImageView circleImageView;
        ImageView imageView;
        LinearLayout move;
        public MyViewHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.social_name);
            circleImageView = (CircleImageView) view.findViewById(R.id.profile_image);
            imageView = (ImageView) view.findViewById(R.id.back_image);
            move = (LinearLayout) view.findViewById(R.id.move);
        }
    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bank_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        BankModel notif = nList.get(position);

       holder.text.setText(notif.getBankName());
    //    Glide.with(context).load(notif.getBankLogo()).into(holder.circleImageView);
        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.bank_default)
                        .error(R.drawable.bank_default))
                .load(notif.getBankLogo())
                .into(holder.circleImageView);
        holder.move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asyncResult_addNewConnection.success(nList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return nList.size();
    }
}
