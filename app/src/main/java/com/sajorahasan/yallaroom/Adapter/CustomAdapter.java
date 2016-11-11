package com.sajorahasan.yallaroom.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sajorahasan.yallaroom.Model.Pojo;
import com.sajorahasan.yallaroom.R;

import java.util.ArrayList;

/**
 * Created by Sajora on 12-11-2016.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Pojo> arrayList = new ArrayList<>();

    public CustomAdapter(ArrayList<Pojo> arrayList) {
        this.arrayList = arrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvAddress, tvPhone, tvDesc, tvImage;

        public ViewHolder(View view) {
            super(view);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAdd);
            tvPhone = (TextView) itemView.findViewById(R.id.tvPhone);
            tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
            tvImage = (TextView) itemView.findViewById(R.id.ivImage);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Pojo pojo = arrayList.get(position);
        final int id = arrayList.get(position).getId();
        holder.tvName.setText(arrayList.get(position).getName());
        holder.tvAddress.setText(arrayList.get(position).getAddress());
        holder.tvPhone.setText(arrayList.get(position).getPhone());
        holder.tvDesc.setText(arrayList.get(position).getDesc());
        holder.tvImage.setText(arrayList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
