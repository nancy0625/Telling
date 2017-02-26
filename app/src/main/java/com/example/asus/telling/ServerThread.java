package com.example.asus.telling;

import android.os.Bundle;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.security.Key;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by asus on 2017/1/16.
 */
public class ServerThread implements Runnable {

    //定义当前线程所处理的Socket
    private Socket socket = null;

    //该线程所处理的Socket对应的输入流
    private BufferedReader bufferedReader = null;

    //ServerThread的构造方法
    public ServerThread(Socket socket) throws IOException {
        this.socket = socket;
        //获取该socket对应的输入流
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    //实现run（）方法，讲读到的客户端内容进行广播

    public void run(){
        try {
            String content = null;
            //采用循环不断从Socket中读取客户端发送过来的数据
            while ((content = bufferedReader.readLine()) != null){
                Bundle bundle = new Bundle();
                bundle.putString(KEY_CONTENT,content);
                Message msg = new Message();
                msg.setData(bundle);
                myHandler.sendMessage(msg);
                //将读到的内容向每个Socket发送一次
                for (Socket socket : MyServer.socketList) {
                    PrintStream printStream = new PrintStream(socket.getOutputStream());
                     //向该输出流写入要广播的内容
                    printStream.println(packMessage(content));
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }




    // Function 对要广播的数据进行包装
    private String packMessage(String content){
        String result = null;
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");  //设置日期格式
        if (content.startsWith("USER_ONE")) {
            String message = content.substring(8);    //获取用户发送的真实信息
            result = "\n" + "依然" + df.format(new Date()) + "\n" + message;
        }
        if (content.startsWith("USER_TWO")){
            String message = content.substring(8);   //获取用户发送的真实信息
            result = "\n"+ "往事" + df.format(new Date()) + "\n" + message;
        }
        return result;
    }
}
