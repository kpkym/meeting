package com.jsu.util.socket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class TheadServer implements Runnable {
    private Socket s = null;
    public InputStream ins;

    public TheadServer(ServerSocket ss ) throws IOException {
        this.s=ss.accept();
        ins = s.getInputStream();

        InputStream is = ins;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }


        Files.write(Paths.get("myfile.txt"), buffer.toByteArray(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        ins.close();
    }
    @Override
    public void run(){
        try {
            System.out.println("线程运行，图片传输中!!!");
        }
        finally{
            try {
                if(!s.isClosed())
                    s.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
            }
        }
    }
}
