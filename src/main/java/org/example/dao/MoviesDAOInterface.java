package org.example.dao;

import org.example.dto.Movie;

import java.util.List;

public interface MoviesDAOInterface {
    List<Movie> getAllMovies();

    Movie findMovieById(int movieId);

    void insertMovie(Movie movie);

    void deleteMovie(int movieId);

    int updateRating(int movieId, double newRating);

    List<Movie> filterMoviesByRating(double minRating);
}
