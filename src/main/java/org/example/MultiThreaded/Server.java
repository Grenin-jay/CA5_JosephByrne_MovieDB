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

        ServerSocket serverSocket =null;
        Socket clientSocket =null;

        try{

            serverSocket = new ServerSocket(SERVER_PORT_NUMBER);
            System.out.println("Server has now started");
            int clientNum = 0;

            while(true){

                System.out.println("Server: Listening/waiting for connections on port:" + SERVER_PORT_NUMBER);
                clientSocket = serverSocket.accept();
                clientNum++;
                System.out.println("Server: Listening for connections on port:" + SERVER_PORT_NUMBER);

                System.out.println("Server: Client " + clientNum + " has connected.");
                System.out.println("Server: Port number of remote client: " + clientSocket.getPort());
                System.out.println("Server: Port number of the socket used to talk with client " + clientSocket.getLocalPort());

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
        this.clientNum = clientNum;
        this.clientSocket = clientSocket;

        try{

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

                if (request.startsWith("DisplayId")) {

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
                else if (request.startsWith("DisplayAll")) {

                }
                else if (request.startsWith("AddEntity")) {

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