package com.simpledev.springboot_mybatis_xml.reload;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class MybatisReloader {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    private Resource[] xmlLocations;
    private Configuration configuration;
    private Map<Resource, Long> modifiedMap = new HashMap<>();
    private Set<String> mapTypeFieldSet = new HashSet<>();
    private Set<String> setTypeFieldSet = new HashSet<>();

    @PostConstruct
    public void onStart() throws Exception {
        String xmlPathPtn = "classpath*:mapper/*.xml";
        xmlLocations = new PathMatchingResourcePatternResolver().getResources(xmlPathPtn);
        configuration = sqlSessionFactory.getConfiguration();

        mapTypeFieldSet.add("mappedStatements");
        mapTypeFieldSet.add("caches");
        mapTypeFieldSet.add("resultMaps");
        mapTypeFieldSet.add("parameterMaps");
        mapTypeFieldSet.add("keyGenerators");
        mapTypeFieldSet.add("sqlFragments");

        setTypeFieldSet.add("loadedResources");

        launch();
    }

    private void launch() {
        Executors.newScheduledThreadPool(1)
                .scheduleWithFixedDelay(() -> {
                    try {
                        tryRoload();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 5, 3, TimeUnit.SECONDS);
    }

    private void tryRoload() throws Exception {
        if (modifiedMap.isEmpty()) {
            for (Resource resource : xmlLocations) {
                modifiedMap.put(resource, resource.getFile().lastModified());
            }
        }
        for (Resource resource : xmlLocations) {
            if (modifiedMap.get(resource) == resource.getFile().lastModified()) {
                continue;
            }
            reload(resource);
            modifiedMap.put(resource, resource.getFile().lastModified());
        }
    }

    private void reload(Resource resource) throws Exception {
        Class<?> clz = configuration.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (mapTypeFieldSet.contains(field.getName())) {
                Map map = (Map)field.get(configuration);
                map.clear();
            }
            if (setTypeFieldSet.contains(field.getName())) {
                Set set = (Set) field.get(configuration);
                set.clear();
            }
        }
        XMLMapperBuilder builder = new XMLMapperBuilder(resource.getInputStream(),
                configuration,
                resource.toString(),
                configuration.getSqlFragments());
        builder.parse();
    }
}
