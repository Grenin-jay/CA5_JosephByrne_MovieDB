import java.sql.*;
import java.util.List;
import java.util.Scanner;

/**
 * Main author: Joseph Byrne
 * Other contributors: Ema Eiliakas, Julius Odeyami, Brandon...
 *
 */


public class MainApp {
    public static void main(String[] args) throws SQLException {

        Scanner keyboard = new Scanner(System.in);
        DatabaseSetUp databaseSetUp = DatabaseSetUp.getInstance();

        System.out.println("Hello there and welcome to our movie database");

        int choice;
        do {
            System.out.println("\n1. View all movies.");
            System.out.println("2. Insert New Movie.");
            System.out.println("3. Find Movie by ID");

            System.out.println("4. Exit.");

            choice = keyboard.nextInt();
            keyboard.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Showing all movies.");
                    ShowMovies(databaseSetUp);
                    break;
                case 2:
                    System.out.println("You are inserting a new movie.");
                    Movie newMovie = insertNewMovie(keyboard);
                    DatabaseSetUp.getInstance().insertMovie(newMovie);
                    System.out.println("New Movie Inserted");
                    break;
                case 3:
                    System.out.println("Finding a movie by ID");
                    System.out.println("Please Enter the Movie ID");
                    int movieId = keyboard.nextInt();
                    Movie foundMovie = databaseSetUp.findMovieById(movieId);
                    if(foundMovie != null){
                        System.out.println("Movie Found!");

                        System.out.printf("%-5s %-20s %-12s %-22s %-24s %-12s %-10s\n",
                                "ID", "Title", "Release", "Genre", "Director", "Runtime", "Rating");

                        System.out.printf("%-5d %-20s %-12d %-22s %-24s %-12d %-10f\n",
                                foundMovie.getMovie_id(), foundMovie.getTitle(), foundMovie.getRelease_year(),
                                foundMovie.getGenre(), foundMovie.getDirector(),foundMovie.getRuntime_minutes(),foundMovie.getRating());
                    }
                    else
                    {
                        System.out.println("No Movie found with this ID!");
                    }
                    break;
                case 4:
                    System.out.println("Exiting Code now.");
                    break;
                default:
                    System.out.println("Invalid Choice.");
            }
        } while (choice != 4); //Exits Loop
    }

    private static Movie insertNewMovie(Scanner keyboard) {
        Movie newMovie = new Movie();
        System.out.println("Enter Movie title:");
        newMovie.setTitle(keyboard.nextLine());
        System.out.println("Enter release year:");
        newMovie.setRelease_year(keyboard.nextInt());
        keyboard.nextLine();
        System.out.println("Enter genre:");
        newMovie.setGenre(keyboard.nextLine());
        System.out.println("Enter director:");
        newMovie.setDirector(keyboard.nextLine());
        System.out.println("Enter runtime in minutes:");
        newMovie.setRuntime_minutes(keyboard.nextInt());
        System.out.println("Enter rating:");
        newMovie.setRating(keyboard.nextDouble());
        return newMovie;
    }

    private static void ShowMovies(DatabaseSetUp databaseSetUp) throws SQLException {
        System.out.println("All Movies:");
        List<Movie> allMovies = databaseSetUp.getAllMovies();
        //System.out.println(allMovies);
        System.out.printf("%-5s %-20s %-12s %-22s %-24s %-12s %-10s\n",
                "ID", "Title", "Release", "Genre", "Director", "Runtime", "Rating");


        for (Movie movie : allMovies)
        {
            System.out.printf("%-5d %-20s %-12d %-22s %-24s %-12d %-10f\n",
                    movie.getMovie_id(), movie.getTitle(), movie.getRelease_year(),
                    movie.getGenre(), movie.getDirector(),movie.getRuntime_minutes(),movie.getRating());
        }

    }
}
