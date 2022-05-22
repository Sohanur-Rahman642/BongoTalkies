package com.example.bongotalkies.ui.movielist;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.bongotalkies.R;
import com.example.bongotalkies.databinding.ActivityMovieListBinding;
import com.example.bongotalkies.model.MovieModel;
import com.example.bongotalkies.repo.MovieListRepository;
import com.example.bongotalkies.ui.adapter.MovieListAdapter;
import com.example.bongotalkies.ui.details.MovieDetailsActivity;
import com.example.bongotalkies.utils.ConnectivityUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MovieListActivity extends AppCompatActivity {

    private ActivityMovieListBinding binding;

    private MovieListViewModel movieListViewModel;
    private MovieListRepository movieListRepository;
    private MovieListAdapter movieListAdapter;
    private ConnectivityUtils connectivityUtils;

    private static int page = 1;

    private ScheduledExecutorService scheduler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMovieListBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        connectivityUtils = ConnectivityUtils.getInstance(MovieListActivity.this);

        movieListRepository = new MovieListRepository();

        MovieListViewModel.Factory factory = new MovieListViewModel.Factory(movieListRepository);

        movieListViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factory)
                .get(MovieListViewModel.class);


        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate
                (new Runnable() {
                    public void run() {
                        showNetworkState();
                    }
                }, 5, 10, TimeUnit.SECONDS);


        observeMovieListFetchTask();
    }

    private void showNetworkState() {
        if(!connectivityUtils.isOnline()){
            Snackbar.make(binding.getRoot(), R.string.error_network_fail,
                    Snackbar.LENGTH_LONG)
                    .show();
        }
    }


    private void observeMovieListFetchTask() {
        if(connectivityUtils.isOnline()){
            movieListViewModel.getListOfMovies(
                    page
            ).observe(this, movieModels -> {
                if(movieModels != null){
                   // Log.e(TAG, "onChanged: movieModels " +movieModels);

                    if(movieModels != null){
                        movieListAdapter = new MovieListAdapter(MovieListActivity.this, R.layout.item_list_movies, movieModels);
                        movieListAdapter.setOnItemClickListener(movieItemClickListener);
                        binding.movieListGridView.setAdapter(movieListAdapter);
                        binding.spinLayout.setVisibility(View.GONE);
                        binding.movieListGridView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }else {
          //  Log.e(TAG, "observeMovieListFetchTask: offline" );
            binding.spinLayout.setVisibility(View.VISIBLE);
            binding.movieListGridView.setVisibility(View.GONE);
        }
    }


    public MovieListAdapter.MovieItemClickListener movieItemClickListener = new MovieListAdapter.MovieItemClickListener() {
        @Override
        public void onMovieItemClicked(MovieModel movieModel) {
            Log.e(TAG, "onMovieItemClicked: movieModel id " +movieModel.getId());
            Intent intent = new Intent(MovieListActivity.this, MovieDetailsActivity.class)
                    .putExtra("movieId", movieModel.getId());
            startActivity(intent);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}