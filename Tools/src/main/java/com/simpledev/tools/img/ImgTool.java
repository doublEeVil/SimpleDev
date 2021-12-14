package com.simpledev.tools.img;

import sun.misc.BASE64Encoder;

import java.io.*;

public class ImgTool {
    public static void main(String ... args) throws Exception {



        String ret = new ImgTool().imgToBase64("C:\\Users\\Lenovo\\Desktop\\常见材料证件照\\营业执照.jpeg");
        //ret = ret.replaceAll("\n", "");
        //System.out.println(ret);
        new FileWriter("C:\\Users\\Lenovo\\Desktop\\常见材料证件照\\base.txt").write(ret);
    }

    public String imgToBase64(String filePath) throws Exception {
        InputStream in = new FileInputStream(filePath);
        BASE64Encoder encoder = new BASE64Encoder();
        byte[] data = new byte[in.available()];
        in.read(data);
        String encode = "data:image/jpeg;base64," + encoder.encode(data);
        return encode;
    }
}
