package com.simpledev.tools.webui_op;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 直接操作浏览器进行自动化测试
 * 使用selenium
 * 需要下载驱动：谷歌  chromedriver.exe
 *            火狐  geckodriver.exe
 */
public class WebUIOP_DB {

    static {
        System.setProperty("webdriver.chrome.driver", "c://driver//chromedriver.exe");
    }
    static final WebDriver webDriver = new ChromeDriver();

    public static void main(String ... args) throws Exception {
        new WebUIOP_DB().webPageCheck();
    }


    public void webPageCheck() throws Exception {

        System.setProperty("webdriver.chrome.driver", "c://driver//chromedriver.exe");

        //System.setProperty("webdriver.gecko.driver", "c://geckodriver.exe");
        //WebDriver webDriver = new FirefoxDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().deleteAllCookies();
        // 与浏览器同步非常重要，必须等待浏览器加载完毕
        webDriver.manage().timeouts().implicitlyWait(10,
                TimeUnit.SECONDS);

        action();

        Thread.sleep(1000);
    }

    public  void action() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String host = "127.0.0.1:3306";
        String db = "zhengwu";
        String user = "root";
        String pwd = "123456";
        Connection conn1 = DriverManager.getConnection("jdbc:mysql://" + host + "/" + db + "?useUnicode=true&characterEncoding=utf8", user, pwd);
        PreparedStatement pstmt;
        // pstmt = conn1.prepareStatement("select `id`,`错敏字`,`所在地址` from xinmeiti_cuozi where `处理情况` is null or  `处理情况` = '未处理'");
        pstmt = conn1.prepareStatement("select `id`,`错敏字`,`所在地址` from xinmeiti_cuozi where `处理情况` is null ");
        ResultSet rs = pstmt.executeQuery();
        List<String[]> list = new ArrayList<>();
        while (rs.next()) {
            String[] data = new String[] {rs.getString(1),
                    rs.getString(2),
                    rs.getString(3)};
            list.add(data);
        }

        PreparedStatement pstmtUpdate =  conn1.prepareStatement("update xinmeiti_cuozi set 处理情况=? where id=?");

        int cnt = 0;

