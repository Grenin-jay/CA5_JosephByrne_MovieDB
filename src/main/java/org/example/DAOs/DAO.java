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
     *
     * Retrieves all movies from the database
     */
    @Override
    public List<Movie> getAllMovies() {

        List<Movie> movies = new ArrayList<>(); // List that stores all movies
        Connection conn = getConnection(); // Gets the database connection

        //Checking if the connection worked
        if(conn != null) {

            try {

                Statement stmt = conn.createStatement(); // creating a statement
                ResultSet results = stmt.executeQuery("Select * from Movies"); // Executes query using statement to select all movies in the DB

                //goes through each result
                while (results.next()) {
                    Movie movie = new Movie(); //creating movie obj so we can populate it

                    //Populating the movie obj with the data we retrieved
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

            // Handles any SQL exceptions that may occur
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return movies;
    }

    /**
     * Main author: Joseph Byrne
     *
     * Finds a movie by its ID
     */
    @Override
    public Movie findMovieById(int movieId) {

        Movie movie = null; //initializing movie Obj, this will help avoid errors when we populate movie obj
        Connection conn = getConnection(); // Gets the database connection

        //Checking if the connection worked
        if(conn != null) {

            try {

                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Movies WHERE movie_id = ?"); //Using a prepared statement so we can execute the query with a parameter

                stmt.setInt(1, movieId); //setting parameter as movieID
                ResultSet results = stmt.executeQuery(); //executes the query

                //goes through all the results
                if (results.next()) {

                    movie = new Movie(); //creating movie obj so we can populate it

                    //Populating the movie obj with the data we retrieved
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
     *
     */
    @Override
    public void insertMovie(Movie movie) {

        Connection conn = getConnection(); //connecting to the DB

        //checking if connection was succesfull
        if(conn != null) {

            try {

                //Using a prepared statement to execute the query wih parameters
                PreparedStatement stmt = conn.prepareStatement("Insert Into Movies VALUES (null, ?, ?, ?, ?, ?,?)");

                //setting each parameter to a value
                stmt.setString(1, movie.getTitle());
                stmt.setInt(2, movie.getRelease_year());
                stmt.setString(3, movie.getGenre());
                stmt.setString(4,movie.getDirector());
                stmt.setInt(5, movie.getRuntime_minutes());
                stmt.setDouble(6, movie.getRating());

                stmt.executeUpdate(); //Will insert a new row by executing the statement

                conn.close();
            }

            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Main author: Ema Eiliakas
     *
     */
    @Override
    public void deleteMovie(int movieId) {

        Connection conn = getConnection(); //gets DB connection

        //checking if connection was succesfull
        if(conn != null) {

            try {

                //Using a prepared statement to execute the query wih a parameter
                PreparedStatement stmt = conn.prepareStatement("Delete From Movies Where movie_id = ?");
                stmt.setInt(1, movieId); //setting parameter to movieID

                stmt.executeUpdate(); // will delete the specified movie_id row

                conn.close();

            }

            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Main author: Ema Eiliakas
     *
     * Rating is the only updatable variable since we thought that you
     * wouldnt need to update the other variables as those sort of factors wouldnt be changed
     */
    @Override
    public int updateRating(int movieId, double newRating) {

        int linesChanged= 0;
        Connection conn = getConnection(); //get connection to DB

        if(conn != null) {

            try {

                //Using a prepared statement to execute the query wih a parameter
                PreparedStatement stmt = conn.prepareStatement("Update Movies Set rating = ? Where movie_id = ?");

                stmt.setDouble(1, newRating); //setting parameter to newRating
                stmt.setInt(2, movieId); //setting parameter to movieID

                linesChanged = stmt.executeUpdate(); //will update the specified movie_id Row

                conn.close();

            }

            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return linesChanged;
    }

    /**
     * Main author: Joey Byrne
     *
     * filters movies by rating
     */
    @Override
    public List<Movie> filterMoviesByRating(double filter) {

        List<Movie> filteredMovies = new ArrayList<>(); //list to store filtered movies
        Movie movie = null; //initialize movie obj
        Connection conn = getConnection(); // get connection from DB

        //checks if it connected succesfully
        if(conn != null) {

            try {

                //Using a prepared statement to execute the query wih a parameter
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Movies WHERE rating = ?");
                stmt.setDouble(1, filter); //setting parameter as filter
                ResultSet results = stmt.executeQuery(); //execute query

                //goes through all results
                while (results.next()) {

                    movie = new Movie(); // setting up movie obj so we can populate it

                    //populates movie obj with the data retrieved from results
                    movie.setMovie_id(results.getInt("movie_id"));
                    movie.setTitle(results.getString("title"));
                    movie.setRelease_year(results.getInt("release_year"));
                    movie.setGenre(results.getString("genre"));
                    movie.setDirector(results.getString("director"));
                    movie.setRuntime_minutes(results.getInt("runtime_minutes"));
                    movie.setRating(results.getDouble("rating"));

                    filteredMovies.add(movie); //adds movie to list
                }

                conn.close();
            }

            catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        return filteredMovies;
    }

    /**
     * Main author: Ema Eiliakas
     *
     * Sets up the login
     */
    public User logIn(String username, String password) throws SQLException
    {
        User u = null; //initalizing user
        Connection conn = getConnection();

        //check if connection was successful
        if(conn != null)
        {
            String query = "select * from Users where username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet results = stmt.executeQuery();

            //goes through all the results
            while(results.next())
            {

                // If the password retrieved from the database matches the provided password
                if(results.getString("password").equals(password))
                {
                    u = new User(); //creates a user obj

                    //assignes data retrieved to variables below
                    u.setId(results.getInt("ID"));
                    u.setUsername(results.getString("username"));
                    u.setPassword(results.getString("password"));
                    u.setDisplayName(results.getString("displayName"));
                    u.setAdmin(results.getInt("isAdmin")==1);
                }
            }
            conn.close();

        }
        return u;
    }

}