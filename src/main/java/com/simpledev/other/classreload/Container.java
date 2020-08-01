package com.simpledev.other.classreload;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// 容器类
public class Container {
    public void start() {
        new WatchingThread().start();

        Scanner scanner = new Scanner(System.in);
        String line;
        while (true) {
            line = scanner.nextLine();
            System.out.println(line);
            String[] datas = line.split("\\s+");
            if (datas.length < 2) continue;
            try {
                service(datas[0], datas[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    Map<String, IActiontor> actiontorMap = new HashMap<>();
    Map<String, SimpleClassLoader> classLoaderMap = new HashMap<>();

    private void service(String module, String input) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        IActiontor actiontor = actiontorMap.get(module);
        if (actiontor == null) {
            SimpleClassLoader loader = new SimpleClassLoader();
            Class<?> clz = loader.findClass(module);
            actiontor = (IActiontor) clz.newInstance();
            actiontorMap.put(module, actiontor);
            classLoaderMap.put(module, loader);
        }
        actiontor.action(input);
    }

    public static void main(String ... args) {
        new Container().start();
    }
}
