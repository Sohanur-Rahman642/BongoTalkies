package com.example.bongotalkies.ui.movielist;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.bongotalkies.constants.Constants;
import com.example.bongotalkies.model.MovieModel;
import com.example.bongotalkies.repo.MovieListRepository;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class MovieListViewModelTest extends TestCase {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    MovieListViewModel movieListViewModel;

    @Mock
    MovieListRepository movieListRepository;

    @Mock
    MutableLiveData<List<MovieModel>> mutableLiveData;


    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        movieListViewModel = new MovieListViewModel(movieListRepository);
        mutableLiveData = new MutableLiveData<>();
        super.setUp();
    }

    //Expected Result == Passed
    @Test
    public void testCallSingleGetListOfMovies() {
        movieListViewModel.getListOfMovies(1);
        verify(movieListRepository).getListOfMovies(
                Constants.API_KEY,
                Constants.LANGUAGE,
                1
        );
    }

    public void testGetListOfMovies() {

        when(movieListRepository.getListOfMovies(
                Constants.API_KEY,
                Constants.LANGUAGE,
                1
        )).thenReturn(mutableLiveData);


        assertEquals(mutableLiveData, movieListRepository.getListOfMovies(
                Constants.API_KEY,
                Constants.LANGUAGE,
                1
        ));
    }
}