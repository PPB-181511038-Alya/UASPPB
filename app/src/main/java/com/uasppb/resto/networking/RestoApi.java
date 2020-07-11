package com.uasppb.resto.networking;

import com.uasppb.resto.model.RestoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RestoApi {
    @Headers("user-key: 53d890d323b2c6c8fd35f972c216c7c9")
    @GET("search")
    Call<RestoResponse> getRestaurants(@Query("sort") String sort);
}
