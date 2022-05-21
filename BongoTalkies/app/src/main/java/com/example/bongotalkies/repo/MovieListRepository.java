package com.example.bongotalkies.repo;

import androidx.lifecycle.MutableLiveData;

import com.example.bongotalkies.application.BongoTalkies;
import com.example.bongotalkies.model.MovieModel;
import com.example.bongotalkies.model.response.MovieListResponse;
import com.example.bongotalkies.server.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListRepository {

    private final String TAG = getClass().getSimpleName();

    public MutableLiveData<List<MovieModel>> getListOfMovies(String apiKey, String language, int page){

        final MutableLiveData<List<MovieModel>> mutableLiveData = new MutableLiveData<>();

        ApiInterface apiInterface = BongoTalkies.getRetrofitClient().create(ApiInterface.class);

        apiInterface.getListOfMovies(apiKey, language, page).enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                if(response.code() == 200 && response.isSuccessful()){
                    mutableLiveData.setValue(response.body().getResults());
                }else{
                    mutableLiveData.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });

        return mutableLiveData;

    }
}
