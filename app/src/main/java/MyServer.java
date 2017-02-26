import android.app.Notification;
import android.os.Message;

import com.example.asus.telling.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Handler;

/**
 * Created by asus on 2017/1/16.
 */
public class MyServer {

    //定义ServerSocket的端口号
    private static final int SOCKET_PORT = 50000;
    //使用ArrayList存储所有的Socket
    public static ArrayList<Socket> socketList = new ArrayList<Socket>();

    private class MyHandler extends Handler{
        public  void handleMessage(Message msg)
    }


    public void initMyServer(){
        try {
            //创建一个ServerSocket，用于监听客户端Socket的连接请求
            ServerSocket serverSocket = new ServerSocket(SOCKET_PORT);
            while (true) {
               //每当客户端的Socket请求，服务器端也相应的创建一个Socket
                Socket socket = serverSocket.accept();
                socketList.add(socket);
                //每连接一个客户端，启动一个ServerThread线程为客户端服务
                new Thread(new ServerThread(socket)).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void initSocket(){
        try {
            socketUser1 = new Socket(URL_PATH, SOCKET_PORT);
            socketUser2 = new Socket(URL_PATH, SOCKET_PORT);
            clientThread = new ClientThread();

            clientThread.start();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String [] args) {
        MyServer myServer = new MyServer();
        myServer.initMyServer();
    }
}
