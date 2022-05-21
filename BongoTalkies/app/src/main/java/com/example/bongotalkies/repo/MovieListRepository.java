package com.example.bongotalkies.repo;

import com.example.bongotalkies.application.BongoTalkies;
import com.example.bongotalkies.server.ApiInterface;

import retrofit2.Callback;

public class MovieListRepository {

    private final String TAG = getClass().getSimpleName();

    ApiInterface apiInterface = BongoTalkies.getRetrofitClient().create(ApiInterface.class);


}
