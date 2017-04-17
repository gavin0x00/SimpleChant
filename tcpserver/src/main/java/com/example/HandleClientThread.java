package com.example;

import java.io.*;
import java.net.Socket;

/**
 * Created by Newtrek on 2017/4/16.
 */
public class HandleClientThread extends Thread {
    private Socket socket;
    private String socketId;

    HandleClientThread(Socket socket){
        this.socket=socket;
        this.socketId=socket.getInetAddress().toString()+": ";
    }


    private void write(Socket socket,String content){
        try {
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            if (bufferedWriter!=null){
                bufferedWriter.write(content);
                bufferedWriter.flush();
            }
//            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            InputStream inputStream=socket.getInputStream();
            int readSize=-1;
            byte[] bytes=new byte[10];
            while ((readSize=inputStream.read(bytes))!=-1){
                String content=new String(bytes);
                System.out.println(socketId+content);
                for (Socket soc :
                        Server.socketList) {
                    if (!soc.getInetAddress().equals(socket.getInetAddress())){
                    write(soc,content);
                    }
                }
            }
            inputStream.close();
            socket.close();
            Server.socketList.remove(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
