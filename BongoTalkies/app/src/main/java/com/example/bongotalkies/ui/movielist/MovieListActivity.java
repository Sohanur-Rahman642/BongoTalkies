package com.example.bongotalkies.ui.movielist;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.bongotalkies.R;
import com.example.bongotalkies.databinding.ActivityMovieListBinding;
import com.example.bongotalkies.repo.MovieListRepository;

public class MovieListActivity extends AppCompatActivity {

    private ActivityMovieListBinding binding;

    private MovieListViewModel movieListViewModel;
    private MovieListRepository movieListRepository;
    //private MovieListAdapter movieListAdapter;

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
            }
        });
    }
}