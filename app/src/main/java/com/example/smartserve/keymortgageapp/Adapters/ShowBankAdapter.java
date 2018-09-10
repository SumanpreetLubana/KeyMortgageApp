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
import com.example.smartserve.keymortgageapp.Models.ShowBankModel;
import com.example.smartserve.keymortgageapp.R;
import com.example.smartserve.keymortgageapp.Util.AsyncResult;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jwick on 8/20/18.
 */

public class ShowBankAdapter extends RecyclerView.Adapter<ShowBankAdapter.MyViewHolder>{
    private List<ShowBankModel> nList;
    AsyncResult<ShowBankModel> asyncResult_addNewConnection;
    Context context;


    public ShowBankAdapter(Context context, List<ShowBankModel> nList, AsyncResult<ShowBankModel> asyncResult_addNewConnection ) {
        this.context=context;
        this.nList = nList;
        this.asyncResult_addNewConnection=asyncResult_addNewConnection;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text,Email,Branch;
        CircleImageView circleImageView;
        ImageView imageView;
        //LinearLayout move;
        public MyViewHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.social_name);
            Email = (TextView) view.findViewById(R.id.Email);
            Branch = (TextView) view.findViewById(R.id.Branch);
            circleImageView = (CircleImageView) view.findViewById(R.id.profile_image);
            imageView = (ImageView) view.findViewById(R.id.delete);
         //   move = (LinearLayout) view.findViewById(R.id.move);
        }
    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.showbank_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        ShowBankModel notif = nList.get(position);

       holder.text.setText(notif.getBankName());
        holder.Email.setText(notif.getUseremail());
        holder.Branch.setText(notif.getUserbankBranch());
     //   Glide.with(context).load(notif.getBankLogo()).into(holder.circleImageView);
        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.bank_default)
                        .error(R.drawable.bank_default))
                .load(notif.getBankLogo())
                .into(holder.circleImageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
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
