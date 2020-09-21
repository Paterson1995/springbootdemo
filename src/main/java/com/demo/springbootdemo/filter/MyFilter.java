package com.demo.springbootdemo.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", filterName = "myFilter")
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        System.out.println("-------------------------------------------Enter filter at "+start);
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("-------------------------------------------Exit filter,execute cost " + (System.currentTimeMillis() - start)+"ms");
    }

    @Override
    public void destroy() {

    }

}
