package com.simpledev.other.classreload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        try {
            byte[] data = Files.readAllBytes(Paths.get("target\\classes\\com\\simpledev\\other\\classreload\\" + name + ".class"));
            return defineClass(name, data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }
}
