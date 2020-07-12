package com.uasppb.resto.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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
                        .placeholder(R.drawable.noimage)
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
                holder.restoImage.setImageDrawable(context.getDrawable(R.drawable.noimage));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.restoName.setText(restoItems.get(position).getRestaurant().getName());
        holder.restoPriceRange.setText(restoItems.get(position).getRestaurant().getPriceRange().toString());
        holder.restoCurrency.setText(restoItems.get(position).getRestaurant().getCurrency());
        holder.restoRating.setText(restoItems.get(position).getRestaurant().getUserRating().getAggregateRating().toString());
        float ratingfloat = Float.valueOf(String.valueOf(restoItems.get(position).getRestaurant().getUserRating().getAggregateRating()));
        holder.ratingBar.setRating(ratingfloat);
        Integer onlineChecker = restoItems.get(position).getRestaurant().getHasOnlineDelivery();
        if(onlineChecker.equals(1)) {
            holder.onlineBadge.setImageDrawable(context.getDrawable(R.drawable.badge));
//            holder.restoOnlineOrder.setText(restoItems.get(position).getRestaurant().getHasOnlineDelivery().toString());
        }



    }

    @Override
    public int getItemCount() {
        return restoItems.size();
    }

    public class RestoViewHolder extends RecyclerView.ViewHolder{

        ImageView restoImage;
        ImageView onlineBadge;
        TextView restoName;
        TextView restoPriceRange;
        TextView restoCurrency;
        TextView restoRating;
        TextView restoOnlineOrder;
        RatingBar ratingBar;


        public RestoViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.ratingbar);
            restoImage = itemView.findViewById(R.id.resto_image);
            restoName = itemView.findViewById(R.id.resto_name);
            restoPriceRange = itemView.findViewById(R.id.resto_range_price);
            restoCurrency = itemView.findViewById(R.id.resto_currency);
            restoRating = itemView.findViewById(R.id.resto_rating);
            onlineBadge = itemView.findViewById(R.id.badge);
//            restoOnlineOrder = itemView.findViewById(R.id.resto_online_order);

        }

    }
}
