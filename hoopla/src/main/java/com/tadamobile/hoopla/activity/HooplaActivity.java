package com.tadamobile.hoopla.activity;


import java.util.List;

import org.lucasr.twowayview.TwoWayView;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tadamobile.hoopla.R;
import com.tadamobile.hoopla.api.HooplaApi;
import com.tadamobile.hoopla.value.Movie;
import com.tadamobile.hoopla.value.MovieCollection;

public class HooplaActivity extends Activity {

	private ListView movieCollectionsList;
    private HooplaApi hooplaApi;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_hoopla);

		movieCollectionsList = (ListView) findViewById(android.R.id.list);

		RestAdapter restAdapter = new RestAdapter.Builder()
	    .setEndpoint("http://hoopla-ws-dev.hoopladigital.com")
	    .build();

		hooplaApi = restAdapter.create(HooplaApi.class);
		hooplaApi.getMovieCollections(new Callback<List<MovieCollection>>() {
            @Override
            public void success(List<MovieCollection> movieCollections, Response response) {
                movieCollectionsList.setAdapter(new MovieCollectionItemArrayAdapter(HooplaActivity.this, 0, movieCollections));
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Toast.makeText(HooplaActivity.this,"Unable to get Movie Collections",Toast.LENGTH_LONG).show();
            }
        });

	}

	private class MovieCollectionItemArrayAdapter extends ArrayAdapter<MovieCollection> {
	    
		private List<MovieCollection> movieCollectionItems;
	 
	    public MovieCollectionItemArrayAdapter(Context context, int textViewResourceId,
	            List<MovieCollection> theMovieCollectionItems) {
	        super(context, textViewResourceId, theMovieCollectionItems);
	        this.movieCollectionItems = theMovieCollectionItems;
	    }
	 
	    @Override
	    public View getView(int position, View v, ViewGroup parent) {
	        
	    	// Keeps reference to avoid future findViewById()
	        final MovieCollectionItemViewHolder viewHolder;
	 
	        if (v == null) {
	            LayoutInflater li = (LayoutInflater) getContext().getSystemService(
	                    Context.LAYOUT_INFLATER_SERVICE);
	            v = li.inflate(R.layout.row_movie_collection_item, parent, false);
	 
	            viewHolder = new MovieCollectionItemViewHolder();
	            viewHolder.movieCollectionName = (TextView) v.findViewById(R.id.textview_movie_collection_name);
	            viewHolder.lvTest = (TwoWayView) v.findViewById(R.id.lvItems);
	            
	            //set row bg transparency
	            //v.findViewById(R.id.charts_row).getBackground().setAlpha(60);
	 
	            v.setTag(viewHolder);
	        } else {
	            viewHolder = (MovieCollectionItemViewHolder) v.getTag();
	        }
	 
	        final MovieCollection movieCollectionItem = movieCollectionItems.get(position);
	        if (movieCollectionItem != null) {
	            viewHolder.movieCollectionName.setText(movieCollectionItem.name);
	            
	            hooplaApi.getCollectionMovies(movieCollectionItem.id, new Callback<List<Movie>>() {
                    @Override
                    public void success(List<Movie> movies, Response response) {
                        ArrayAdapter<Movie> aItems = new MovieItemArrayAdapter(HooplaActivity.this, 0, movies);
                        viewHolder.lvTest.setAdapter(aItems);
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        Toast.makeText(HooplaActivity.this,"Unable to get Movies for Collection:"+movieCollectionItem.id,Toast.LENGTH_LONG).show();
                    }
                });
	            

	        }
	        return v;
	    }
	 
	     class MovieCollectionItemViewHolder {
	        TextView movieCollectionName;
	        TwoWayView lvTest;
	    }
	     
	     private class MovieItemArrayAdapter extends ArrayAdapter<Movie>
	     {

	 		private List<Movie> movieItems;
		 
		    public MovieItemArrayAdapter(Context context, int textViewResourceId,
		            List<Movie> theMovieItems) {
		        super(context, textViewResourceId, theMovieItems);
		        this.movieItems = theMovieItems;
		    }
		 
		    @Override
		    public View getView(int position, View v, ViewGroup parent) {
		        
		    	// Keeps reference to avoid future findViewById()
		        MovieItemViewHolder viewHolder;
		 
		        if (v == null) {
		            LayoutInflater li = (LayoutInflater) getContext().getSystemService(
		                    Context.LAYOUT_INFLATER_SERVICE);
		            v = li.inflate(R.layout.row_movie, parent, false);
		 
		            viewHolder = new MovieItemViewHolder();
                    viewHolder.thumbnail = (ImageView) v.findViewById(R.id.thumbnail);

		            v.setTag(viewHolder);
		        } else {
		            viewHolder = (MovieItemViewHolder) v.getTag();
		        }
		 
		        final Movie movieItem = movieItems.get(position);
		        if (movieItem != null) {
                   Picasso.with(HooplaActivity.this).load("http://d2snwnmzyr8jue.cloudfront.net/" + movieItem.artKey + "_180.jpeg").into(viewHolder.thumbnail);
		        }
		        return v;
		    }
		 
		     class MovieItemViewHolder {
		    	 ImageView thumbnail;
		    }
	    	 
	     }
	}
}
