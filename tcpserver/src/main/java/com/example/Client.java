package com.example;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Newtrek on 2017/4/16.
 */
public class Client {
    public static void main(String[] args){

        try {
            Socket socket=new Socket("localhost",9999);
            if (socket==null){
                return;
            }
            System.out.println("link Server Success!");
            new ReadThread(socket).start();

            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner scanner=new Scanner(System.in);
            String input=null;
            while ((input=scanner.nextLine())!=null) {
                if (input.equals("stop")) {
                    break;
                }
                bufferedWriter.write(input);
                bufferedWriter.flush();
                input=null;
            }
            bufferedWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
