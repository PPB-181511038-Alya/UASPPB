package com.uasppb.resto.networking;

import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;

import com.uasppb.resto.R;
import com.uasppb.resto.model.RestoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestoRepository {
    private static RestoRepository restoRepository;

    public static RestoRepository getInstance(){
        if (restoRepository == null){
            restoRepository = new RestoRepository();
        }
        return restoRepository;
    }

    private RestoApi restoApi;

    public RestoRepository(){
        restoApi = RetrofitService.createService(RestoApi.class);
    }

    public MutableLiveData<RestoResponse> getRestaurant(String sort){
        final MutableLiveData<RestoResponse> restoData = new MutableLiveData<>();
        restoApi.getRestaurants(sort).enqueue(new Callback<RestoResponse>() {
            @Override
            public void onResponse(Call<RestoResponse> call,
                                   Response<RestoResponse> response) {
                if (response.isSuccessful()){
                    restoData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RestoResponse> call, Throwable t) {
                restoData.setValue(null);
                Log.d("error fetch data",t.getMessage());
            }
        }
    );
        return restoData;
    }
}
