package com.simpledev.other.classreload;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleClassLoader extends ClassLoader {
    private String classPath;

    public SimpleClassLoader(String baseClassPath) {
        super();
        if (!baseClassPath.endsWith(File.separator)) {
            baseClassPath += File.separator;
        }
        classPath = baseClassPath;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] data = Files.readAllBytes(Paths.get(classPath + className2Path(name) + ".class"));
            return defineClass(null, data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    public Class<?> findClassOrNull(String name) {
        try {
            return findClass(name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static String className2Path(String clzName) {
        return clzName.replaceAll("\\.", "\\" + File.separator);
    }
}
