package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

import com.codepath.asynchttpclient.AsyncHttpClient;

// MoviePLayer for movie trailers
public class MovieTrailerActivity extends YouTubeBaseActivity {

    // Movie variable
    Movie movie;
    String tURL;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        // Unwrap Movie
        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        AsyncHttpClient client = new AsyncHttpClient();

        tURL = "https://api.themoviedb.org/3/movie/" + movie.getId() + "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        client.get(tURL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    if (results.length() > 0) {
                        JSONObject movieTrailer = results.getJSONObject(0);
                        key = movieTrailer.getString("key");
                        // resolve the player view from the layout
                        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);

                        // initialize with API key stored in secrets.xml
                        playerView.initialize(getString(R.string.google_API_key), new YouTubePlayer.OnInitializedListener() {
                            @Override
                            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                YouTubePlayer youTubePlayer, boolean b) {
                                // do any work here to cue video, play video, etc.
                                youTubePlayer.cueVideo(key);
                            }

                            @Override
                            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                YouTubeInitializationResult youTubeInitializationResult) {
                                // log the error
                                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                System.out.println("Error");
            }
        });
    }

}