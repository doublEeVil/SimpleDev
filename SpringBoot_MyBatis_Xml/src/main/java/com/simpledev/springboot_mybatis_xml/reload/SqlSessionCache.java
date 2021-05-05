package com.simpledev.springboot_mybatis_xml.reload;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

/**
 * mybatis的mapper文件有改动时，进行重新加载
 * @author ycblus
 *
 */

//@Component
public class SqlSessionCache {
    private Logger log  = Logger.getLogger(SqlSessionCache.class);
    private Resource[] mapperLocations;
    private String packageSearchPath = "classpath*:mapper/*.xml";

    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private ApplicationContext context;

    Configuration configuration;
    private HashMap<String, Long> fileMapping = new HashMap<String, Long>();// 记录文件是否变化


    public void refreshMapper() throws Exception{
        try {
            try {
                this.scanMapperXml();
            } catch (IOException e) {
                log.error("packageSearchPath扫描包路径配置错误");
                return;
            }
            Runnable runnable = new Runnable() {
                public void run() {
                    // task to run goes here
                    try {
                        // 判断是否有文件发生了变化
                        if (isChanged()) {
                            // 清理
                            this.removeConfig(configuration);
                            // 重新加载
                            for (Resource configLocation : mapperLocations) {
                                try {
                                    XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configLocation.getInputStream(),
                                            configuration, configLocation.toString(), configuration.getSqlFragments());
                                    xmlMapperBuilder.parse();
                                    log.info("mapper文件[" + configLocation.getFilename() + "]加载成功");
                                } catch (IOException e) {
                                    log.error("mapper文件[" + configLocation.getFilename() + "]不存在或内容格式不对");
                                    continue;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                /**      * 清空Configuration中几个重要的缓存      * @param configuration      * @throws Exception      */
                private void removeConfig(Configuration configuration) throws Exception {
                    Class<?> classConfig = configuration.getClass();
                    clearMap(classConfig, configuration, "mappedStatements");
                    clearMap(classConfig, configuration, "caches");
                    clearMap(classConfig, configuration, "resultMaps");
                    clearMap(classConfig, configuration, "parameterMaps");
                    clearMap(classConfig, configuration, "keyGenerators");
                    clearMap(classConfig, configuration, "sqlFragments");
                    clearSet(classConfig, configuration, "loadedResources");
                }

                /**      * 判断文件是否发生了变化      * @param resource      * @return      * @throws IOException      */
                boolean isChanged() throws IOException {
                    boolean flag = false;
                    for (Resource resource : mapperLocations) {
                        String resourceName = resource.getFilename();
                        boolean addFlag = !fileMapping.isEmpty() && !fileMapping.containsKey(resourceName);// 此为新增标识
                        // 修改文件:判断文件内容是否有变化
                        Long compareFrame = fileMapping.get(resourceName);
                        long lastFrame = resource.contentLength() + resource.lastModified();
                        boolean modifyFlag = null != compareFrame && compareFrame.longValue() != lastFrame;// 此为修改标识

                        fileMapping.put(resourceName, Long.valueOf(lastFrame));// 文件内容帧值
                        // 新增或是修改时,存储文件
                        if(addFlag || modifyFlag) {
                            flag = true;
                        }
                    }
                    return flag;
                }
            };
            ScheduledExecutorService service = Executors
                    .newSingleThreadScheduledExecutor();
            // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
            service.scheduleAtFixedRate(runnable, 1, 10, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setPackageSearchPath(String packageSearchPath) {
        this.packageSearchPath = packageSearchPath;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**      * 扫描xml文件所在的路径      * @throws IOException       */
    private void scanMapperXml() throws IOException {
        this.mapperLocations = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
    }

    /**      * 清空Configuration中几个重要的缓存      * @param configuration      * @throws Exception      */
    private void removeConfig(Configuration configuration) throws Exception {
        Class<?> classConfig = configuration.getClass();
        clearMap(classConfig, configuration, "mappedStatements");
        clearMap(classConfig, configuration, "caches");
        clearMap(classConfig, configuration, "resultMaps");
        clearMap(classConfig, configuration, "parameterMaps");
        clearMap(classConfig, configuration, "keyGenerators");
        clearMap(classConfig, configuration, "sqlFragments");
        clearSet(classConfig, configuration, "loadedResources");
    }

    @SuppressWarnings("rawtypes")
    private void clearMap(Class<?> classConfig, Configuration configuration, String fieldName) throws Exception {
        Field field = classConfig.getDeclaredField(fieldName);
        field.setAccessible(true);
        Map mapConfig = (Map) field.get(configuration);
        mapConfig.clear();
    }

    @SuppressWarnings("rawtypes")
    private void clearSet(Class<?> classConfig, Configuration configuration, String fieldName) throws Exception {
        Field field = classConfig.getDeclaredField(fieldName);
        field.setAccessible(true);
        Set setConfig = (Set) field.get(configuration);
        setConfig.clear();
    }

    @PostConstruct
    public void onStart() throws Exception {
        try {
            if (sqlSessionFactory == null) {
                sqlSessionFactory = (SqlSessionFactory)context.getBean("sqlSessionFactory");
                if (sqlSessionFactory == null) {
                    sqlSessionFactory = context.getBean(SqlSessionFactoryBean.class).getObject();
                }
            }
            configuration = this.sqlSessionFactory.getConfiguration(); //扫描文件
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.refreshMapper();
    }
}