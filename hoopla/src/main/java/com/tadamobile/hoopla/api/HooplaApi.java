package com.tadamobile.hoopla.api;

import java.util.List;

import com.tadamobile.hoopla.value.Movie;
import com.tadamobile.hoopla.value.MovieCollection;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface HooplaApi {

    @GET("/kinds/7/collections")
	void getMovieCollections(Callback<List<MovieCollection>> callback);

    @GET("/collections/{collectionId}/titles")
    void getCollectionMovies(@Path("collectionId") int collectionId, Callback<List<Movie>> callback);
}
