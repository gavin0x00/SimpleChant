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
            System.out.println("Server Start!");

//            启动写线程
            new WriteThread().start();

            while (true){
                Socket client=serverSocket.accept();
                System.out.println(client.getInetAddress().toString()+" has linked!");
//                add to List
                socketList.add(client);
//                start to deal Client Socket
                new HandleClientThread(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
