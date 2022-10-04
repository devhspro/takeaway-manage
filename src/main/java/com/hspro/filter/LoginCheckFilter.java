package com.hspro.filter;

import com.alibaba.fastjson.JSON;
import com.hspro.common.R;
import com.hspro.enity.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/9/27
 */
@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/login",
                "/user/logout"
        };
        if(check(urls, requestURI)){
            log.info("本次登录无需拦截");
            filterChain.doFilter(request, response);
            return;
        }

        if(request.getSession().getAttribute("uid") != null){
            log.info("已登录无需拦截");
            BaseContext.setId((Long) request.getSession().getAttribute("uid"));
            filterChain.doFilter(request, response);
            return;
        }
        if(request.getSession().getAttribute("userId") != null){
            log.info("已登录无需拦截");
            BaseContext.setId((Long) request.getSession().getAttribute("userId"));
            filterChain.doFilter(request, response);
            return;
        }
        log.info("未登录，拦截成功");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }
    public boolean check(String[] urls, String requestURI){
        for(String url:urls){
            if(PATH_MATCHER.match(url, requestURI)){
                return true;
            }
        }
        return false;
    }
}
