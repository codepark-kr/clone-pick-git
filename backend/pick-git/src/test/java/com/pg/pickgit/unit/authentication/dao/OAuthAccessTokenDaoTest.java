package com.pg.pickgit.unit.authentication.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OAuthAccessTokenDaoTest {

    private static final String TOKEN = "jwt token";
    private static final String OAUTH_ACCESS_TOKEN = "oauth access token";

    private OAuthAccessTokenDao oAuthAccessTokenDao;

    @BeforeEach
    void setUp() {
        //given
        oAuthAccessTokenDao = new CollectionOAuthAccessTokenDao();
    }

    @DisplayName("Save and load newly generated JWT token and OAuth Access Token to map")
    @Test
    void insertAndFind_NonDuplicated_Save() {
        // when
        oAuthAccessTokenDao.insert(TOKEN, OAUTH_ACCESS_TOKEN);

        // then
        assertThat(oAuthAccessTokenDao.findByKeyToken(TOKEN).get()).isEqualTo(OAUTH_ACCESS_TOKEN);
    }

    @DisplayName("Success to update for add new OAuth Access Token if JWT token already exists")
    @Test
    void insertAndFind_Duplicated_Save() {
        // when
        oAuthAccessTokenDao.insert(TOKEN, OAUTH_ACCESS_TOKEN);
        oAuthAccessTokenDao.insert(TOKEN, "duplicated");

        // then
        assertThat(oAuthAccessTokenDao.findByKeyToken(TOKEN).get()).isEqualTo("duplicated");
    }
}