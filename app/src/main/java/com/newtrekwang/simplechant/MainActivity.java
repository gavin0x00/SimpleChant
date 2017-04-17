package com.newtrekwang.simplechant;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity>>>>>>>>>>";
    Button btn_connect,btn_send;
    EditText editText_ip,editText_message;
    TextView textView_message;
    private boolean isConnected=false;
    private Socket socket=null;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case 100:
                   String mess= (String) msg.obj;
                   textView_message.append(mess);
                   break;
               case 101:
                   break;
               default:
                   break;
           }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_connect= (Button) findViewById(R.id.btn_connect);
        btn_send= (Button) findViewById(R.id.btn_send);

        editText_ip= (EditText) findViewById(R.id.et_ip);
        editText_message= (EditText) findViewById(R.id.et_messge);

        btn_send.setOnClickListener(this);
        btn_connect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_connect:
                String ip=editText_ip.getText().toString();
                if (!TextUtils.isEmpty(ip)){
                    new ConnectThread(ip).start();
                }
                break;
            case R.id.btn_send:
                if (isConnected&&socket!=null){
                    String message=editText_message.getText().toString();
                    if (!TextUtils.isEmpty(message)){
                        new WriteThread(handler,socket,message).start();
                    }
                }
                break;
            default:
                break;
        }
    }

    private class ConnectThread extends Thread{
        private String ip;
        ConnectThread(String ip){
            this.ip=ip;
        }
        @Override
        public void run() {
            try {
                socket=new Socket(ip,9999);

                if (socket.isConnected()){
                    isConnected=true;
                    new ReadThread(handler,socket).start();
                }

            } catch (IOException e) {
                e.printStackTrace();
                isConnected=false;
                socket=null;
                Log.e(TAG, "run: !!!!!!!!!!!!!!!"+e.toString() );
            }
        }
    }


}
