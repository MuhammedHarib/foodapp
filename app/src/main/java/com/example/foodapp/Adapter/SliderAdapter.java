package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodapp.Domain.SliderItems;
import com.example.foodapp.R;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private final List<SliderItems> sliderItems;
    private Context context;

    public SliderAdapter(List<SliderItems> sliderItems) {
        // List already prepared (with fake items if needed)
        this.sliderItems = sliderItems;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.slider_viewholder, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {

        RequestOptions requestOptions = new RequestOptions()
                .transform(new CenterCrop(), new RoundedCorners(60));

        Glide.with(context)
                .load(sliderItems.get(position).getImage())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(requestOptions)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    /* -------------------- VIEW HOLDER -------------------- */

    public static class SliderViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlider);
        }
    }
}
