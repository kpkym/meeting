package com.jsu.util.socket;

import java.io.IOException;
import java.net.ServerSocket;

@SuppressWarnings("serial")
public class SocketServer{
    /**
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(9393);
        System.out.println("begin");

        while(true){
            new Thread(new TheadServer(ss)).start();
        }
    }
}