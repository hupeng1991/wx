package com.hp.wx.interceptor;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hp.wx.until.Const;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author phu
 */
@Repository
public class LoginInterceptor extends HandlerInterceptorAdapter {
  private static final String[] NO_FILTERS = new String[] {
      "/tologin",
      "/toindex.html",
      "/images/",
      "/css/",
      "/font/",
      "/js/",
      "/skins/",
      "/pluging/",
      "/code",
      "/tosignup",
      "/signup",
      "/templates/common/",
      "/login"
  };

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    String uri = request.getRequestURI();
    
    for (String noFilter : NO_FILTERS) {
      if (uri.startsWith(noFilter)) {
        return true;
      }
    }

    if (request.getSession().getAttribute(Const.SESSION_USER) == null) {
      response.sendRedirect("/tologin");
      return false;
    }

    return true;
  }
}
