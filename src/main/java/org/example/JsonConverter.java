package org.example;


import com.google.gson.reflect.TypeToken;
import org.example.DTOs.Movie;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class JsonConverter
{
    private static final Gson gsonParser = new Gson();

    public static String moviesListToJson (List<Movie> list) {
        return gsonParser.toJson(list);
    }

    public static String singleMovieToJson(Movie movie)
    {
        // Movie movie = gsonParser.fromJson(json, Movie.class);

        return gsonParser.toJson(movie);
    }


    public static String movietoJson(Movie movie){
        return new Gson().toJson(movie);}

    public static Movie jsonToMovie(String json, Class<Movie> movieClass) {
        return gsonParser.fromJson(json, Movie.class);
    }

    /**

     Main author: Julius**/
    public static List<Movie> jsonToMovies(String json) {
        Type listType = new TypeToken<List<Movie>>(){}.getType();
        return gsonParser.fromJson(json, listType);}

//    public static String commandToJson(Command c)
//    {
//        return gsonParser.toJson(c);
//    }
//
//    public static Command commandFromJson(String c)
//    {
//        return gsonParser.fromJson(c, Command.class);
//    }

}