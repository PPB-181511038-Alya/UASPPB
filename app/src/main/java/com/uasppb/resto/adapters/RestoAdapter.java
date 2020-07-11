package com.uasppb.resto.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uasppb.resto.R;
import com.uasppb.resto.model.RestoItem;

import java.util.ArrayList;

public class RestoAdapter extends RecyclerView.Adapter<RestoAdapter.RestoViewHolder>{
    Context context;
    ArrayList<RestoItem> restoItems;

    public RestoAdapter(Context context, ArrayList<RestoItem> restoItems) {
        this.context = context;
        this.restoItems = restoItems;
    }

    @NonNull
    @Override
    public RestoAdapter.RestoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_resto_item, parent, false);
        return new  RestoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestoAdapter.RestoViewHolder holder, int position) {
        Picasso.get().load(restoItems.get(position).getPhotosUrl()).into(holder.restoImage);
        holder.restoName.setText(restoItems.get(position).getName());
        holder.restoPriceRange.setText(restoItems.get(position).getPriceRange().toString());
        holder.restoCurrency.setText(restoItems.get(position).getCurrency());
        holder.restoRating.setText(restoItems.get(position).getUserRating().getAggregateRating().toString());
        holder.restoOnlineOrder.setText(restoItems.get(position).getHasOnlineDelivery());

    }

    @Override
    public int getItemCount() {
        return restoItems.size();
    }

    public class RestoViewHolder extends RecyclerView.ViewHolder{

        ImageView restoImage;
        TextView restoName;
        TextView restoPriceRange;
        TextView restoCurrency;
        TextView restoRating;
        TextView restoOnlineOrder;

        public RestoViewHolder(@NonNull View itemView) {
            super(itemView);

            restoImage = itemView.findViewById(R.id.resto_image);
            restoName = itemView.findViewById(R.id.resto_name);
            restoPriceRange = itemView.findViewById(R.id.resto_range_price);
            restoCurrency = itemView.findViewById(R.id.resto_currency);
            restoRating = itemView.findViewById(R.id.resto_rating);
            restoOnlineOrder = itemView.findViewById(R.id.resto_online_order);

        }
    }
}
