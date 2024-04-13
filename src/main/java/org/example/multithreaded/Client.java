package org.example.multithreaded;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
    Client client = new Client();
    //client.start();
    }

    public void start(){

        try(
                Socket socket = new Socket("Localhost", 8888);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {

            System.out.println("Client Message: The Client is running and has been connected to server");
            Scanner consoleInput = new Scanner(System.in);
            System.out.println("Valid commands include: Command1, Command2, quit");
            System.out.println("Enter your command:");
            String userCommand = consoleInput.nextLine();

            while (true) {
                out.println(userCommand); //this is required to have(writes the request to socket along with a newline terminator.

                //types of commandds go here, Process the answers returned by the server
                //lots of if, elif and else statements.

                //end of while statement clears and prompts the user for a new command
                consoleInput = new Scanner(System.in);
                System.out.println("Valid commands include: Command1, Command2, Command3");
                System.out.println("Enter your command:");
                userCommand = consoleInput.nextLine();
            }
        } catch (IOException e){
            System.out.println("Client Message: IO Exception: " + e);
        }
        System.out.println("Exiting Client, check if server may still be running.");

    }
}

