package org.example;

public class CommitIgnore {

    //Had a problem with the first repository so this is the code from my first 2 commits so that you can see i commited stuff before hand
    /*

import java.lang.runtime.SwitchBootstraps;
import java.sql.*;
import java.util.List;
import java.util.Scanner;


    public class org.example.MainApp {
        public static void main(String[] args) throws SQLException {

            Scanner keyboard = new Scanner(System.in);
            DatabaseSetUp databaseSetUp = DatabaseSetUp.getInstance();

            System.out.println("Hello there and welcome to our movie database");

            int choice;
            do {
                System.out.println("1. View all movies.");
                System.out.println("2. Exit.");

                choice = keyboard.nextInt();
                keyboard.nextLine();

                switch (choice) {
                    case 1:
                        ShowMovies(databaseSetUp);
                        break;
                    case 2:
                        System.out.println("Exiting Code now.");
                        break;
                    default:
                        System.out.println("Invalid Choice");
                }
            } while (choice != 2); //Exits Loop
        }

        private static void ShowMovies(DatabaseSetUp databaseSetUp) throws SQLException {
            System.out.println("All Movies:");
            List<org.example.dto.Movie> allMovies = databaseSetUp.getAllMovies();
            System.out.println(allMovies);
        }
    }

    import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSetUp {

    private static final String URL = "jdbc:mysql://localhost/";
    private String dbname = "moviedatabase";

    private static DatabaseSetUp instance;

    private DatabaseSetUp()
    {

    }

    public static DatabaseSetUp getInstance()
    {
        if(instance == null)
            instance = new DatabaseSetUp();

        return instance;
    }

    public Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection
                    (URL + dbname);
            return conn;
        } catch (SQLException e) {
            System.out.println("Unable to connect to database: " + e.getMessage());
            return null;
        }
    }

    public List<org.example.dto.Movie> getAllMovies() throws SQLException
    {
        List<org.example.dto.Movie> movies = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("Select * from Movies");

        while (results.next())
        {
            org.example.dto.Movie movie = new org.example.dto.Movie();
            movie.setMovie_id(results.getInt("movie_id"));
            movie.setTitle(results.getString("title"));
            movies.add(movie);
        }

        conn.close();
        return movies;
    }


}





    public class org.example.dto.Movie {

    private int movie_id;
    private String title;
    private int release_year;
    private String genre;
    private String director;
    private int runtime_minutes;
    private double rating;

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRelease_year() {
        return release_year;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getRuntime_minutes() {
        return runtime_minutes;
    }

    public void setRuntime_minutes(int runtime_minutes) {
        this.runtime_minutes = runtime_minutes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
     */
}