        for (String[] data : list) {
            String ret = check(data[1], data[2]);
            pstmtUpdate.setNString(1, ret);
            pstmtUpdate.setInt(2, Integer.valueOf(data[0]));
            pstmtUpdate.addBatch();
            cnt++;
            if (cnt >= 50) {
                cnt = 0;
                pstmtUpdate.executeBatch();
            }
        }
        pstmtUpdate.executeBatch();
    }

    public String check(String keyword, String url) {
        webDriver.get(url);
        String pageXml = webDriver.getPageSource();
        if (pageXml.contains("该内容已被发布者删除")) {
            return "已删除";
        }

        //System.out.println(pageXml);
        if (pageXml.contains(keyword)) {
            return "未处理";
        } else {
            return "已处理";
        }
    }

    public void start() throws Exception {
        System.setProperty("webdriver.chrome.driver", "c://driver//chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        //System.setProperty("webdriver.gecko.driver", "c://geckodriver.exe");
        //WebDriver webDriver = new FirefoxDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().deleteAllCookies();
        // 与浏览器同步非常重要，必须等待浏览器加载完毕
        webDriver.manage().timeouts().implicitlyWait(10,
                TimeUnit.SECONDS);

        //打开目标地址
        webDriver.get("https://baidu.com");
        //System.out.println(webDriver.getPageSource());
        webDriver.findElement(By.id("kw")).sendKeys("赣州");
        webDriver.findElement(By.id("su")).click();
        System.out.println(webDriver.findElement(By.id("kw")).getText());

        Thread.sleep(4000);
    }

    /***
     * 测试
     * @throws Exception
     */
    public void test() throws Exception {
        Thread.sleep(3000);
        System.setProperty("webdriver.chrome.driver", "c://driver//chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        //System.setProperty("webdriver.gecko.driver", "c://geckodriver.exe");
        //WebDriver webDriver = new FirefoxDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().deleteAllCookies();
        // 与浏览器同步非常重要，必须等待浏览器加载完毕
        webDriver.manage().timeouts().implicitlyWait(10,
                TimeUnit.SECONDS);

        //打开目标地址
        webDriver.get("http://192.168.0.32:88");
        //输入账号 密码并登陆系统
        Thread.sleep(1000);
        webDriver.findElement(By.xpath("/html/body/div/div/form/div[1]/div/div/input")).sendKeys("admin");
        webDriver.findElement(By.xpath("/html/body/div/div/form/div[2]/div/div/input")).sendKeys("123456");
        webDriver.findElement(By.cssSelector("html body div#app div.loginPage form.el-form.fromBox button.el-button.loginBtn.el-button--primary")).click();

        //选择系统
        Thread.sleep(1000);
        webDriver.findElement(By.xpath("/html/body/div/div/div/div[1]/p")).click();

        //展开基础信息管理菜单
        Thread.sleep(1000);
        webDriver.findElement(By.xpath("/html/body/div/div/div/div[1]/div[3]/ul/div[1]/li/div/span")).click();
        //点击科室管理菜单
        Thread.sleep(1000);
        webDriver.findElement(By.cssSelector(".is-opened > ul:nth-child(2) > li:nth-child(1)")).click();

        //跳转到第2页
        Thread.sleep(1000);
        webDriver.findElement(By.xpath("/html/body/div/div/div/div[2]/div[2]/div/div[2]/div[2]/div/div[2]/ul/li[2]")).click();

        //点击新增按钮
        webDriver.findElement(By.xpath("/html/body/div/div/div/div[2]/div[2]/div/div[2]/div[1]/div/div[1]/div[1]/button")).click();

        //根据规则随机生成文本框内容
        int random = new Random().nextInt(200000000);
        Thread.sleep(1000);
        webDriver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div[2]/div/div[3]/div/div[2]/form/div[1]/div[1]/div/div/input")).sendKeys(String.valueOf(random));
        Thread.sleep(1000);
        webDriver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div[2]/div/div[3]/div/div[2]/form/div[1]/div[2]/div/div[1]/input")).sendKeys("自动化测试-"+random);

        //展开下拉框
        webDriver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div[2]/div/div[3]/div/div[2]/form/div[2]/div[1]/div/div/div[1]/span/span/i")).click();
        Thread.sleep(1000);
        //获取下拉框size
        List<WebElement> select1 = webDriver.findElements(By.cssSelector("div.el-select-dropdown:nth-child(4) > div:nth-child(1) > div:nth-child(1) > ul li"));
        //随机选择一个项目
        int selectItem1 = new Random().nextInt(select1.size())+1;
        webDriver.findElement(By.xpath("/html/body/div[3]/div[1]/div[1]/ul/li["+selectItem1+"]")).click();

        //稍作停顿，然后保存
        Thread.sleep(1000);
        webDriver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div[2]/div/div[3]/div/div[3]/div/button[1]")).click();

        //跳转到我的博客
        Thread.sleep(3000);
        webDriver.get("https://www.cnblogs.com/xiaochangwei");

        webDriver.findElements(By.className("postTitle")).forEach(x -> {
            System.out.println(x.getText());
        });

        Thread.sleep(1000);
        //打开标题为 通过Dockerfile构建镜像并发布web项目 的文章
        webDriver.findElement(By.partialLinkText("通过Dockerfile构建镜像并发布web项目")).click();

        Thread.sleep(1000);
        //移动到底部
        //((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        //移动到指定的坐标(相对当前的坐标移动)
        ((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0, 700)");
        Thread.sleep(1000);
        //移动到窗口绝对位置坐标，如下移动到纵坐标1600像素位置
        ((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, 1600)");
        Thread.sleep(1000);
        //移动到指定元素，且元素底部和窗口底部对齐 参考 https://www.cnblogs.com/testway/p/6693140.html
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(false);", webDriver.findElement(By.xpath("/html/body/div[2]/div[2]/div[1]/div/div[1]/div/div/div[2]/div[4]/div[3]/div[1]/a[5]/img")));
        //暂停五秒钟后关闭
        Thread.sleep(2000);
        webDriver.quit();
    }
}
