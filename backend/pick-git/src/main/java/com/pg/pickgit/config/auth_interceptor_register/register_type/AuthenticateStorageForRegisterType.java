package com.pg.pickgit.config.auth_interceptor_register.register_type;

import com.pg.pickgit.authentication.presentation.interceptor.PathMatchInterceptor;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthenticateStorageForRegisterType implements StorageForRegisterType{

    private static final RegisterType TYPE = RegisterType.AUTHENTICATE;

    private final Map<String, List<HttpMethod>> cache = new HashMap<>();

    @Override
    public void appendTo(PathMatchInterceptor include) { cache.forEach(include::addPathPatterns); }

    @Override
    public boolean isSatisfiedBy(RegisterType registerType) { return registerType == TYPE;  }

    @Override
    public void put(String key, HttpMethod value){
        List<HttpMethod> httpMethods = cache.computeIfAbsent(key, k -> new ArrayList<>());
        httpMethods.add(value);
    }

    @Override
    public RegisterType getType() { return TYPE; }
}