package com.uasppb.resto.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.uasppb.resto.R;
import com.uasppb.resto.model.RestoItem;
import com.uasppb.resto.model.RestoItem_;

import java.util.ArrayList;

public class RestoAdapter extends RecyclerView.Adapter<RestoAdapter.RestoViewHolder>{
    Context context;
    ArrayList<RestoItem_> restoItems;

    public RestoAdapter(Context context, ArrayList<RestoItem_> restoItems) {
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
//        Picasso.get().load(restoItems.get(position).getRestaurant().getFeaturedImage()).into(holder.restoImage);
        String thumb = restoItems.get(position).getRestaurant().getFeaturedImage();

        try {
            if (!TextUtils.isEmpty(thumb))
                Picasso.get().load(thumb)
                        .fit()
                        .centerCrop()
                        .placeholder(R.drawable.ee_min)
                        .into(holder.restoImage, new Callback() {
                            @Override
                            public void onSuccess() {
//                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Exception e) {
//                                progressBar.setVisibility(View.GONE);

                            }
                        });
            else {
//                progressBar.setVisibility(View.GONE);
                holder.restoImage.setImageDrawable(context.getDrawable(R.drawable.ee_min));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.restoName.setText(restoItems.get(position).getRestaurant().getName());
        holder.restoPriceRange.setText(restoItems.get(position).getRestaurant().getPriceRange().toString());
        holder.restoCurrency.setText(restoItems.get(position).getRestaurant().getCurrency());
        holder.restoRating.setText(restoItems.get(position).getRestaurant().getUserRating().getAggregateRating().toString());
        holder.restoOnlineOrder.setText(restoItems.get(position).getRestaurant().getHasOnlineDelivery().toString());

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
