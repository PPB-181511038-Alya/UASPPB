package com.uasppb.resto;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;
import com.uasppb.resto.adapters.RestoAdapter;
import com.uasppb.resto.adapters.ReviewAdapter;
import com.uasppb.resto.model.RestoItem;
import com.uasppb.resto.model.RestoItem_;
import com.uasppb.resto.model.Review;
import com.uasppb.resto.model.Review_;
import com.uasppb.resto.networking.RestoApi;
import com.uasppb.resto.networking.RetrofitService;
import com.uasppb.resto.viewmodels.RestoViewModel;
import com.uasppb.resto.viewmodels.ReviewViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestoDetailActivity extends AppCompatActivity {
    private final static String TAG = RestoDetailActivity.class.getSimpleName();

    private static Retrofit retrofit = null;
    private Toolbar toolbar;
    private TextView restoName, restoRangePrice, restoLoc, restoCurrency, restoLong, restoLat, restoRating;
    private ImageView restoImage;
    private RecyclerView rvReviews;
    private ReviewAdapter reviewAdapter;
    private ReviewViewModel reviewViewModel;
    ArrayList<Review_> arrayReviews = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto_detail);

        //Find views
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        restoImage = (ImageView) findViewById(R.id.resto_image);
        restoName = (TextView) findViewById(R.id.resto_name);
        restoRangePrice = (TextView) findViewById(R.id.resto_range_price);
        restoCurrency = (TextView) findViewById(R.id.resto_currency);
        restoLoc = (TextView) findViewById(R.id.address);
        restoLong = (TextView) findViewById(R.id.resto_long);
        restoLat = (TextView) findViewById(R.id.resto_lat);
        restoRating = (TextView) findViewById(R.id.zomato_star);


//        rvReviews = (RecyclerView) findViewById(R.id.review_rv);
//        List<RestoItem_> restoItems = restoResponse.getRestaurants();
//        arrayRestoItems.addAll(restoItems);

        Intent iGet = getIntent();
        int restoId = iGet.getIntExtra("restoId", 0);
        RetrofitService.createService(RestoApi.class).getRestaurant(restoId).enqueue(new Callback<RestoItem>() {
            @Override
            public void onResponse(Call<RestoItem> call, Response<RestoItem> response) {
                final RestoItem restoItem_ = response.body();
                Log.d("detail", response.body().toString());
                renderResto(restoItem_);

            }

            @Override
            public void onFailure(Call<RestoItem> call, Throwable t) {
                Log.e(TAG, t.toString());
                Toast.makeText(getApplicationContext(), "Error loading!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void renderResto(RestoItem resto) {
        String thumb = resto.getFeaturedImage();

        try {
            if (!TextUtils.isEmpty(thumb))
                Picasso.get().load(thumb)
                        .fit()
                        .centerCrop()
                        .placeholder(R.drawable.ee_min)
                        .into(restoImage);
            else {
                restoImage.setImageDrawable(this.getDrawable(R.drawable.ee_min));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String test = resto.getPriceRange().toString();
        Log.e("hello", test);
        restoName.setText(resto.getName());
        restoRangePrice.setText(resto.getPriceRange().toString());
        restoCurrency.setText(resto.getCurrency());
        restoLoc.setText(resto.getLocation().getAddress());
        restoLong.setText(resto.getLocation().getLongitude());
        restoLat.setText(resto.getLocation().getLatitude());

        reviewViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
        reviewViewModel.init(resto.getId());
        reviewViewModel.getReviewRepository().observe(this, reviewResponse -> {
            List<Review_> reviews = reviewResponse.getUserReviews();
            arrayReviews.addAll(reviews);
            Log.e("test", arrayReviews.toString());
//            reviewAdapter.notifyDataSetChanged();
        });

//        setupRecyclerView();
    }

    private void setupRecyclerView() {

        if (reviewAdapter == null) {
            reviewAdapter = new ReviewAdapter(this, arrayReviews);
            rvReviews.setLayoutManager(new LinearLayoutManager(this));
            rvReviews.setAdapter(reviewAdapter);
            rvReviews.setItemAnimator(new DefaultItemAnimator());
            rvReviews.setNestedScrollingEnabled(true);
        } else {
            reviewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_resto_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_share:
//                String formattedString = android.text.Html.fromHtml(overview).toString();
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
//                share.putExtra(Intent.EXTRA_TITLE, restoTitle);
                share.putExtra(Intent.EXTRA_PACKAGE_NAME, getPackageName());
//                share.putExtra(Intent.EXTRA_TEXT, formattedString);
                share.setType("text/plain");
                startActivity(share);
                break;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
