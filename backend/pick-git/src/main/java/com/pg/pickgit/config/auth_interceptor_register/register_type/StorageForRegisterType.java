package com.pg.pickgit.config.auth_interceptor_register.register_type;

import com.pg.pickgit.authentication.presentation.interceptor.PathMatchInterceptor;
import org.springframework.http.HttpMethod;

public interface StorageForRegisterType {

    void appendTo(PathMatchInterceptor include);
    boolean isSatisfiedBy(RegisterType registerType);
    void put(String key, HttpMethod value);
    RegisterType getType();
}
