package cl.sebastian.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cl.sebastian.moviesapp.models.Movie;

public class MainActivity extends AppCompatActivity {

    private List<Movie> movieList;
    private EditText titleMovieEt;
    public static final String MOVIE_ID = "cl.sebastian.moviesapp.KEY.MOVIE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleMovieEt = (EditText) findViewById(R.id.titleMovieEt);
        Button saveBtn = (Button) findViewById(R.id.saveBtn);
        Button lastMovieBtn = (Button) findViewById(R.id.lastMovieBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titlemovie = titleMovieEt.getText().toString();

                if (titlemovie.trim().length() > 0){

                    List<Movie> checkmovies = Movie.find(Movie.class,"name = ?",titlemovie);

                    if (checkmovies.size() > 0){

                        Toast.makeText(MainActivity.this, "Existe película con ese nombre", Toast.LENGTH_SHORT).show();

                    }else{

                        Movie newmovie = new Movie();
                        newmovie.setName(titlemovie);
                        newmovie.setWatched(false);
                        newmovie.save();

                        movieList = getMovies();
                        titleMovieEt.setText("");
                        Toast.makeText(MainActivity.this, "Película Guardada con éxito", Toast.LENGTH_SHORT).show();
                    }

                }else{

                    Toast.makeText(MainActivity.this, "Debe escribir un nombre", Toast.LENGTH_SHORT).show();

                }
            }
        });

        lastMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = movieList.size();

                if (count > 0){
                    long movieId = movieList.get(count-1).getId();

                    Intent intent = new Intent(MainActivity.this,MovieActivity.class);
                    intent.putExtra(MOVIE_ID,movieId);
                    startActivity(intent);

                }else{
                    Toast.makeText(MainActivity.this, "No hay películas", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private List<Movie> getMovies (){

        //movieList = Movie.listAll(Movie.class);
        return Movie.find(Movie.class, "watched = ?","0");
        //return movieList;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("UPDATE","OK");
        movieList = getMovies();


    }
}
