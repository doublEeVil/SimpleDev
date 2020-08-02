package com.simpledev.other.classreload;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WatchingThread implements Runnable {

    private String classPath;
    private Container container;
    private Map<String, Long> fileMd5Map = new HashMap<>();

    public WatchingThread(String classPath, Container container) {
        if (!classPath.endsWith(File.separator)) {
            classPath += File.separator;
        }
        this.classPath = classPath;
        this.container = container;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000L);
                Set<String> set = fileMd5Map.keySet();
                for (String s : set) {
                    File file = new File(classPath + SimpleClassLoader.className2Path(s) + ".class");
                    if (file.lastModified() != fileMd5Map.get(s)) {
                        container.getOrLoadClz(s);
                        System.out.println("---class:" + s + " reload");
                        fileMd5Map.put(s, file.lastModified());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void add(String name) {
        File file = new File(classPath + SimpleClassLoader.className2Path(name) + ".class");
        fileMd5Map.putIfAbsent(name, file.lastModified());
    }

    public void start() {
        new Thread(this, "Watching-Thread").start();
    }
}
