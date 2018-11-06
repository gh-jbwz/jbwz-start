package com.jbwz.lemon.server.security.filter;

import com.jbwz.lemon.server.security.common.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

@WebFilter(urlPatterns = "/*", filterName = "accessLogFilter")
@Order(1)
public class LogAccessFilter extends AbstractExcludeStaticUrlFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(LogAccessFilter.class);


  /**
   * 记录每个请求的日志，除了静态资源
   */
  @Override
  public void doFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
    logRequest(servletRequest);
  }

  /**
   * 记录请求日志
   */
  private void logRequest(HttpServletRequest request) {
    String path = request.getServletPath();
    ServletWebRequest servletWebRequest = new ServletWebRequest(request);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("HTTP REQUEST:　METHOD[").append(request.getMethod())
        .append("] Content-Type[")
        .append(request.getContentType()).append("] PATH[").append(path).append("] PARAMETERS[");
    Iterator<String> iterator = servletWebRequest.getParameterNames();
    while (iterator.hasNext()) {
      String s = iterator.next();
      stringBuilder.append(s);
      stringBuilder.append(":");
      if ("password".equals(s)) {
        stringBuilder.append("PROTECTED");
      } else {
        stringBuilder.append(request.getParameter(s));
      }
      if (iterator.hasNext()) {
        stringBuilder.append(",");
      }
    }
    stringBuilder.append("] IP[").append(RequestUtil.getIpAddr(request)).append("]");
    LOGGER.info(stringBuilder.toString());
  }

}
