package com.decagon.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientMain {


    public static void main(String[] args) {

        try{
            InetAddress ip = InetAddress.getByName("localhost");
            Socket socket = new Socket(ip,12345);

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            MessageStream messageStream = new MessageStream(dataOutputStream,dataInputStream,socket);

            messageStream.receiveMessage();
            messageStream.sendMessage();

        }
        catch (Exception e){
            System.out.println("Client Error: " +e.getMessage());
        }
    }
}