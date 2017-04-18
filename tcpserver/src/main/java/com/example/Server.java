package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final int PORT=9999;
//    save all clientsocket
    public static List<Socket> socketList=new ArrayList<>();

    public static void main(String[] args) {

      Server server=new Server();
      server.startListen();

    }

    /**
     *
     */
    public void startListen(){
        ServerSocket serverSocket=null;
        try {
             serverSocket=new ServerSocket(PORT);
            while (true){
                Socket client=serverSocket.accept();
                System.out.println(client.getInetAddress().toString()+"linked!");

                socketList.add(client);
                new HandleClientThread(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
