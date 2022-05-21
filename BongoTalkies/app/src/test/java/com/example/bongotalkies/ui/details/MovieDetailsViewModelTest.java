package com.example.bongotalkies.ui.details;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.bongotalkies.constants.Constants;
import com.example.bongotalkies.model.MovieModel;
import com.example.bongotalkies.repo.MovieDetailsRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MovieDetailsViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    MovieDetailsViewModel movieDetailsViewModel;

    @Mock
    MovieDetailsRepository movieDetailsRepository;

    @Mock
    MutableLiveData<MovieModel> mutableLiveData;



    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        movieDetailsViewModel = new MovieDetailsViewModel(movieDetailsRepository);
    }

    @Test
    public void testSingleGetMovieDetailsCall() {
        movieDetailsViewModel.getMovieDetails(1);
        verify(movieDetailsRepository,times(1)).getMovieDetails(
                1,
                Constants.API_KEY,
                Constants.LANGUAGE
        );
    }

    @Test
    public void testMultipleGetMovieDetailsCall() {
        movieDetailsViewModel.getMovieDetails(3);
        verify(movieDetailsRepository,times(1)).getMovieDetails(
                1,
                Constants.API_KEY,
                Constants.LANGUAGE
        );
    }

    @Test
    public void getRating() {
    }
}