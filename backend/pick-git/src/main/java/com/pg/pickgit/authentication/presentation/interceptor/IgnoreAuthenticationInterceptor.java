package com.pg.pickgit.authentication.presentation.interceptor;

import com.pg.pickgit.authentication.application.OAuthService;
import com.pg.pickgit.authentication.infrastructure.dto.AuthorizationExtractor;
import com.pg.pickgit.exception.authentication.InvalidTokenException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class IgnoreAuthenticationInterceptor implements HandlerInterceptor {

    private final OAuthService oAuthService;
    public IgnoreAuthenticationInterceptor(OAuthService oAuthService){ this.oAuthService = oAuthService; }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception{
        if(isPreflightRequest(request)){ return true; }
        String authentication = AuthorizationExtractor.extract(request);
        if(Objects.nonNull(authentication) && !oAuthService.validateToken(authentication)){
            throw new InvalidTokenException();
        }
        request.setAttribute(AuthHeader.AUTHENTICATION, authentication);
        return true;
    }

    private boolean isPreflightRequest(HttpServletRequest request){
        return isOptions(request)
                && hasAccessControlRequestHeaders(request)
                && hasAccessControlRequestMethod(request)
                && hasOrigin(request);
    }

    private boolean isOptions(HttpServletRequest request){
        return request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.toString());
    }

    private boolean hasAccessControlRequestHeaders(HttpServletRequest request){
        return Objects.nonNull(request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS));
    }

    private boolean hasAccessControlRequestMethod(HttpServletRequest request){
        return Objects.nonNull(request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD));
    }

    private boolean hasOrigin(HttpServletRequest request){
        return Objects.nonNull(request.getHeader(HttpHeaders.ORIGIN));
    }
}
