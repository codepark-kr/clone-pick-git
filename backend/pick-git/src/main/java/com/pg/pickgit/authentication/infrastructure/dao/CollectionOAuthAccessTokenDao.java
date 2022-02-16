package com.pg.pickgit.authentication.infrastructure.dao;

import com.pg.pickgit.authentication.domain.OAuthAccessTokenDao;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionOAuthAccessTokenDao implements OAuthAccessTokenDao {

    private final ConcurrentHashMap<String, String> tokenDB;

    public CollectionOAuthAccessTokenDao(){ this(new ConcurrentHashMap<>()); }

    private CollectionOAuthAccessTokenDao(ConcurrentHashMap<String, String> tokenDB){ this.tokenDB = tokenDB; }


    @Override
    public void insert(String token, String oauthAccessToken) { tokenDB.put(token, oauthAccessToken); }

    @Override
    public Optional<String> findByKeyToken(String token) { return Optional.ofNullable(tokenDB.get(token)); }
}
