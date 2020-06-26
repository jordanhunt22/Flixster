package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.R;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

// Pops up when a movie is clicked and displays the ratings + other information
public class MovieDetailsActivity extends AppCompatActivity {

    // The movie to display
    Movie movie;

    // The view objects
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Implement ViewBinding
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Resolve the view objects
        tvTitle = binding.tvTitle;
        tvOverview = binding.tvOverview;
        rbVoteAverage = binding.rbVoteAverage;

        // Unwrap the movie passed in via intent, using its simple name as a key
        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // Set the title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        // Vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);
    }

    public void onClick(View v) {
            // Create intent for the new activity
            Intent intent = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
            // Serialize the movie using parceler, use its short name as a key
            intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
            // Show the activity
            MovieDetailsActivity.this.startActivity(intent);
        }
}