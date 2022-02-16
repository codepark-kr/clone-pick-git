package com.pg.pickgit.authentication.domain;

import com.pg.pickgit.authentication.application.dto.OAuthProfileResponse;

public interface OAuthClient {

    String getLoginUrl();

    String getAccessToken(String code);

    OAuthProfileResponse getGithubProfile(String githubAccessToken);
}