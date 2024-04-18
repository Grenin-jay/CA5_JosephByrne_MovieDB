package org.example.MultiThreaded;

import org.example.DAOs.DAO;
import org.example.DTOs.Movie;
import org.example.JsonConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

/**
 * Main author: Joseph Byrne
 *
 */

public class Server {

    final int SERVER_PORT_NUMBER = 8888;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start(){

        //initialize server
        ServerSocket serverSocket =null;
        //initialize client socket
        Socket clientSocket =null;

        try{

            //creating a new server socket
            serverSocket = new ServerSocket(SERVER_PORT_NUMBER);
            System.out.println("Server has now started");
            int clientNum = 0;

            //checking for incoming connections from the client
            while(true){

                System.out.println("Server: Listening/waiting for connections on port:" + SERVER_PORT_NUMBER);
                clientSocket = serverSocket.accept();
                clientNum++;
                System.out.println("Server: Listening for connections on port:" + SERVER_PORT_NUMBER);

                System.out.println("Server: Client " + clientNum + " has connected.");
                System.out.println("Server: Port number of remote client: " + clientSocket.getPort());
                System.out.println("Server: Port number of the socket used to talk with client " + clientSocket.getLocalPort());

                //starting a thread for each client connection
                //allows multiple clients to connect at once without interfering with each other
                Thread t = new Thread((Runnable) new ClientHandler(clientSocket, clientNum));
                t.start();

                System.out.println("Server: ClientHandler started in thread " + t.getName() + " for client " + clientNum + ". ");
            }
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }
}

class ClientHandler implements Runnable{

    BufferedReader socketReader;
    PrintWriter socketWriter;
    Socket clientSocket;
    final int clientNum;

    public ClientHandler(Socket clientSocket, int clientNum){
        //initialise client number and socket
        this.clientNum = clientNum;
        this.clientSocket = clientSocket;

        try{
            //initialise socket writer and reader
            this.socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            this.socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }

        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void run() {
        String request;

        try {
            while ((request = socketReader.readLine()) != null) {

                System.out.println("Server: (ClientHandler): Read command from client " + clientNum + ": " + request);

                if (request.startsWith("displayid")) {

                    //array splits the command in half (DisplayId, <ID>) using the delimiter " "
                    String[] splitCommand = request.split(" ");

                    //checks if there are 2 variables in the array, meaning the split was done correctly
                    if (splitCommand.length == 2) {
                        //gets the string at index 1 (ID) and parses it into an int
                        int id = Integer.parseInt(splitCommand[1].trim());

                        try {
                            Movie movie = DAO.getInstance().findMovieById(id); //calling the find by ID method in the DAO file

                            //making sure movie that was retrieved by id exists to avoid errors
                            if (movie != null) {

                                String jsonMovie = JsonConverter.movietoJson(movie); //converts obj movie to Json
                                socketWriter.println(jsonMovie); //sends the Json to client
                                System.out.println("Server Message: JSON movie was sent to the client");
                            }
                            else{
                                socketWriter.println("Error: Movie not found, with this ID"); //sends error to client
                                System.out.println("Server Message: Movie not found, with this ID");
                            }
                        }
                        catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
                else if (request.startsWith("displayall")) {
                    try {
                        List<Movie> movies = DAO.getInstance().getAllMovies(); // Retrieve all movies from the DAO

                        // Check if any movies were found
                        if (!movies.isEmpty()) {
                            // Convert the list of movies to JSON
                            String jsonMovies = JsonConverter.moviesListToJson(movies);
                            socketWriter.println(jsonMovies); // Send the JSON to the client
                            System.out.println("Server Message: JSON movies were sent to the client");
                        } else {
                            socketWriter.println("Error: No movies found"); // Send error to client
                            System.out.println("Server Message: No movies found");
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }


                else if (request.toLowerCase().startsWith("AddEntity")) {

//                    // Get the JSON data from the client side
//                    String jsonData = socketReader.readLine();
//
//                    // Convert the data to a Movie object using the new jsonToMovie method added by Emma to the JsonConverter
//                    Movie movie = JsonConverter.jsonToMovie(jsonData, Movie.class);
//
//                    // Add the movie to the database
//                    try {
//                        DAO.getInstance().insertMovie(movie);
//
//                        // Send success confirmation back to the client if everything worked out fine
//                        String successConfirmed = JsonConverter.movietoJson(movie);
//                        socketWriter.println(successConfirmed);
//                        socketWriter.println("Movie added to database.");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//
//                        // Send error response to the client
//                        socketWriter.println("Error: Couldn't add your movie 😦 ");
//                    }
//
//                    // Flush the output stream
//                    socketWriter.flush();

                }
                else if (request.startsWith("Quit")) {
                    socketWriter.println("Sorry to see you leaving. Goodbye.");
                    System.out.println("Server message: Client has notified us that it is quitting.");
                }
                else {
                    socketWriter.println("error I'm sorry I don't understand your request");
                    System.out.println("Server message: Invalid request from client.");
                }
            }
        }

        catch (IOException ex) {
            ex.printStackTrace();
        }

        finally {
            this.socketWriter.close();

            try {
                this.socketReader.close();
                this.clientSocket.close();
            }

            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        System.out.println("Server: (ClientHandler): Handler for Client " + clientNum + " is terminating .....");
    }
}