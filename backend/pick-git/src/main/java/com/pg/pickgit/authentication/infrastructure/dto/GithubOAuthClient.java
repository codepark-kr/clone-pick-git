package com.pg.pickgit.authentication.infrastructure.dto;

import com.pg.pickgit.authentication.application.dto.OAuthProfileResponse;
import com.pg.pickgit.authentication.domain.OAuthClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Profile("!test")
@Component
public class GithubOAuthClient implements OAuthClient {

    private static final String OAUTH_LOGIN_URL_SUFFIX =
            "/login/oauth/authorize?client_id=%s&redirect_uri=%s&scope=user:follow";
    private static final String ACCESS_TOKEN_URL_SUFFIX =
            "/login/oauth/access_token";

    private final String clientId;
    private final String clientSecret;
    private final String redirectUrl;
    private final String oauthBaseUrl;
    private final String apiBaseUrl;

    public GithubOAuthClient(
            @Value("${security.github.client.id}") String clientId,
            @Value("${security.github.client.secret}") String clientSecret,
            @Value("${security.github.url.redirect}") String redirectUrl,
            @Value("${security.github.url.oauth}") String oauthBaseUrl,
            @Value("${security.github.url.api}") String apiBaseUrl
    ){
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
        this.oauthBaseUrl = oauthBaseUrl;
        this.apiBaseUrl = apiBaseUrl;
    }

    @Override
    public String getLoginUrl() {
        String oauthLoginUrlFormat = oauthBaseUrl + OAUTH_LOGIN_URL_SUFFIX;
        return String.format(oauthLoginUrlFormat, clientId, redirectUrl);
    }

    @Override
    public String getAccessToken(String code) {
        OAuthAccessTokenRequest githubAccessTokenRequest =
                new OAuthAccessTokenRequest(clientId, clientSecret, code);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<OAuthAccessTokenRequest> httpEntity =
                new HttpEntity<>(githubAccessTokenRequest, headers);
        String accessTokenUrl = oauthBaseUrl + ACCESS_TOKEN_URL_SUFFIX;

        OAuth2AccessTokenResponse response = new RestTemplate()
                .exchange(accessTokenUrl, HttpMethod.POST, httpEntity, OAuth2AccessTokenResponse.class)
                .getBody();
        if(Objects.isNull(response)){ throw new PlatformHttpErrorException(); }
        return response.getAccessToken();
    }

    @Override
    public OAuthProfileResponse getGithubProfile(String githubAccessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + githubAccessToken);
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        String url = apiBaseUrl + "/user";
        return new RestTemplate()
                .exchange(url, HttpMethod.GET, httpEntity, OAuthProfileResponse.class)
                .getBody();
    }
}
