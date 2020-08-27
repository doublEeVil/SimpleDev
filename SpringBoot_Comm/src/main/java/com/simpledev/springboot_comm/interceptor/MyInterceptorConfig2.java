package com.simpledev.springboot_comm.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 拦截器配置的第二种写法，可以和第一种写法对比下
 */
@Configuration
public class MyInterceptorConfig2 extends WebMvcConfigurationSupport {
    @Autowired
    private MyInterceptor myInterceptor;

    /**
     * 静态资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
        // 解决 SWAGGER 404报错
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPa-thPatterns 用于添加拦截规则
        //excludePathPatterns 用于排除拦截
        /*要执行的拦截器*/
        registry.addInterceptor(myInterceptor).
                addPathPatterns("/**").
                excludePathPatterns("/**/login", "/**/esProductinfo/**");
    }

    /**
     * 解决跨域问题
     * 源（origin）就是协议、域名和端口号。
     * URL由协议、域名、端口和路径组成，如果两个URL的协议、域名和端口全部相同，
     * 则表示他们同源。否则，只要协议、域名、端口有任何一个不同，就是跨域
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")
                .maxAge(3600);
    }
}
