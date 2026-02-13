package com.news.newsservice.aop;

import com.news.newsservice.entity.Role;
import com.news.newsservice.entity.RoleType;
import com.news.newsservice.exception.ForbiddenException;
import com.news.newsservice.exception.UserNotAuthenticatedException;
import com.news.newsservice.security.AppUserPrincipal;
import com.news.newsservice.service.CommentService;
import com.news.newsservice.service.NewsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import static com.news.newsservice.aop.AspectMessagesTemplates.*;
import static com.news.newsservice.aop.AspectMessagesTemplates.TEMPLATE_OPERATION_FORBIDDEN;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthoriseUsernameAspect {

    private final NewsService newsService;
    private final CommentService commentService;


    @Before("@annotation(AuthoriseUsernameForUserUpdateAndDelete)")
    public void authUsernameForUserBefore(JoinPoint joinPoint) {
        HttpServletRequest request = getRequest();

        loggingOperation(joinPoint, request);

        AppUserPrincipal principal = getUserDetails();

        if (isAdminOrModerator(principal)) {
            return;
        }

        Role role = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Role) {
                role = (Role) arg;
                if (!role.getAuthority().equals(RoleType.ROLE_USER)) {
                    throw new ForbiddenException(TEMPLATE_OPERATION_FORBIDDEN);
                }
            }
        }

        UUID userId = getId(request);

        UUID userIdPrincipal = principal.getUserId();

        if (!userIdPrincipal.equals(userId)) {
            throw new ForbiddenException(TEMPLATE_OPERATION_FORBIDDEN);
        }
    }

    @Before("@annotation(AuthoriseUsernameForNewsUpdateAndDelete)")
    public void authUsernameForNewsBefore(JoinPoint joinPoint) {
        HttpServletRequest request = getRequest();

        loggingOperation(joinPoint, request);

        AppUserPrincipal principal = getUserDetails();

//        if (isAdminOrModerator(principal)) {
//            return;
//        }

        UUID newsId = getId(request);

        UUID userId = newsService.findUserIdById(newsId);

        UUID userIdPrincipal = principal.getUserId();

        if (!userIdPrincipal.equals(userId)) {
            throw new ForbiddenException(TEMPLATE_OPERATION_FORBIDDEN);
        }
    }

    @Before("@annotation(AuthoriseUsernameForCommentUpdateAndDelete)")
    public void authUsernameForCommentBefore(JoinPoint joinPoint) {
        HttpServletRequest request = getRequest();

        loggingOperation(joinPoint, request);

        AppUserPrincipal principal = getUserDetails();

//        if (isAdminOrModerator(principal)) {
//            return;
//        }

        UUID commentId = getId(request);

        UUID userId = commentService.findUserIdById(commentId);

        UUID userIdPrincipal = principal.getUserId();

        if (!userIdPrincipal.equals(userId)) {
            throw new ForbiddenException(TEMPLATE_OPERATION_FORBIDDEN);
        }
    }

    private void loggingOperation(JoinPoint joinPoint,
                                  HttpServletRequest request) {
        Map<String, String> pathVariables =
                (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        log.info(CALL_OPERATION,
                auth.getName(),
                joinPoint.getSignature().getName(),
                pathVariables.toString(),
                Arrays.toString(joinPoint.getArgs()));
    }

    private UUID getId(HttpServletRequest request) {
        Map<String, String> pathVariables =
                (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);


        if (pathVariables.isEmpty()) {
            throw new ForbiddenException(TEMPLATE_OPERATION_FORBIDDEN);
        }

        return UUID.fromString(pathVariables.get("id"));
    }

    private HttpServletRequest getRequest() {
        RequestAttributes requestAttributes =
                RequestContextHolder.getRequestAttributes();

        if (requestAttributes == null) {
            throw new ForbiddenException(TEMPLATE_OPERATION_FORBIDDEN);
        }

        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    private AppUserPrincipal getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new UserNotAuthenticatedException(TEMPLATE_OPERATION_UNAUTHORIZED);
        }

        return (AppUserPrincipal) auth.getPrincipal();
    }

    private Boolean isAdminOrModerator(UserDetails userDetails) {
        return userDetails.getAuthorities()
                .stream()
                .anyMatch(ga ->
                        ga.getAuthority().equals(RoleType.ROLE_ADMIN.toString()) ||
                                ga.getAuthority().equals(RoleType.ROLE_MODERATOR.toString()));
    }
}
