package com.decagon.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    Socket socket;

    public ClientHandler(DataInputStream dataInputStream, DataOutputStream dataOutputStream, Socket socket){
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this. socket = socket;

    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }


    @Override
    public void run(){
        try {

            while (true) {
                synchronized (this.dataInputStream) {

                    // read data from an inputStream
                    String message = dataInputStream.readUTF();
                    System.out.println("message: " + message);

                    if(message.equals("exit")){
                        dataInputStream.close();
                        dataOutputStream.close();
                        socket.close();
                        break;
                    }

                    // write to an outputStream
                    // check if there are more than one socket connected to the server
                    if(ServerMain.inputStreamsList.size()> 1) {

                        if (dataInputStream.equals(ServerMain.inputStreamsList.get(0).getDataInputStream())) {
                            ServerMain.inputStreamsList.get(1).getDataOutputStream().writeUTF( message);
                        }

                        if (dataInputStream.equals(ServerMain.inputStreamsList.get(1).getDataInputStream())) {
                            ServerMain.inputStreamsList.get(0).getDataOutputStream().writeUTF(message);
                        }
                    }
                }
            }


        }catch (Exception e) {
        System.out.println("com.decagon.server.ClientHandler Error: " + e.getMessage());
        }
    }


//    private void removeClientFromList(String message){
//        String[] str = message.split(":");
//
//        System.out.println("removeClientList: " +str[0]);
//
//        try {
//
//            if (dataInputStream.equals(ServerMain.inputStreamsList.get(0).getDataInputStream())) {
//                ServerMain.inputStreamsList.get(1).getDataOutputStream().writeUTF(str[0] +" is offline!");
//                // remove from List
//                ServerMain.inputStreamsList.remove(0);
//            }
//
//            if (dataInputStream.equals(ServerMain.inputStreamsList.get(1).getDataInputStream())) {
//                ServerMain.inputStreamsList.get(0).getDataOutputStream().writeUTF(str[0] +" is offline!");
//                ServerMain.inputStreamsList.remove(1);
//            }
//
//        }catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//    }

}
