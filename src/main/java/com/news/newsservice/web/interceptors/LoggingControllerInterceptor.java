package com.news.newsservice.web.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static com.news.newsservice.web.interceptors.LoggingTemplates.TEMPLATE_HTTP_REQUEST_LOGGING;
import static com.news.newsservice.web.interceptors.LoggingTemplates.TEMPLATE_HTTP_RESPONSE_LOGGING;

@Slf4j
public class LoggingControllerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        log.info(TEMPLATE_HTTP_REQUEST_LOGGING,
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString(),
                request.getSession().getId());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        log.info(TEMPLATE_HTTP_RESPONSE_LOGGING,
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString(),
                request.getSession().getId(),
                response.getStatus());
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
