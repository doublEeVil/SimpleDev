package com.simpledev.tools.webui_op;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class WebUIOP_Zhihu {
    static {
        System.setProperty("webdriver.chrome.driver", "c://driver//chromedriver.exe");
    }
    static final WebDriver webDriver = new ChromeDriver();

    public static void main(String ... args) throws Exception {
        //new WebUIOP().webPageCheck();
        new WebUIOP_Zhihu().start();
    }

    public void start() throws Exception {
        webDriver.manage().window().maximize();
        webDriver.manage().deleteAllCookies();
        // 与浏览器同步非常重要，必须等待浏览器加载完毕
        webDriver.manage().timeouts().implicitlyWait(10,
                TimeUnit.SECONDS);

        //打开目标地址
        webDriver.get("https://www.zhihu.com/people/ren-min-ri-bao-jin-ju-zhai-lu/posts");

        // 需要登录
        if (webDriver.getPageSource().contains("登录知乎，与优秀答主深度交流")) {
            // 密码登录
            webDriver.findElement(By.xpath("/html[1]/body[1]/div[4]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/form[1]/div[1]/div[2]")).click();
            //
            webDriver.findElement(By.xpath("/html[1]/body[1]/div[4]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/form[1]/div[2]/div[1]/label[1]/input[1]"))
                    .sendKeys("@qq.com");
            webDriver.findElement(By.xpath("/html[1]/body[1]/div[4]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/form[1]/div[3]/div[1]/label[1]/input[1]"))
                    .sendKeys("");
            webDriver.findElement(By.xpath("/html[1]/body[1]/div[4]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/form[1]/button[1]"))
                    .click();
        }

        Thread.sleep(4000);
    }
}
