package com.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * @author rtw
 * @since 2018/10/29
 */
public class Server {
    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(8080);
            System.out.println("TimeServer Started on 8080...");
            while (true) {
                //accept会一直阻塞到一个连接建立，会返回一个新的Socket用于客户端和服务端之间的通信。该ServerSocket将继续监听传入的连接
                Socket client = server.accept();
                new Thread(new ServerHandler(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static class ServerHandler implements Runnable {
        /**
         * 服务端套接字,服务器与客户端都通过 Socket 来收发数据.
         */
        private Socket clientSocket;
        //构造函数
        public ServerHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            BufferedReader reader = null;
            PrintWriter writer = null;
            try {
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new PrintWriter(clientSocket.getOutputStream());
                //因为一个client可以发送多次请求，所以每一次循环相当于接收处理一次请求
                while (true) {
                    //这里也会阻塞，就是系统把数据放到进程的第一第二阶段阻塞
                    String request = reader.readLine();
                    if (!"get time".equals(request)) {
                        writer.println("bad request");
                    } else {
                        //把当前时间写入输出流
                        writer.println(LocalDateTime.now().toString());
                    }
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                    reader.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
