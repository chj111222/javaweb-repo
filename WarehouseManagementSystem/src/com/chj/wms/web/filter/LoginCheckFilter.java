package com.chj.wms.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // 获取请求路径
        String servletPath = request.getServletPath();

        HttpSession session = request.getSession(false);

        if("/index.jsp".equals(servletPath) || "/welcome".equals(servletPath) ||
                "/admin/login".equals(servletPath)|| "/error.jsp".equals(servletPath)  || "/admin/exit".equals(servletPath)
                || "/medium/2.jpg".equals(servletPath)
                || (session != null && session.getAttribute("admin") != null)){
            // 继续往下走
            chain.doFilter(request, response);
        }else{
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}
