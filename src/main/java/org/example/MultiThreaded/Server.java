package org.example.MultiThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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

        }catch (IOException ex) {
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
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void run()
    {
        String request;
        try
        {
            while ((request = socketReader.readLine()) != null)
            {
                System.out.println("Server: (ClientHandler): Read command from client " + clientNum + ": " + request);

                if (request.startsWith("Command1"))
                {

                }
                else if (request.startsWith("Command2"))
                {

                }
                else if (request.startsWith("quit"))
                {
                    socketWriter.println("Sorry to see you leaving. Goodbye.");
                    System.out.println("Server message: Client has notified us that it is quitting.");
                }
                else
                {
                    socketWriter.println("error I'm sorry I don't understand your request");
                    System.out.println("Server message: Invalid request from client.");
                }
            }
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            this.socketWriter.close();
            try
            {
                this.socketReader.close();
                this.clientSocket.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        System.out.println("Server: (ClientHandler): Handler for Client " + clientNum + " is terminating .....");
    }
}