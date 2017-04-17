package com.newtrekwang.simplechant;

import android.os.Handler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class WriteThread extends Thread {
    private Handler handler;
    private Socket socket;
    private String message;

    public WriteThread(Handler handler, Socket socket,String message) {
        this.handler = handler;
        this.socket = socket;
        this.message=message;
    }


    @Override
    public void run() {
        try {
            OutputStream outputStream=socket.getOutputStream();
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream);
            outputStreamWriter.write(message);
            outputStream.flush();
          outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
