package com.decagon.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class ServerMain {
    static Vector<ClientHandler> inputStreamsList = new Vector<>(2);

    public static void main(String[] args) {

       try(ServerSocket serverSocket = new ServerSocket(12345)){

           Socket socket;
           while(true){

               socket = serverSocket.accept();

               System.out.println("socket: " + socket);

               DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
               DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

               // add client's DataInputStream and DataOutputStream details
               inputStreamsList.add(new ClientHandler(dataInputStream,dataOutputStream,socket));

               System.out.println("dataInputStream: " + dataInputStream);
               System.out.println("dataOutputStream: " + dataOutputStream);

               ClientHandler clientHandler = new ClientHandler(dataInputStream,dataOutputStream,socket);
               clientHandler.start();
           }

       }
       catch (Exception e){
           System.out.println("Server Error: "+ e.getMessage());
       }
    }
}