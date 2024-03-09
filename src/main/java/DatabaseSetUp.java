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


}
