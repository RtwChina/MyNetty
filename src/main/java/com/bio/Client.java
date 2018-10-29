package com.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author rtw
 * @since 2018/10/29
 */
public class Client {
    public static void main(String[] args) {
        BufferedReader reader = null;
        PrintWriter writer = null;
        Socket client = null;

        try {
            client = new Socket("localhost",8080);
            writer = new PrintWriter(client.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            while (true) {
                writer.println("get time");
                writer.flush();
                String response = reader.readLine();
                System.out.println("现在时间：" + response);
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                reader.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
