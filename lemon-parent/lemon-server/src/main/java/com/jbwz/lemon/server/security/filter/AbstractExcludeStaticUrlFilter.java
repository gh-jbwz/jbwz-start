package com.jbwz.lemon.server.security.filter;


import com.jbwz.lemon.server.security.SecurityMVCConfig;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractExcludeStaticUrlFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();
        if (SecurityMVCConfig.isNotStaticUrl(path)) {
            doFilter(request, response);
        }
        chain.doFilter(request, response);
        if (SecurityMVCConfig.isNotStaticUrl(path)) {
            doFilterAfter(request, response);
        }
    }

    protected abstract void doFilter(HttpServletRequest servletRequest,
                                     HttpServletResponse servletResponse);

    protected void doFilterAfter(HttpServletRequest servletRequest,
                                 HttpServletResponse servletResponse) {
    }

    @Override
    public void destroy() {

    }
}
