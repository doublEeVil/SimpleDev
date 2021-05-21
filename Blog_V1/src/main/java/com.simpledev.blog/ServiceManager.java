package com.simpledev.blog;
import com.simpledev.blog.service.IAdminService;
import com.simpledev.blog.service.IArticleService;
import com.simpledev.blog.service.IEventLogService;
import com.simpledev.blog.service.IPicInfoService;
import com.simpledev.blog.service.impl.AdminServiceImpl;
import com.simpledev.blog.service.impl.ArticleServiceImpl;
import com.simpledev.blog.service.impl.EventLogService;
import com.simpledev.blog.service.impl.PicInfoService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class ServiceManager {
    private static ServiceManager                     instance;
    private static AnnotationConfigApplicationContext context;

    private ServiceManager() {
    }

    public static ServiceManager getInstance() {
        if (null == instance) {
            synchronized (ServiceManager.class) {
                if (null == instance) {
                    instance = new ServiceManager();
                    context = new AnnotationConfigApplicationContext();
                    context.register(BlogConfig.class);
                    context.refresh();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化一些数据
     */
    public void initData() {
        System.out.println("loading data ...");
        ServiceManager.getInstance().getPicInfoService().initPicData();
        ServiceManager.getInstance().getArticleService().onStart();
    }

    /**
     * 获得文章服务
     * @return
     */
    public IArticleService getArticleService() {
        return context.getBean(ArticleServiceImpl.class);
    }

    /**
     * 文章管理服务
     * @return
     */
    public IAdminService getAdminService() {
        return context.getBean(AdminServiceImpl.class);
    }

    /**
     * 图片管理
     * @return
     */
    public IPicInfoService getPicInfoService() {
        return context.getBean(PicInfoService.class);
    }

    /**
     * 2019年6月13日11:29:31记录
     * @return
     */
    public IEventLogService getEventLogService() {
        return context.getBean(EventLogService.class);
    }
}
