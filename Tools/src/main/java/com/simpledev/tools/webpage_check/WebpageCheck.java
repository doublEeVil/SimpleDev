package com.simpledev.tools.webpage_check;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用htmlunit动态加载js，模拟动态网页
 * 但使用效果一般，比不上直接操作浏览器
 */
public class WebpageCheck {

    // static ExecutorService pool = Executors.newFixedThreadPool(8);

    //static final WebClient webClient = new WebClient(BrowserVersion.CHROME);//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象

    public static void main(String ... args) throws Exception {

        System.out.println(check("党史学习教育活动", "https://mp.weixin.qq.com/s?__biz=MzU2NzM2MTYyMA==&mid=2247496745&idx=1&sn=b8ccbc2cd1612d8a3c57bc95688f7af5&chksm=fc9cdd12cbeb5404a1fc4282bde772bf1783637b080bff2a3532bc7ccee64691cfd0cee6137b&scene=27"));
        if (true) {
           return;
        }
        init();
        String rootPath = "C:\\Users\\Lenovo\\Desktop\\错别字";
        File rootDir = new File(rootPath);
        File[] files = rootDir.listFiles();
        for (File file : files) {
            if (file.isHidden()) {
                continue;
            }
            if (file.isDirectory()) continue;
            if (!file.getName().startsWith("A")) continue;
            action(file);
            //break;
        }
    }

    private static void init() {
    }


    public static void action(File file) throws IOException {

        ZipSecureFile.setMinInflateRatio(0);
        Workbook wb = WorkbookFactory.create(file);
        int sheetCnt = wb.getNumberOfSheets();
        for (int i = 0; i < sheetCnt; i++) {
            Sheet sheet = wb.getSheetAt(i);
            String sheetName = sheet.getSheetName();
            System.out.printf("sheetName: %s\n", sheet);
            int rowCnt = 0;
            for (Row row : sheet) {
                rowCnt++;
                if (rowCnt < 83) {
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
            }
        }
        wb.write(new FileOutputStream("C:\\Users\\Lenovo\\Desktop\\错别字\\1\\" + file.getName()));
    }

    public static String check(String keyword, String url) throws Exception {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象

        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX



        HtmlPage page = null;

        page = webClient.getPage(url);//尝试加载上面图片例子给出的网页
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            webClient.close();
//        }


        webClient.waitForBackgroundJavaScript(6000);//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束
        System.out.println(page.isAttachedToPage());
        String pageXml = page.asXml();//直接将加载完成的页面转换成xml格式的字符串
        webClient.close();
        if (pageXml.contains("该内容已被发布者删除")) {
            return "已删除";
        }

        System.out.println(pageXml);
        if (pageXml.contains(keyword)) {
            return "未处理";
        } else {
            return "已处理";
        }
    }
}
