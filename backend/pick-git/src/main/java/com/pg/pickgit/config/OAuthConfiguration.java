package com.pg.pickgit.config;

import com.pg.pickgit.authentication.application.OAuthService;
import com.pg.pickgit.authentication.presentation.AuthenticationPrincipalArgumentsResolver;
import com.pg.pickgit.authentication.presentation.interceptor.AuthenticationInterceptor;
import com.pg.pickgit.authentication.presentation.interceptor.IgnoreAuthenticationInterceptor;
import com.pg.pickgit.authentication.presentation.interceptor.PathMatchInterceptor;
import com.pg.pickgit.config.auth_interceptor_register.register_type.AuthenticateStorageForRegisterType;
import com.pg.pickgit.config.auth_interceptor_register.register_type.IgnoreAuthenticateStorageForRegisterType;
import com.pg.pickgit.config.auth_interceptor_register.register_type.StorageForRegisterType;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

public class OAuthConfiguration implements WebMvcConfigurer {

    private static final String PACKAGE = "com.pg.pickgit";

    private final OAuthService oAuthService;

    public OAuthConfiguration(OAuthService oAuthService){ this.oAuthService = oAuthService; }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
        resolvers.add(authenticationPrincipalArgumentsResolver());
    }

    private AuthenticationPrincipalArgumentsResolver authenticationPrincipalArgumentsResolver(){
        return new AuthenticationPrincipalArgumentsResolver(oAuthService);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        PathMatchInterceptor authenticationInterceptor =
                new PathMatchInterceptor(authenticationInterceptor());
        PathMatchInterceptor ignoreAuthenticationInterceptor =
                new PathMatchInterceptor(ignoreAuthenticationInterceptor());

        AutoAuthorizationInterceptorRegister autoAuthorizationInterceptorRegister=
                AutoAuthorizationInterceptorRegister.builder()
                        .storageForRegisterTypes(getStorageForRegisterTypes())
                        .authenticationInterceptor(authenticationInterceptor)
                        .ignoreAuthenticationInterceptor(ignoreAuthenticationInterceptor)
                        .uriParser(getUriParser())
                        .build();

        autoAuthorizationInterceptorRegister.execute();
        ignoreAuthenticationInterceptor
                .excludePatterns("/api/profiles/me", HttpMethod.GET);

        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**");

        registry.addInterceptor(ignoreAuthenticationInterceptor)
                .addPathPatterns("/**");
    }

    private AuthenticationInterceptor authenticationInterceptor(){
        return new AuthenticationInterceptor(oAuthService);
    }

    private IgnoreAuthenticationInterceptor ignoreAuthenticationInterceptor(){
        return new IgnoreAuthenticationInterceptor(oAuthService);
    }

    private List<StorageForRegisterType> getStorageForRegisterTypes(){
        return List.of(
          new AuthenticateStorageForRegisterType(),
          new IgnoreAuthenticateStorageForRegisterType()
        );
    }

    private UriParser getUriParser(){
        return new UrlParser(
                new ControllerScanner(parseClassesNames()),
                new ForGuestScanner(),
                new ForLoginUserScanner()
        );
    }

    private List<String> parseClassesNames(){
        PackageScanner packageScanner = new PackageScanner(PACKAGE);
        return packageScanner.getAllClassNames();
    }
}
