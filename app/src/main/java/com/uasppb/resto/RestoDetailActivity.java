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
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import com.uasppb.resto.networking.RestoApi;
import com.uasppb.resto.networking.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestoDetailActivity extends AppCompatActivity {
    private final static String TAG = RestoDetailActivity.class.getSimpleName();

    private static Retrofit retrofit = null;
    private Toolbar toolbar;
    private TextView restoName;
    private TextView restoRangePrice;
    private TextView restoLoc;
    private TextView restoCurrency;
    private TextView restoLong;
    private TextView restoLat;
    private ImageView restoImage;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView rvReviews;
    private AppBarLayout appBarLayout;
    private String restoTitle, reviews;
    private RestoApi restoApi;
    private ReviewAdapter reviewAdapter;
    ArrayList<Review> arrayReviews = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto_detail);

        //Find views
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        restoImage = (ImageView) findViewById(R.id.resto_image_detail);
        restoName = (TextView) findViewById(R.id.resto_name);
        restoRangePrice = (TextView) findViewById(R.id.resto_range_price);
        restoCurrency = (TextView) findViewById(R.id.resto_currency_detail);
        restoLoc = (TextView) findViewById(R.id.resto_location);
        restoLong = (TextView) findViewById(R.id.resto_long);
        restoLat = (TextView) findViewById(R.id.resto_lat);

        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        rvReviews = (RecyclerView) findViewById(R.id.rv_reviews);

//        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        rvReviews.setHasFixedSize(true);
        rvReviews.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        Intent iGet = getIntent();
        int restoId = iGet.getIntExtra("restoId", 0);
//        Log.d("resid", restoId.toString());
        RetrofitService.createService(RestoApi.class).getRestaurant(restoId).enqueue(new Callback<RestoItem>() {
            @Override
            public void onResponse(Call<RestoItem> call, Response<RestoItem> response) {
                final RestoItem restoItem_ = response.body();
                Log.d("detail", response.body().toString());
                restoTitle = restoItem_.getName();

                if (actionBar != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setTitle(restoTitle);
                }

                appBarLayout.setExpanded(true);

                // hiding & showing the title when toolbar expanded & collapsed
                appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    boolean isShow = false;
                    int scrollRange = -1;

                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        if (scrollRange == -1) {
                            scrollRange = appBarLayout.getTotalScrollRange();
                        }
                        if (scrollRange + verticalOffset == 0) {
                            collapsingToolbarLayout.setTitle(restoTitle);
                            isShow = true;
                        } else if (isShow) {
                            collapsingToolbarLayout.setTitle(restoTitle);
                            isShow = false;
                        }
                    }
                });
                collapsingToolbarLayout.setTitle(restoTitle);
                renderResto(restoItem_);
                progressBar.setVisibility(View.GONE);
                coordinatorLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<RestoItem> call, Throwable t) {
                Log.e(TAG, t.toString());
                progressBar.setVisibility(View.GONE);
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
//        restoName.setText(resto.getR().getRestoId());
//        restoName.setText(thumb);
//        restoName.setText(resto.getName());
//        restoRangePrice.setText(resto.getPriceRange().toString());
//        restoLoc.setText(resto.getLocation().getAddress());
//        restoLong.setText(resto.getLocation().getLongitude());
//        restoLat.setText(resto.getLocation().getLatitude());

        setupRecyclerView();

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
                share.putExtra(Intent.EXTRA_TITLE, restoTitle);
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
