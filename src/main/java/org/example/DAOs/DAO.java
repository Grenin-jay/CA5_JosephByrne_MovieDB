package org.example.DAOs;

import org.example.DTOs.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Main author: Joseph Byrne
 * Other contributors: Ema Eiliakas
 *
 */

public class DAO extends MySQLDAO implements MoviesDAOInterface {

    private static DAO instance;

    private DAO() {

    }

    public static DAO getInstance() {

        if(instance == null)
            instance = new DAO();

        return instance;
    }

    /**
     * Main author: Joseph Byrne
     * Other contributors: Julius Odeyami
     *
     */
    @Override
    public List<Movie> getAllMovies() {

        List<Movie> movies = new ArrayList<>();
        Connection conn = getConnection();

        if(conn != null) {

            try {
                Statement stmt = conn.createStatement();
                ResultSet results = stmt.executeQuery("Select * from Movies");

                while (results.next()) {
                    Movie movie = new Movie();
                    movie.setMovie_id(results.getInt("movie_id"));
                    movie.setTitle(results.getString("title"));
                    movie.setRelease_year(results.getInt("release_year"));
                    movie.setGenre(results.getString("genre"));
                    movie.setDirector(results.getString("director"));
                    movie.setRuntime_minutes(results.getInt("runtime_minutes"));
                    movie.setRating(results.getDouble("rating"));
                    movies.add(movie);
                }

                conn.close();
            }

            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return movies;
    }

    /**
     * Main author: Joseph Byrne
     * Other contributors: Julius Odeyami
     *
     */
    @Override
    public Movie findMovieById(int movieId) {

        Movie movie = null;
        Connection conn = getConnection();

        if(conn != null) {

            try {

                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Movies WHERE movie_id = ?");

                stmt.setInt(1, movieId);
                ResultSet results = stmt.executeQuery();

                if (results.next()) {

                    movie = new Movie();
                    movie.setMovie_id(results.getInt("movie_id"));
                    movie.setTitle(results.getString("title"));
                    movie.setRelease_year(results.getInt("release_year"));
                    movie.setGenre(results.getString("genre"));
                    movie.setDirector(results.getString("director"));
                    movie.setRuntime_minutes(results.getInt("runtime_minutes"));
                    movie.setRating(results.getDouble("rating"));
                }

                conn.close();
            }

            catch (SQLException e) {

                throw new RuntimeException(e);
            }
        }

        return movie;
    }

    /**
     * Main author: Ema Eiliakas
     * Other contributors: Brandon Thompson
     *
     */
    @Override
    public void insertMovie(Movie movie) {
        Connection conn = getConnection();

        if(conn != null) {
            try {
                String query = "Insert Into Movies VALUES (null, ?, ?, ?, ?, ?,?)";

                PreparedStatement preparedStatement = conn.prepareStatement(query);

                preparedStatement.setString(1, movie.getTitle());
                preparedStatement.setInt(2, movie.getRelease_year());
                preparedStatement.setString(3, movie.getGenre());
                preparedStatement.setString(4,movie.getDirector());
                preparedStatement.setInt(5, movie.getRuntime_minutes());
                preparedStatement.setDouble(6, movie.getRating());
                preparedStatement.executeUpdate(); //Will insert a new row

                conn.close();
            }

            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Main author: Ema Eiliakas
     * Other contributors: Brandon Thompson
     *
     */
    @Override
    public void deleteMovie(int movieId) {

        Connection conn = getConnection();

        try {

            String query = "Delete From Movies Where movie_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, movieId);
            preparedStatement.executeUpdate(); // will delete the specified movie_id row

            conn.close();
        }


        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Main author: Ema Eiliakas
     *
     * Rating is the only updatable variable since we thought that you wouldnt need to update the other variables as those sort of factors wouldnt be changed
     */
    @Override
    public int updateRating(int movieId, double newRating) {

        Connection conn = getConnection();
        String query = "Update Movies Set rating = ? Where movie_id = ?";

        int linesChanged;

        try {

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setDouble(1, newRating);
            preparedStatement.setInt(2, movieId);
            linesChanged = preparedStatement.executeUpdate(); //will update the specified movie_id Row

            conn.close();
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return linesChanged;
    }

    /**
     * Main author: Joseph
     */
    @Override
    public List<Movie> filterMoviesByRating(double minRating) {

        List<Movie> filteredMovies = new ArrayList<>();
        Movie movie = null;
        Connection conn = getConnection();

        try {

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Movies WHERE rating >= ?");
            stmt.setDouble(1, minRating);
            ResultSet results = stmt.executeQuery();

            while (results.next()) {

                movie = new Movie();
                movie.setMovie_id(results.getInt("movie_id"));
                movie.setTitle(results.getString("title"));
                movie.setRelease_year(results.getInt("release_year"));
                movie.setGenre(results.getString("genre"));
                movie.setDirector(results.getString("director"));
                movie.setRuntime_minutes(results.getInt("runtime_minutes"));
                movie.setRating(results.getDouble("rating"));
                filteredMovies.add(movie);
            }

            conn.close();
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return filteredMovies;
    }

}