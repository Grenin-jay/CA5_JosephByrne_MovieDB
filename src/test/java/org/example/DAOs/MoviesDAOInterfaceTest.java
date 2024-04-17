package org.example.DAOs;

import org.example.DTOs.Movie;
import org.example.MainApp;

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

    /**
     * Method to test get all movies feature
     * @throws SQLException
     */
    @org.junit.jupiter.api.Test
    void getAllMovies() throws SQLException {
        List<Movie> movies = dao.getAllMovies();
        assertEquals(14, movies.size());
    }

    @org.junit.jupiter.api.Test
    void findMovieById() {
    }

    @org.junit.jupiter.api.Test
    void insertMovie() {
    }

    @org.junit.jupiter.api.Test
    void deleteMovie() {
    }

    @org.junit.jupiter.api.Test
    void updateRating() {
    }

    @org.junit.jupiter.api.Test
    void filterMoviesByRating() {
    }
}