package org.example.MultiThreaded;

import org.example.DTOs.Movie;
import org.example.JsonConverter;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

/**
 * Main author: Joseph Byrne
 * Other contributors: Ema Eiliakas
 *
 */

public class Client {
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start(){

        try(
                Socket socket = new Socket("Localhost", 8888);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {

            System.out.println("Client Message: The Client is running and has been connected to server");
            Scanner consoleInput = new Scanner(System.in);
            System.out.println("Valid commands include: DisplayID <ID>, DisplayAll, AddEntity , Quit");
            System.out.println("Enter your command:");
            String userCommand = consoleInput.nextLine();

            while (true) {

                out.println(userCommand); //this is required to have(writes the request to socket along with a newline terminator.

                //types of commands go here, Process the answers returned by the server
                if(userCommand.startsWith("DisplayId")) {

                    //sends command to server
                    String jsonMovie = in.readLine(); //receives response from server

                    Movie movie = JsonConverter.jsonToMovie(jsonMovie, Movie.class); //converting Json movie back to an object

                    System.out.println(movie.toString()); //prints out the movie

                    //System.out.println(jsonMovie.toString());
                }

                else if(userCommand.startsWith("DisplayAll")) {
                    String jsonMovies = in.readLine();
                    List<Movie> movies = JsonConverter.jsonToMovies(jsonMovies);

                    for (Movie movie : movies) {
                        System.out.println(movie.toString());
                    }
                }

                else if(userCommand.startsWith("AddEntity")) {
                    //Command3 code goes here, fill out after discussing with group what we want
                }
                else if(userCommand.startsWith("Quit")){
                    String response = in.readLine(); //Waits for response
                    System.out.println("Client Message: Response from server: " + response);

                    break;
                }
                else{
                    System.out.println("Command not known, please try again");
                }

                //end of while statement clears and prompts the user for a new command
                consoleInput = new Scanner(System.in);
                System.out.println("Valid commands include: DisplayID<ID>, DisplayAll, AddEntity , Quit");
                System.out.println("Enter your command:");
                userCommand = consoleInput.nextLine();
            }
        }

        catch (IOException e){
            System.out.println("Client Message: IO Exception: " + e);
        }

        System.out.println("Exiting Client, check if server may still be running.");

    }
}