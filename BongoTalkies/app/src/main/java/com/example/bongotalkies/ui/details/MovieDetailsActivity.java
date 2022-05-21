package com.example.bongotalkies.ui.details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.bongotalkies.R;
import com.example.bongotalkies.constants.Constants;
import com.example.bongotalkies.databinding.ActivityMovieDetailsBinding;
import com.example.bongotalkies.repo.MovieDetailsRepository;
import com.example.bongotalkies.utils.ConnectivityUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MovieDetailsActivity extends AppCompatActivity {

    private ActivityMovieDetailsBinding binding;
    private Integer movieId;
    private static final String TAG = "MovieDetailsActivity";

    private MovieDetailsRepository movieDetailsRepository;
    private MovieDetailsViewModel movieDetailsViewModel;

    private ScheduledExecutorService scheduler;

    private ConnectivityUtils connectivityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        movieId = getIntent().getIntExtra("movieId", 0);

        Log.e(TAG, "onCreate: movieId " +movieId );

        movieDetailsRepository = new MovieDetailsRepository();

        MovieDetailsViewModel.Factory factory = new MovieDetailsViewModel.Factory(movieDetailsRepository);
        movieDetailsViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factory)
                .get(MovieDetailsViewModel.class);

        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate
                (new Runnable() {
                    public void run() {
                        showNetworkState();
                    }
                }, 5, 30, TimeUnit.SECONDS);

        observeMovieDetailsFetchTask();
    }


    private void observeMovieDetailsFetchTask() {

        movieDetailsViewModel.getMovieDetails(movieId)
                .observe(this, movieModel -> {
                    if(movieModel != null){
                        Log.e(TAG, "observeMovieDetailsFetchTask: movieModel " +movieModel.toString());
                        binding.setMoviemodel(movieModel);

                        binding.detailSpinLayout.setVisibility(View.GONE);

                        binding.mainDetailLayout.setVisibility(View.VISIBLE);

                        binding.ratingBar.setRating(movieDetailsViewModel.getRating(movieModel.getVoteAverage()));

                        setPosterImage(MovieDetailsActivity.this, movieModel.getPosterPath(), binding.imagePoster);

                    }
                });
    }


    public void setPosterImage(Context context, String imageUrl, ImageView view){
        Glide.with(context)
                .load(Constants.BASE_IMAGE_URL+imageUrl)
                .into(view);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showNetworkState() {
        if(!connectivityUtils.isOnline()){
            Snackbar.make(binding.getRoot(), R.string.error_network_fail,
                    Snackbar.LENGTH_LONG)
                    .show();
        }
    }


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