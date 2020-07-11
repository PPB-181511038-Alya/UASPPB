package com.uasppb.resto;

import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class ActivityRestoDetails extends AppCompatActivity {
    private Context context;
    private Toolbar toolbar;
    private TextView resto_name, price, address, zomato_meters, zomato_stars, rv;
    private ImageView img_resto, resto_flag, img_map;
    private RecyclerView review;
    private String title, overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto_details);
        this.context = getApplicationContext();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        img_map = (ImageView) findViewById(R.id.maps);
        img_resto = (ImageView) findViewById(R.id.resto_image);
        resto_flag = (ImageView) findViewById(R.id.resto_flag);
        resto_name = (TextView) findViewById(R.id.resto_name);
        price = (TextView) findViewById(R.id.resto_range_price);
        address = (TextView) findViewById(R.id.address);
        zomato_meters = (TextView) findViewById(R.id.zomato_meters);
        zomato_stars = (TextView) findViewById(R.id.zomato_star);
        rv = (TextView) findViewById(R.id.review);
        review = (RecyclerView) findViewById(R.id.review_rv);

    }
}