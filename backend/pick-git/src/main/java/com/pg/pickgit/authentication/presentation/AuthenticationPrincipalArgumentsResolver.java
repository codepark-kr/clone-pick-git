package com.pg.pickgit.authentication.presentation;

import com.pg.pickgit.authentication.application.OAuthService;
import com.pg.pickgit.authentication.domain.Authenticated;
import com.pg.pickgit.authentication.presentation.interceptor.AuthHeader;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationPrincipalArgumentsResolver implements HandlerMethodArgumentResolver {

    private final OAuthService oAuthService;

    public AuthenticationPrincipalArgumentsResolver(OAuthService oAuthService){ this.oAuthService = oAuthService; }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Authenticated.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ){
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String authentication = (String) request.getAttribute(AuthHeader.AUTHENTICATION);
        return oAuthService.findRequestUserByToken(authentication);
    }
}
