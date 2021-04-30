package com.example.mapcovid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mapcovid.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter {

    ArrayList<String> dates;
    ArrayList<String> longnums;
    ArrayList<String> latnums;
    Context context;

    public CustomAdapter(ArrayList<String> longnums, ArrayList<String> latnums, ArrayList<String> dates, Context context){
        this.longnums = longnums;
        this.dates = dates;
        this.latnums = latnums;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        ViewHolder myViewHolder = (ViewHolder)holder;
        myViewHolder.textlong.setText("Longitude: " + longnums.get(position));
        myViewHolder.textlat.setText("Latitude: " + latnums.get(position));
        myViewHolder.textdate.setText(dates.get(position));

    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textlong;
        TextView textlat;
        TextView textdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textlong=itemView.findViewById(R.id.textlong);
            textlat=itemView.findViewById(R.id.textlat);
            textdate=itemView.findViewById(R.id.textdate);
        }
    }


}
