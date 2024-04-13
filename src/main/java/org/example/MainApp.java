package org.example;

import org.example.dao.DAO;
import org.example.dto.Movie;

import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        DAO dao = DAO.getInstance();

        System.out.println("Hello there and welcome to our movie database");

        int choice;
        do {
            System.out.println("------------------------");
            System.out.println("\n1. View all movies.");
            System.out.println("2. Insert New Movie.");
            System.out.println("3. Find Movie by ID.");
            System.out.println("4. Delete Movie by movie ID.");
            System.out.println("5. Update a movies rating.");
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
                    System.out.println("Enter the new rating for this movie: ");
                    double newRating = keyboard.nextDouble();
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
                    System.out.println("Which movies would you like to convert to Json format?");
                    System.out.println("1.All Movies");
                    System.out.println("2.By ID");

                    String json;
                    int op = keyboard.nextInt();

                    if (op == 1)
                    {
                        List<Movie> allMovies = dao.getAllMovies();
                        json = JsonConverter.moviesListToJson(allMovies);
                        System.out.println("JSON Format: " + json);
                    }


                    else if (op == 2)
                    {
                        System.out.println("Please Enter the Movie ID");
                        int movieIdJson = keyboard.nextInt();
                        Movie foundMovieJson = dao.findMovieById(movieIdJson);
                        if(foundMovieJson != null)
                        {

                            String movieJson = JsonConverter.gsonParser.toJson(foundMovieJson);

                            json = JsonConverter.singleMovieToJson(movieJson);
                            System.out.println("JSON representation of the found movie:");
                            System.out.println(json);
                        }

                        else {
                            System.out.println("No Movie found with this ID!");
                        }
                    }

                    break;

                case 8:
                    System.out.println("Exiting Code Now.");

                    break;
                default:
                    System.out.println("Invalid Choice.");
            }
        } while (choice != 8); // Exits Loop
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