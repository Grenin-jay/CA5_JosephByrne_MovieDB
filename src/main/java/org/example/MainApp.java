package org.example;

import org.example.DAOs.DAO;
import org.example.DAOs.User;
import org.example.DTOs.Movie;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Main author: Joseph Byrne
 * Other contributors: Ema Eiliakas
 */

public class MainApp {

    public static void main(String[] args) throws SQLException {

        Scanner keyboard = new Scanner(System.in);
        DAO dao = DAO.getInstance();

        System.out.println("Hello there and welcome to our movie database");

        int choice;

        System.out.println("Username: ");
        String userName = keyboard.nextLine();

        System.out.println("Password: ");
        String userPassword = keyboard.nextLine();

        User user = dao.logIn(userName,userPassword);

        if(user == null){
            System.out.println("Login Failed. Invalid User.");
        }
        else{
            do {
                System.out.println("------------------------");
                System.out.println("\n1. View all movies.");
                if(user.isAdmin()){
                    System.out.println("2. Insert New Movie.");
                }
                System.out.println("3. Find Movie by ID.");
                if(user.isAdmin()){
                    System.out.println("4. Delete Movie by movie ID.");
                    System.out.println("5. Update a movies rating.");
                }
                System.out.println("6. Filter by rating.");
                System.out.println("7. Json Convert");
                System.out.println("8. Exit Code.");
                System.out.println("------------------------");

                choice = keyboard.nextInt();
                keyboard.nextLine();

                switch (choice) {

                    case 1:
                        System.out.println("Showing all movies.");
                        ShowMovies(dao);

                        break;

                    case 2:
                        System.out.println("You are inserting a new movie.");
                        Movie newMovie = insertNewMovie(keyboard);
                        dao.insertMovie(newMovie);
                        System.out.println("New Movie Inserted");

                        break;

                    case 3:
                        System.out.println("Finding a movie by ID");
                        System.out.println("Please Enter the Movie ID");
                        int movieId = keyboard.nextInt();
                        Movie foundMovie = dao.findMovieById(movieId);

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
                        System.out.println("Enter the movie ID to delete it: ");
                        int movieIdToDelete = keyboard.nextInt();
                        keyboard.nextLine();
                        dao.deleteMovie(movieIdToDelete);
                        System.out.println("Movie Deleted successfully");

                        break;

                    case 5:
                        System.out.println("You are updating a movie rating");
                        System.out.println("Enter the movie ID to update its rating: ");
                        int movieUpdating = keyboard.nextInt();

                        double newRating;
                        do {
                            System.out.println("Enter the new rating for this movie (0-10): ");
                            newRating = keyboard.nextDouble();
                            if(newRating < 0 || newRating > 10){
                                System.out.println("Error, Enter a valid number");
                            }
                        } while (newRating < 0 || newRating > 10);

                        dao.updateRating(movieUpdating, newRating);
                        System.out.println("Movie rating updated successfully");
                        break;

                    case 6:
                        System.out.println("Filtering Movies By Rating");
                        System.out.println("Enter the minumum rating you want");

                        double minRating = keyboard.nextDouble();
                        keyboard.nextLine();

                        List<Movie> filteredMoviesbyRating = dao.filterMoviesByRating(minRating);

                        if (!filteredMoviesbyRating.isEmpty()){
                            System.out.println("Filtered Movies");

                            for(Movie movie : filteredMoviesbyRating) {
                                System.out.printf("Title: %s, Rating: %.2f\n",movie.getTitle(), movie.getRating());
                            }
                        }
                        else{
                            System.out.println("No Movies found above min rating");
                        }
                        break;

                    case 7:
                        System.out.println("Convert Movies to Json Format");
                        System.out.println("Converting All Available Movies to JSON Format");

                        String json;
                        List<Movie> allMovies = dao.getAllMovies();
                        json = JsonConverter.moviesListToJson(allMovies);
                        System.out.println("JSON Format: " + json);
                        break;

                    case 8:
                        System.out.println("Convert Movie to Json by ID");
                        System.out.println("Please Enter the Movie ID");
                        int movieIdJson = keyboard.nextInt();
                        Movie foundMovieJson = dao.findMovieById(movieIdJson);

                        if(foundMovieJson != null) {

                            String movieJson = JsonConverter.singleMovieToJson(foundMovieJson);


                            System.out.println("JSON representation of the found movie:");
                            System.out.println(movieJson);
                        }
                        else {
                            System.out.println("No Movie found with this ID!");
                        }
                        break;

                    case 9:
                        System.out.println("Exiting Code Now.");
                        break;
                    default:
                        System.out.println("Invalid Choice.");
                }
            } while (choice != 8); // Exits Loop
        }
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

    private static void ShowMovies(DAO dao){
        System.out.println("All Movies:");
        List<Movie> allMovies = dao.getAllMovies();
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