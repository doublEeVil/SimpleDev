package com.simpledev.other.classreload;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// 测试类
public class TestClassReload {

    public static Map<String, IActor> actorMap = new HashMap<>();

    public static void main(String ... args) {
        String classPath = "target\\classes";
        Container container = new Container(classPath);
        container.setListener(clz -> {
            if (actorMap.containsKey(clz.getName())) {
                try {
                    actorMap.put(clz.getName(), (IActor) clz.newInstance());
                } catch (Exception e) {

                }
            }
        });

        Scanner scanner = new Scanner(System.in);
        String line;
        while (true) {
            line = scanner.nextLine();
            System.out.println(line);
            String[] datas = line.split("\\s+");
            if (datas.length < 2) continue;
            try {
                service(container, datas[0], datas[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void service(Container container, String module, String input) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        IActor actiontor = actorMap.get(module);
        if (actiontor == null) {
            Class<?> clz = container.getOrLoadClz(module);
            if (clz == null) {
                throw new RuntimeException(module + " not found");
            }
            actiontor = (IActor) clz.newInstance();
            actorMap.put(module, actiontor);
        }
        actiontor.action(input);
    }
}
