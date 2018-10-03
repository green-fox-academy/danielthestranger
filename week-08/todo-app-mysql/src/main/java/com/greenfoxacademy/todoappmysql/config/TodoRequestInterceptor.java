package com.greenfoxacademy.todoappmysql.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.greenfoxacademy.todoappmysql.utils.RequestLogFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;


@Component
public class TodoRequestInterceptor extends HandlerInterceptorAdapter {
    private final static Logger log = LoggerFactory.getLogger(TodoRequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String postData;
        HttpServletRequest requestCacheWrapperObject = null;
        try {
            requestCacheWrapperObject = new ContentCachingRequestWrapper(request);
            requestCacheWrapperObject.getParameterMap();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            log.info(RequestLogFormat.fromRequest(request));
        }
        return true;
    }
}
