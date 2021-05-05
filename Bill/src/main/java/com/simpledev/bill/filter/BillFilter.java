package com.simpledev.bill.filter;

import com.auth0.jwt.interfaces.Claim;
import com.simpledev.bill.exception.BillException;
import com.simpledev.bill.util.JWTUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@WebFilter(urlPatterns = "/*")
public class BillFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        if (!httpServletRequest.getRequestURI().endsWith("/login") &&
                !httpServletRequest.getRequestURI().endsWith(".html")) {
            final HttpServletRequest request = httpServletRequest;
            final HttpServletResponse response = httpServletResponse;

            response.setCharacterEncoding("UTF-8");
            //获取 header里的token
            String token = request.getHeader("authorization");
            if (StringUtils.isEmpty(token) && request.getCookies() != null) {
                // 从cookie
                for (Cookie cookie : request.getCookies()) {
                    System.out.println("---" + cookie);
                    if (cookie.getName().equals("authorization")) {
                        token = cookie.getValue();
                    }
                }
            }
            if ("OPTIONS".equals(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
                filterChain.doFilter(request, response);
            }
            // Except OPTIONS, other request should be checked by JWT
            else {

                if (token == null) {
                    response.getWriter().write("missing token");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    throw new BillException(BillException.NO_LOGIN, "");
                }

                Map<String, Claim> userData = JWTUtil.verifyToken(token);
                if (userData == null) {
                    response.getWriter().write("token  ERR");
                    throw new BillException(BillException.NO_LOGIN, "");
                }
                Integer id = userData.get("id").asInt();
                String name = userData.get("name").asString();
                String userName = userData.get("userName").asString();
                //拦截器 拿到用户信息，放到request中
                request.setAttribute("id", id);
                request.setAttribute("name", name);
                request.setAttribute("userName", userName);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
