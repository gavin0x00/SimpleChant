package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Newtrek on 2017/4/16.
 */
public class ReadThread extends Thread {
    private Socket socket;
    ReadThread(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream=socket.getInputStream();
            int readSize=-1;
            byte[] bytes=new byte[10];
            while ((readSize=inputStream.read(bytes))!=-1){
                System.out.println("receive"+new String(bytes));
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
