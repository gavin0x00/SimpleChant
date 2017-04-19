package com.newtrekwang.simplechant;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

class ReadThread extends Thread{
    private Handler handler;
    private Socket socket;

    ReadThread(Handler handler, Socket socket){
        this.handler=handler;
        this.socket=socket;
    }
    @Override
    public void run() {
        try {
            InputStream inputStream=socket.getInputStream();
            int size=-1;
            byte[] bytes=new byte[20];
            while ((size=inputStream.read(bytes))!=-1){
                String messa=new String(bytes,"utf-8");
                Message message=new Message();
                message.what=100;
                message.obj=messa;
                handler.sendMessage(message);
//                clearCache
                bytes=new byte[20];
            }
        } catch (IOException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(103);
        }

    }
}
