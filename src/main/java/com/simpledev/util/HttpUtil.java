package com.simpledev.util;

import java.io.*;
import java.net.URL;

public class HttpUtil {

    /**
     * 下载单个文件
     * @param url
     */
    public static void download(String url) {
        download(url, null);
    }

    private static String getDownloadName(String url) {
        String[] datas = url.split("/");
        return datas[datas.length - 1];
    }

    public static void download(String url, String location) {
        if (location == null){
            location = "C:\\Users\\" + System.getenv("UserName") + "\\Downloads\\";
        } else if (!location.endsWith("\\")) {
            location += "\\";
        }
        String name = getDownloadName(url);
        System.out.println("----开始下载：" + url);
        try {
            URL openUrl = new URL(url);
            InputStream in = openUrl.openStream();
            byte[] data = new byte[1024 * 3];
            int c = -1;
            OutputStream out = new FileOutputStream(location + name);
            while ((c = in.read(data)) >= 0) {
                out.write(data, 0, c);
            }
            out.flush();
            out.close();
            in.close();
        } catch (IOException e) {
            System.out.println("---下载" + url + " 失败");
            e.printStackTrace();
        }
    }
}
