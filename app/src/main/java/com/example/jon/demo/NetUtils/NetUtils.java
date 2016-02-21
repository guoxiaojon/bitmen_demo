package com.example.jon.demo.NetUtils;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by jon on 2016/2/17.
 */
public class NetUtils {

    public static void write2Service(String s){
        Socket socket = null;

        try {
            socket= new Socket("192.168.1.106",10003);
            OutputStream out = socket.getOutputStream();
            out.write(s.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }


    public static String getFromService(String s){

        StringBuilder sb = new StringBuilder();
        String temp = null;
        try {
            Socket socket = new Socket("192.168.1.106",10003);

            OutputStream out = socket.getOutputStream();
            out.write(s.getBytes());
            out.close();
            socket.close();

            Socket socket_1 = new Socket("192.168.1.106",10003);
            InputStream in = socket_1.getInputStream();
            byte[] buf = new byte[1024];
            int len;


            while((len = in.read(buf) )!= -1){
                temp = new String(buf,0,len);

                sb.append(temp);

            }
            socket_1.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }
}
