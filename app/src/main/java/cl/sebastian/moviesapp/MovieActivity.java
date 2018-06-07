package cl.sebastian.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

import cl.sebastian.moviesapp.models.Movie;

public class MovieActivity extends AppCompatActivity {

    private CheckBox watchedCb;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        TextView nameMovieTv = (TextView) findViewById(R.id.nameMovieTv);
        watchedCb = (CheckBox) findViewById(R.id.watchedCb);

        Intent intent = getIntent();
        long movieid = intent.getLongExtra(MainActivity.MOVIE_ID,0);
        Log.d("TAG",String.valueOf(movieid));

        movie = Movie.findById(Movie.class,movieid);
        nameMovieTv.setText(movie.getName());

        getSupportActionBar().setTitle(movie.getName());

//        if (movie.isWatched()){
//            watchedCb.setChecked(true);
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (watchedCb.isChecked()){
            movie.setWatched(true);
            movie.save();
        }

    }
}
