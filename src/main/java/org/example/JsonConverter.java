package org.example;
/**
 * Main author: Julius
 *
 */

import org.example.DTOs.Movie;
import com.google.gson.Gson;

import java.util.List;

public class JsonConverter
{
    public static final Gson gsonParser = new Gson();

    public static String moviesListToJson (List<Movie> list) {
        return gsonParser.toJson(list);
    }

    public static String singleMovieToJson(String json)
    {
        Movie movie = gsonParser.fromJson(json, Movie.class);

        return gsonParser.toJson(movie);
    }

    public static String movietoJson(Movie movie)
    {
        return new Gson().toJson(movie);
    }

    public static Movie jsonToMovie(String json, Class<Movie> movieClass) {
        return gsonParser.fromJson(json, Movie.class);
    }

}