package com.example.bongotalkies.repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bongotalkies.application.BongoTalkies;
import com.example.bongotalkies.model.MovieModel;
import com.example.bongotalkies.server.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsRepository {
    private final String TAG = getClass().getSimpleName();

    public MutableLiveData<MovieModel> getMovieDetails(Integer movieId, String apiKey, String language){
        final MutableLiveData<MovieModel> mutableLiveData = new MutableLiveData<>();

        ApiInterface apiInterface = BongoTalkies.getRetrofitClient().create(ApiInterface.class);

        apiInterface.getMovieDetails(movieId, apiKey, language).enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                Log.e(TAG, "onResponse:response  " +response.body());
                if(response.code() == 200 && response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }else {
                    mutableLiveData.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                Log.e(TAG, "onFailure: moviedetails error 404" );
                mutableLiveData.setValue(null);
            }
        });

        return mutableLiveData;
    }
}
