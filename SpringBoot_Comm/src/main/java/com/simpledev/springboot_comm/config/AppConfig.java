package com.simpledev.springboot_comm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AppConfig {
    @Value("${server.port}")
    private int port;
    @Value("${website.name}")
    private String websiteName;
    @Value("${website.url}")
    private String websiteUrl;
    @Value("${website.rid}")
    private int websiteRid;

    public int getPort() {
        return port;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public int getWebsiteRid() {
        return websiteRid;
    }

    @PostConstruct
    public void onInit() {
        System.out.printf("config[port:%s,websiteName:%s,websiteUrl:%s,websiteRid:%s]\n", port, websiteName, websiteUrl, websiteRid);
    }
}
