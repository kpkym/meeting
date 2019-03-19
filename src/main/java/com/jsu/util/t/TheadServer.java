package com.jsu.util.t;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class TheadServer implements Runnable {
    private Socket s = null;
    public InputStream ins;

    public TheadServer(ServerSocket ss ) throws IOException {

        System.out.println("start thread");
        this.s=ss.accept();
        /*读入流数据，并转成BufferImage类型*/
        ins = s.getInputStream();
        BufferedImage bi = ImageIO.read(ins);
        File file = new File("/Users/kpkym/images/" + new Date().getTime() + ".jpg");
        if (!file.exists()) {
            file.mkdirs();
        }
        if(ImageIO.write(bi, "jpg", file)){
            System.out.printf("save picture success!\n");
        }
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
