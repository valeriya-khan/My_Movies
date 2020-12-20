package com.demo.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.demo.mymovies.adapters.MovieAdapter;
import com.demo.mymovies.data.FavouriteMovie;
import com.demo.mymovies.data.MainViewModel;
import com.demo.mymovies.data.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {
    private RecyclerView recyclerViewFavouriteMovies;
    private MovieAdapter movieAdapter;
    private MainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        recyclerViewFavouriteMovies = findViewById(R.id.recycleViewFavouriteMovies);
        movieAdapter = new MovieAdapter();
        recyclerViewFavouriteMovies.setLayoutManager(new GridLayoutManager(this,2));
        recyclerViewFavouriteMovies.setAdapter(movieAdapter);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        LiveData<List<FavouriteMovie>> favouriteMovies = viewModel.getFavouriteMovies();

        favouriteMovies.observe(this, new Observer<List<FavouriteMovie>>() {
            @Override
            public void onChanged(List<FavouriteMovie> favouriteMovies) {
                List<Movie> movies = new ArrayList<>();
                if(favouriteMovies!=null){
                    movies.addAll(favouriteMovies);
                    Log.i("myres", String.valueOf(movies.size()));
                    movieAdapter.setMovies(movies);
                }

            }
        });
        movieAdapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                Movie movie = movieAdapter.getMovies().get(position);
                Log.i("myres",movie.getBigPosterPath());
                Intent intent = new Intent(FavouriteActivity.this,DetailActivity.class);
                Log.i("myres", String.valueOf(movie.getId()));
                intent.putExtra("id",movie.getId());
                intent.putExtra("source","FavouriteActivity");
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itemMain:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.itemFavourite:
                Intent intentToFavourite = new Intent(this, FavouriteActivity.class);
                startActivity(intentToFavourite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}