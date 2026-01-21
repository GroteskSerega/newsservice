package com.news.newsservice.aop;

import com.news.newsservice.entity.Comment;
import com.news.newsservice.entity.News;
import com.news.newsservice.exception.OperationUnauthorizedException;
import com.news.newsservice.service.CommentService;
import com.news.newsservice.service.NewsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Arrays;
import java.util.Map;

import static com.news.newsservice.aop.AspectMessagesTemplates.CALL_OPERATION;
import static com.news.newsservice.aop.AspectMessagesTemplates.TEMPLATE_OPERATION_UNAUTHORIZED;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthUsernameAspect {

    private final NewsService newsService;
    private final CommentService commentService;


    private static final String HEADER_USER = "HEADER_USER";

    @Before("@annotation(AuthUsernameForNewsUpdateAndDelete)")
    public void authUsernameForNewsBefore(JoinPoint joinPoint) {
        HttpServletRequest request = getRequest();

        loggingOperation(joinPoint, request);

        String username = getUsername(joinPoint, request);

        Long newsId = getId(request);

        News news = newsService.findById(newsId);

        if (!news.getUser().getUsername().equals(username)) {
            throw new OperationUnauthorizedException(TEMPLATE_OPERATION_UNAUTHORIZED);
        }
    }

    @Before("@annotation(AuthUsernameForCommentUpdateAndDelete)")
    public void authUsernameForCommentBefore(JoinPoint joinPoint) {
        HttpServletRequest request = getRequest();

        loggingOperation(joinPoint, request);

        String username = getUsername(joinPoint, request);

        Long commentId = getId(request);

        Comment comment = commentService.findById(commentId);

        if (comment.getUser().getUsername().equals(username)) {
            throw new OperationUnauthorizedException(TEMPLATE_OPERATION_UNAUTHORIZED);
        }
    }

    private void loggingOperation(JoinPoint joinPoint,
                                    HttpServletRequest request) {
        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        log.info(CALL_OPERATION,
                request.getHeader(HEADER_USER),
                joinPoint.getSignature().getName(),
                pathVariables.toString(),
                Arrays.toString(Arrays.stream(joinPoint.getArgs()).toArray()));
    }

    private String getUsername(JoinPoint joinPoint,
                               HttpServletRequest request) {
        String username = request.getHeader(HEADER_USER);

        if (username == null) {
            throw new OperationUnauthorizedException(TEMPLATE_OPERATION_UNAUTHORIZED);
        }

        return username;
    }

    private Long getId(HttpServletRequest request) {
        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);


        if (pathVariables.isEmpty()) {
            throw new OperationUnauthorizedException(TEMPLATE_OPERATION_UNAUTHORIZED);
        }

        return Long.valueOf(pathVariables.get("id"));
    }

    private HttpServletRequest getRequest() {
        RequestAttributes requestAttributes =
                RequestContextHolder.getRequestAttributes();

        if (requestAttributes == null) {
            throw new OperationUnauthorizedException(TEMPLATE_OPERATION_UNAUTHORIZED);
        }

        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }
}
