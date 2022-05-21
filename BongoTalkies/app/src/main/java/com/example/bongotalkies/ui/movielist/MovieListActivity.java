package com.example.bongotalkies.ui.movielist;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.bongotalkies.R;
import com.example.bongotalkies.databinding.ActivityMovieListBinding;
import com.example.bongotalkies.model.MovieModel;
import com.example.bongotalkies.repo.MovieListRepository;
import com.example.bongotalkies.ui.adapter.MovieListAdapter;
import com.example.bongotalkies.ui.details.MovieDetailsActivity;

public class MovieListActivity extends AppCompatActivity {

    private ActivityMovieListBinding binding;

    private MovieListViewModel movieListViewModel;
    private MovieListRepository movieListRepository;
    private MovieListAdapter movieListAdapter;

    private static int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        binding = ActivityMovieListBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        movieListRepository = new MovieListRepository();

        MovieListViewModel.Factory factory = new MovieListViewModel.Factory(movieListRepository);

        movieListViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factory)
                .get(MovieListViewModel.class);


        observeMovieListFetchTask();
    }


    private void observeMovieListFetchTask() {
        movieListViewModel.getListOfMovies(
                page
        ).observe(this, movieModels -> {
            if(movieModels != null){
                Log.e(TAG, "onChanged: movieModels " +movieModels);

                if(movieModels != null){
                    movieListAdapter = new MovieListAdapter(MovieListActivity.this, R.layout.item_list_movies, movieModels);
                    movieListAdapter.setOnItemClickListener(movieItemClickListener);
                    binding.movieListGridView.setAdapter(movieListAdapter);
                    binding.spinLayout.setVisibility(View.GONE);
                    binding.movieListGridView.setVisibility(View.VISIBLE);
                }
            }
        });
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
}