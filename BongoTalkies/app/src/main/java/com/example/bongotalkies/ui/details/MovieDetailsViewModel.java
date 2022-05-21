package com.example.bongotalkies.ui.details;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bongotalkies.constants.Constants;
import com.example.bongotalkies.model.MovieModel;
import com.example.bongotalkies.repo.MovieDetailsRepository;

public class MovieDetailsViewModel extends ViewModel {
    private MovieDetailsRepository movieDetailsRepository;

    private MutableLiveData<MovieModel> mutableLiveData;

    public MovieDetailsViewModel(MovieDetailsRepository movieDetailsRepository) {
        this.movieDetailsRepository = movieDetailsRepository;
    }

    public LiveData<MovieModel> getMovieDetails(Integer movieId){
        if(mutableLiveData == null){
            mutableLiveData = movieDetailsRepository.getMovieDetails(
                    movieId,
                    Constants.API_KEY,
                    Constants.LANGUAGE);
        }

        return mutableLiveData;
    }

    public float getRating(double value){
        return (float) value/2;
    }



    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final MovieDetailsRepository movieDetailsRepository;


        public Factory(MovieDetailsRepository movieDetailsRepository) {
            this.movieDetailsRepository = movieDetailsRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MovieDetailsViewModel(movieDetailsRepository);
        }
    }
}
