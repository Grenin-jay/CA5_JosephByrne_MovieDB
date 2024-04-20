package org.example.DAOs;

import org.example.DTOs.Movie;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoviesDAOInterfaceTest {

    private DAO dao;

    @org.junit.jupiter.api.BeforeEach
    public void setUp()
    {
        dao = DAO.getInstance();
    }

    // Get all movies - Test 1
    @org.junit.jupiter.api.Test
    void getAllMoviesTest() throws SQLException {
        List<Movie> movies = dao.getAllMovies();
        assertEquals(14, movies.size());
    }
    // Get all movies - Test 2
    @org.junit.jupiter.api.Test
    void checkIfMoviesNull() throws SQLException {
        List<Movie> movies = dao.getAllMovies();
        assertNotNull(movies);
    }
    // Find movie by ID - Test 1
    @org.junit.jupiter.api.Test
    void findMovieByIdTest() throws SQLException {
        Movie movie = dao.findMovieById(2);
        assertNotNull(movie);
        assertEquals(2, movie.getMovie_id());
    }
    // Find movie by ID - Test 2
    @org.junit.jupiter.api.Test
    void invalidMovieIDTest() throws SQLException {
        Movie movie= dao.findMovieById(-1);
        assertNull(movie);
    }

    //Insert new movie - Test 1
    @org.junit.jupiter.api.Test
    void InsertNewMovieTest() throws SQLException {
        //inserting a new movie so we can test
        Movie movie = new Movie();

        movie.setMovie_id(88);
        movie.setTitle("Test movie");
        movie.setRelease_year(2003);
        movie.setGenre("Test Genre, test poo");
        movie.setDirector("Test Director");
        movie.setRuntime_minutes(130);
        movie.setRating(8);

        dao.insertMovie(movie);

        //retrieving the movie we inserted from the database
        Movie insertedMovie = dao.findMovieById(movie.getMovie_id());

        //checking if movie was inserted
        assertNotNull(insertedMovie);

        //checks if the inserted movies variables match
        assertEquals(88, insertedMovie.getMovie_id());
        assertEquals("Test movie", insertedMovie.getTitle());
        assertEquals(2003, insertedMovie.getRelease_year());
        assertEquals("Test Genre, test poo", insertedMovie.getGenre());
        assertEquals("Test Director", insertedMovie.getDirector());
        assertEquals(130, insertedMovie.getRuntime_minutes());
        assertEquals(8, insertedMovie.getRating());
    }

    //Delete a movie - Test 1
    @org.junit.jupiter.api.Test
    void deleteMovieTest() throws SQLException {
        // Insert a movie to delete
        Movie movie = new Movie();
        movie.setTitle("Test movie");
        movie.setRelease_year(2003);
        movie.setGenre("Test Genre, test poo");
        movie.setDirector("Test Director");
        movie.setRuntime_minutes(130);
        movie.setRating(8);

        dao.insertMovie(movie);

        // gets the ID of the movie we inserted
        int movieId = movie.getMovie_id();

        // Deletes the movie
        dao.deleteMovie(movieId);

        // tries to find the deleted movie
        Movie deletedMovie = dao.findMovieById(movie.getMovie_id());

        // checks if it was deleted by checking if the movie is null
        assertNull(deletedMovie);
    }

    //Delete a movie - Test 2
    @org.junit.jupiter.api.Test
    void deleteNonExistentMovieTest() throws SQLException {
        int invalidMovieID = 100; //initializing a fake movie id

        // Tries to delete the fake movie
        dao.deleteMovie(invalidMovieID);

        // tries to find the deleted movie
        Movie deletedMovie = dao.findMovieById(invalidMovieID);

        // makes sure the deleted movie is null
        assertNull(deletedMovie);
    }

    //Update the movies rating - Test 1
    @org.junit.jupiter.api.Test
    void updateRatingTest() throws SQLException {
        int movieId = 2;//chooses movie to change by its id
        double newRating = 9.0;//new rating for the movie
        int linesChanged = dao.updateRating(movieId, newRating);//runs update method with new rating
        assertEquals(1, linesChanged);//checks if 1 line has been affected
    }

    //Update the movies rating - Test 2
    @org.junit.jupiter.api.Test
    void updateNegativeRatingTest() throws SQLException {
        int movieId = 2;//chooses movie to change by its id
        double newRating = -1.0;//setting a negative rating
        int linesChanged = dao.updateRating(movieId, newRating);//runs update method with new rating
        assertEquals(0, linesChanged);//line is expected to not be affected as -1 is an invalid input
    }

    //Filter by specified rating - Test 1
    @org.junit.jupiter.api.Test
    void testFilterMoviesByRating() throws SQLException {

        // Inserting 3 test movies to filter by rating
        Movie movie1 = new Movie();
        movie1.setTitle("Test1");
        movie1.setRelease_year(2003);
        movie1.setGenre("Horror");
        movie1.setDirector("Director1");
        movie1.setRuntime_minutes(120);
        movie1.setRating(1.0);
        dao.insertMovie(movie1);

        Movie movie2 = new Movie();
        movie2.setTitle("Test2");
        movie2.setRelease_year(2005);
        movie2.setGenre("Horror");
        movie2.setDirector("Director2");
        movie2.setRuntime_minutes(110);
        movie2.setRating(1.0);
        dao.insertMovie(movie2);

        List<Movie> filteredMovies = dao.filterMoviesByRating(1.0);

        assertEquals(2, filteredMovies.size());
    }
}