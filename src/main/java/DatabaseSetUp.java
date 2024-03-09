import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
}
