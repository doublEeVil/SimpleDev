package com.simpledev.other.jmx;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class JMXApp {

    /**
     * 将mbean注册进
     * @param args
     */
    public static void main(String ... args) {
        User user = new User();
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = null;
        // 设置MBean对象名,格式为：“域名：type=MBean类型,name=MBean名称”
        String mBeanName = "com.simpledev:type=user,name=user0";
        try {
            name = new ObjectName(mBeanName);
            server.registerMBean(user, name);
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        } catch (NotCompliantMBeanException e) {
            e.printStackTrace();
        } catch (InstanceAlreadyExistsException e) {
            e.printStackTrace();
        } catch (MBeanRegistrationException e) {
            e.printStackTrace();
        }
        while (true);
    }
}
