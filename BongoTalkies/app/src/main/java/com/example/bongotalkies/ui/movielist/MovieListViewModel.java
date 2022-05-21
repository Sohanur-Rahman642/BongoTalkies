package com.example.bongotalkies.ui.movielist;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bongotalkies.constants.Constants;
import com.example.bongotalkies.model.MovieModel;
import com.example.bongotalkies.repo.MovieListRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel{

    private MovieListRepository movieListRepository;

    private MutableLiveData<List<MovieModel>> mutableLiveData;

    public MovieListViewModel(MovieListRepository movieListRepository) {
        this.movieListRepository = movieListRepository;
    }

    public LiveData<List<MovieModel>> getListOfMovies(int page){
        if(mutableLiveData == null){
            mutableLiveData = movieListRepository.getListOfMovies(
                    Constants.API_KEY,
                    Constants.LANGUAGE,
                    page
            );
        }

        return mutableLiveData;
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final MovieListRepository movieListRepository;


        public Factory(MovieListRepository movieListRepository) {
            this.movieListRepository = movieListRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MovieListViewModel(movieListRepository);
        }
    }
}
