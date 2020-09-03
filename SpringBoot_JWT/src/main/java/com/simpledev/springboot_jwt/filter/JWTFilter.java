package com.simpledev.springboot_jwt.filter;

import com.auth0.jwt.interfaces.Claim;
import com.simpledev.springboot_jwt.util.JWTUtil;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

//@Component
@WebFilter(filterName = "JwtFilter", urlPatterns = "/admin/*")
public class JWTFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        response.setCharacterEncoding("UTF-8");
        //获取 header里的token
        final String token = request.getHeader("authorization");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
        }
        // Except OPTIONS, other request should be checked by JWT
        else {

            if (token == null) {
                response.getWriter().write("missing token");
                return;
            }

            Map<String, Claim> userData = JWTUtil.verifyToken(token);
            if (userData == null) {
                response.getWriter().write("token  ERR");
                return;
            }
            Integer id = userData.get("id").asInt();
            String name = userData.get("name").asString();
            String userName = userData.get("userName").asString();
            //拦截器 拿到用户信息，放到request中
            request.setAttribute("id", id);
            request.setAttribute("name", name);
            request.setAttribute("userName", userName);
            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
