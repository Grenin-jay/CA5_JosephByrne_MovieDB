package org.example.multithreaded;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    final int SERVER_PORT_NUMBER = 8888;

    public static void main(String[] args) {
        Server server = new Server();
        //server.start();
    }

    public void start(){

        ServerSocket serverSocket =null;
        Socket clientSocket =null;

        try{
            serverSocket = new ServerSocket(SERVER_PORT_NUMBER);
            System.out.println("Server has now started");
            int clientNum = 0;








        }catch (IOException ex) {
            System.out.println(ex);
        }
    }
}







class ClientHandler{

}