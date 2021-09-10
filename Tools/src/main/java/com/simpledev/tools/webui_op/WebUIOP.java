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
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 直接操作浏览器进行自动化测试
 * 使用selenium
 * 需要下载驱动：谷歌  chromedriver.exe
 *            火狐  geckodriver.exe
 */
public class WebUIOP {

    static {
        System.setProperty("webdriver.chrome.driver", "c://driver//chromedriver.exe");
    }
    static final WebDriver webDriver = new ChromeDriver();

    public static void main(String ... args) throws Exception {
        //new WebUIOP().webPageCheck();
        new WebUIOP().start();
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

        String rootPath = "C:\\Users\\Lenovo\\Desktop\\错别字\\0909";
        File rootDir = new File(rootPath);
        File[] files = rootDir.listFiles();
        for (File file : files) {
            if (file.isHidden()) {
                continue;
            }
            if (file.isDirectory()) continue;
            // if (!file.getName().startsWith("A")) continue;
            action(file);
            //break;
        }
        Thread.sleep(1000);
    }

    public  void action(File file) throws Exception {

        ZipSecureFile.setMinInflateRatio(0);
        Workbook wb = WorkbookFactory.create(file);
        int sheetCnt = wb.getNumberOfSheets();
        for (int i = 0; i < sheetCnt; i++) {
            Sheet sheet = wb.getSheetAt(i);
            //String sheetName = sheet.getSheetName();
            //System.out.printf("sheetName: %s\n", sheet);
            int rowCnt = 0;
            for (Row row : sheet) {
                rowCnt++;
                if (rowCnt == 1) {
                    continue;
                }
                if (row.getCell(11) == null) {
                    row.createCell(11);
                }
                if (row.getCell(11).equals("已处理")) {
                    continue;
                }
                String keyword = row.getCell(0).toString();
                String url = row.getCell(4).toString();
                System.out.println(rowCnt + "     " + keyword + "   " + url);
                try {
                    String  result = check(keyword, url);
                    row.getCell(11).setCellValue(result);
                } catch (Exception e) {
                    row.getCell(11).setCellValue("未知情况");
                }
                Thread.sleep(100);
            }
        }
        wb.write(new FileOutputStream("C:\\Users\\Lenovo\\Desktop\\错别字\\0909\\01" + file.getName()));
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
        webDriver.get("https://mp.weixin.qq.com/s?__biz=MzI1NTMyMDIxOQ==&mid=2247487982&idx=2&sn=b1925ad449c86b9a29abb4fdb8a78b15&chksm=ea36981add41110ccdf5f21b10b5854b9b850695562cddbb8d2282ca3d36e40f2737f3a1cb91&scene=27");
        System.out.println(webDriver.getPageSource());

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
