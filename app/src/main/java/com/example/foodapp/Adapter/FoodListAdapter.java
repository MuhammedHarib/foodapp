package com.example.foodapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.foodapp.Activity.DetailActivity;
import com.example.foodapp.Domain.Foods;
import com.example.foodapp.R;

import java.util.ArrayList;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.viewholder> {
    ArrayList<Foods> items;
    Context context;

    public FoodListAdapter(ArrayList<Foods> item) {
        this.items = items;
    }

    @NonNull
    @Override
    public FoodListAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        return new viewholder(LayoutInflater.from(context).inflate(R.layout.viewholder_list_food,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter.viewholder holder, int position) {

        holder.titletxt.setText(items.get(position).getTitle());
        holder.timetxt.setText(items.get(position).getTimeValue() +"min");
        holder.pricetxt.setText("$"+items.get(position).getPrice());
        holder.ratingtxt.setText(""+items.get(position).getStar());

        Glide.with(context)
                .load(items.get(position).getImagePath())
                .transform(new CenterCrop(),new RoundedCorners(50))
                .into(holder.pic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("object",items.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView titletxt,ratingtxt,pricetxt,timetxt;
        ImageView pic;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            timetxt=itemView.findViewById(R.id.titlletxt);
            ratingtxt=itemView.findViewById(R.id.ratingtxt);
            pricetxt=itemView.findViewById(R.id.pricetxt);
            titletxt=itemView.findViewById(R.id.titlletxt);
            pic=itemView.findViewById(R.id.pic);
        }
    }
}
