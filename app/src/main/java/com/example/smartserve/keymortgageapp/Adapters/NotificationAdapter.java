package com.example.smartserve.keymortgageapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartserve.keymortgageapp.Models.NotificationModel;
import com.example.smartserve.keymortgageapp.R;
import com.example.smartserve.keymortgageapp.Util.AsyncResult;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder>{
    private List<NotificationModel> nList;
    AsyncResult<NotificationModel> asyncResult_addNewConnection;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text,type,status,viewMore,typeTxt,statusTxt,newsFeed;

        public MyViewHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.text);
            type = (TextView) view.findViewById(R.id.type);
            status = (TextView) view.findViewById(R.id.status);
            viewMore = (TextView) view.findViewById(R.id.viewMore);
            typeTxt = (TextView) view.findViewById(R.id.type_txt);
            statusTxt = (TextView) view.findViewById(R.id.status_txt);
            newsFeed = (TextView) view.findViewById(R.id.news);
        }
    }


    public NotificationAdapter(List<NotificationModel> nList,AsyncResult<NotificationModel> asyncResult_addNewConnection) {
        this.nList = nList;
        this.asyncResult_addNewConnection = asyncResult_addNewConnection;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_notification, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        NotificationModel notif = nList.get(position);
        if(notif.getIsNotification().equals("1"))
        {
            holder.viewMore.setVisibility(View.VISIBLE);
            holder.status.setVisibility(View.GONE);
            holder.type.setVisibility(View.GONE);
            holder.typeTxt.setVisibility(View.GONE);
            holder.statusTxt.setVisibility(View.GONE);
            holder.text.setText(notif.getText());
            holder.newsFeed.setText("NewsFeed");
            holder.viewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    asyncResult_addNewConnection.success(nList.get(position));
                }
            });

        }else{
            holder.viewMore.setVisibility(View.GONE);
            holder.status.setVisibility(View.GONE);
            holder.typeTxt.setVisibility(View.GONE);
            holder.statusTxt.setVisibility(View.GONE);
            holder.newsFeed.setText("Notification");
            holder.type.setVisibility(View.GONE);
            holder.text.setText(notif.getText());
            holder.type.setText(notif.getType());
            if(notif.getStatus().equals("0"))
            {
                holder.status.setText("Not Approved");
            }
            else
            {
                holder.status.setText("Approved");
            }
        }



    //    holder.year.setText(movie.getYear());
    }

    @Override
    public int getItemCount() {
        return nList.size();
    }
}
