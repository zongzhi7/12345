package com.cy.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    //检测是否有uid

    /**
     *
     * @param request
     * @param response
     * @param handler
     * @return 如果返回值为true则放行。若为false则拦截请求
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        Object obj=request.getSession().getAttribute("uid");
        if(obj==null){//未登录，重定向到login
            response.sendRedirect("/web/login.html");
            return false;
        }

        return true;
    }
}
