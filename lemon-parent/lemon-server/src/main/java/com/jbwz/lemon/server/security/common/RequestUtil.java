package com.jbwz.lemon.server.security.common;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 解析request header
 *
 * @author yyh
 */
public class RequestUtil {

  public static final Logger LOG = LoggerFactory.getLogger(RequestUtil.class);
  public static final String DEFAULT_ENCODE = "UTF-8";

  public static boolean isAppLogin(HttpServletRequest request) {
    String app = request.getHeader("app");
    return "true".equals(app);
  }


  /**
   * 获取访问用户的客户端IP（适用于公网与局域网）.
   */
  public static final String getIpAddr(HttpServletRequest request) {
    String ipString = request.getHeader("x-forwarded-for");
    if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
      ipString = request.getHeader("Proxy-Client-IP");
    }
    if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
      ipString = request.getHeader("WL-Proxy-Client-IP");
    }
    if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
      ipString = request.getRemoteAddr();
    }
    // 多个路由时，取第一个非unknown的ip
    String[] arr = ipString.split(",");
    for (String str : arr) {
      if (!"unknown".equalsIgnoreCase(str)) {
        ipString = str;
        break;
      }
    }
    return "0:0:0:0:0:0:0:1".equals(ipString) ? "127.0.0.1" : ipString;
  }

  /**
   * 获取去除根路径contextPath 的 path
   */
  public static String getPathWithinApplication(HttpServletRequest request) {
    String contextPath = getContextPath(request);
    String requestUri = getRequestUri(request);
    if (StringUtils.startsWithIgnoreCase(requestUri, contextPath)) {
      String path = requestUri.substring(contextPath.length());
      return StringUtils.isNotBlank(path) ? path : "/";
    } else {
      return requestUri;
    }
  }

  public static String getRequestUri(HttpServletRequest request) {
    String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
    if (uri == null) {
      uri = request.getRequestURI();
    }
    return normalize(decodeAndCleanUriString(request, uri));
  }

  // 59对应字符为分号";"
  private static String decodeAndCleanUriString(HttpServletRequest request, String uri) {
    uri = decodeRequestString(request, uri);
    int semicolonIndex = uri.indexOf(59);
    return semicolonIndex != -1 ? uri.substring(0, semicolonIndex) : uri;
  }

  public static String normalize(String path) {
    return normalize(path, true);
  }

  private static String normalize(String path, boolean replaceBackSlash) {
    if (path == null) {
      return null;
    } else {
      String normalized = path;
      if (replaceBackSlash && path.indexOf(92) >= 0) {
        normalized = path.replace('\\', '/');
      }

      if (normalized.equals("/.")) {
        return "/";
      } else {
        if (!normalized.startsWith("/")) {
          normalized = "/" + normalized;
        }

        while (true) {
          int index = normalized.indexOf("//");
          if (index < 0) {
            while (true) {
              index = normalized.indexOf("/./");
              if (index < 0) {
                while (true) {
                  index = normalized.indexOf("/../");
                  if (index < 0) {
                    return normalized;
                  }

                  if (index == 0) {
                    return null;
                  }

                  int index2 = normalized.lastIndexOf(47, index - 1);
                  normalized = normalized.substring(0, index2) + normalized.substring(index + 3);
                }
              }

              normalized = normalized.substring(0, index) + normalized.substring(index + 2);
            }
          }

          normalized = normalized.substring(0, index) + normalized.substring(index + 1);
        }
      }
    }
  }

  public static String getContextPath(HttpServletRequest request) {
    String contextPath = (String) request.getAttribute("javax.servlet.include.context_path");
    if (contextPath == null) {
      contextPath = request.getContextPath();
    }
    if ("/".equals(contextPath)) {
      contextPath = "";
    }
    return decodeRequestString(request, contextPath);
  }

  public static String decodeRequestString(HttpServletRequest request, String source) {
    String enc = determineEncoding(request);
    try {
      return URLDecoder.decode(source, enc);
    } catch (UnsupportedEncodingException var4) {
      return URLDecoder.decode(source);
    }
  }

  protected static String determineEncoding(HttpServletRequest request) {
    String enc = request.getCharacterEncoding();
    if (enc == null) {
      enc = DEFAULT_ENCODE;
    }

    return enc;
  }
}
