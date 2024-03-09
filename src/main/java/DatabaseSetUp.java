import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Main author: Joseph Byrne
 * Other contributors: Ema Eiliakas
 *
 */

public class DatabaseSetUp {

    private static final String URL = "jdbc:mysql://localhost/";
    private String dbname = "ca5_joseph_byrne";

    private String username = "root";
    private String password = "";

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

    /**
     * Main author: Joseph Byrne
     *
     */
    public Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection
                    (URL + dbname,username, password);
            return conn;
        } catch (SQLException e) {
            System.out.println("Unable to connect to database: " + e.getMessage());
            return null;
        }
    }

    /**
     * Main author: Joseph Byrne
     * Other contributors: Julius Odeyami
     *
     */
    public List<Movie> getAllMovies() throws SQLException
    {
        List<Movie> movies = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("Select * from Movies");

        while (results.next())
        {
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
        return movies;
    }

    /**
     * Main author: Joseph Byrne
     * Other contributors: Julius Odeyami
     *
     */
    public Movie findMovieById(int movieId) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Movies WHERE movie_id = ?");
        stmt.setInt(1, movieId);
        ResultSet results = stmt.executeQuery();

        Movie movie = null;
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
        return movie;
    }

    /**
     * Main author: Ema Eiliakas
     * Other contributors: Brandon Thompson
     *
     */
    public void insertMovie(Movie movie) throws SQLException {
        Connection conn = getConnection();
        String query = "Insert Into Movies VALUES (null, ?, ?, ?, ?, ?,?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setInt(2, movie.getRelease_year());
            preparedStatement.setString(3, movie.getGenre());
            preparedStatement.setString(4,movie.getDirector());
            preparedStatement.setInt(5, movie.getRuntime_minutes());
            preparedStatement.setDouble(6, movie.getRating());
            preparedStatement.executeUpdate(); //Will insert a new row
        } finally {
            conn.close();
        }
    }

    /**
     * Main author: Ema Eiliakas
     * Other contributors: Brandon Thompson
     *
     */
    public void deleteMovie(int movieId) throws SQLException {
        Connection conn = getConnection();
        String query = "Delete From Movies Where movie_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, movieId);
            preparedStatement.executeUpdate(); // will delete the specified movie_id row
        } finally {
            conn.close();
        }
    }

}
