package com.decagon.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MessageStream {
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    Socket socket;
    Scanner scanner = new Scanner(System.in);
    static String message = "";

    public MessageStream(DataOutputStream dataOutputStream, DataInputStream dataInputStream, Socket socket){
        this.dataOutputStream = dataOutputStream;
        this.socket = socket;
        this.dataInputStream = dataInputStream;
    }


    public void sendMessage(){
        new Thread(()->{
            System.out.print("Enter your name: ");
            String  name = scanner.nextLine();

            try {
                while (!message.equals("exit")) {

                   message = scanner.nextLine();
                   dataOutputStream.writeUTF(name + ": " + message);
                   dataOutputStream.flush();
                }
                dataInputStream.close();
                dataOutputStream.close();
                socket.close();

            } catch (Exception e) {
                System.out.println("Write Message Error: " + e.getMessage());
            }

        }).start();

    }


    public void receiveMessage(){
        new Thread(()-> {
            try {
                while (!message.equals("exit")){
                    String receivedMessage = "";
                    receivedMessage = dataInputStream.readUTF();
                    System.out.println(receivedMessage);
                }

                dataInputStream.close();
                dataOutputStream.close();
                socket.close();


            } catch (Exception e) {
                System.out.println("Read Message Error: " + e.getMessage());
            }

        }).start();
    }


}
