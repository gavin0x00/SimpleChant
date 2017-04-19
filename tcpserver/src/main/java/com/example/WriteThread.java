package com.example;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread {

    private void write(Socket socket,String string){
        final String str=string;
        try {
            OutputStream outputStream=socket.getOutputStream();
            outputStream.write(str.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void run() {
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            String message=scanner.nextLine();
            if (message!=null){
                message="Server:"+message;
                for (Socket socket :
                        Server.socketList) {
                    if (socket != null && socket.isConnected()){
                        write(socket,message);
                    }
                }
            }
        }

    }
}
