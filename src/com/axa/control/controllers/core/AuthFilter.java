package com.axa.control.controllers.core;



import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AuthFilter implements Filter {
    private FilterConfig customedFilterConfig;


    public void doFilter(ServletRequest req, ServletResponse resp,FilterChain chain) throws IOException, ServletException {


            chain.doFilter(req, resp);

    }

    public void init(FilterConfig customedFilterConfig) throws ServletException {
        this.customedFilterConfig = customedFilterConfig;
    }

    public void destroy() {
        customedFilterConfig = null;
    }
}


