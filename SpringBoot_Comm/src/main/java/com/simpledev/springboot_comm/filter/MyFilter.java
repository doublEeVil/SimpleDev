package com.simpledev.springboot_comm.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Filter和上面的拦截器很像，不过一个servlet的标准，一个是spring的组件
 * 运行顺序是filter->interceptor
 */
@Component
@WebFilter(filterName = "myFilter",urlPatterns = "/*")
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("filter:" + servletRequest.getRemoteAddr() + " " + servletRequest.getLocalAddr());
        if (true) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletResponse.getWriter().flush();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {
        System.out.println("---filter destroy");
    }
}
