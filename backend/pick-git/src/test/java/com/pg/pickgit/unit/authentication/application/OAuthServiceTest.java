package com.pg.pickgit.unit.authentication.application;


import com.pg.pickgit.authentication.application.JwtTokenProvider;
import com.pg.pickgit.authentication.application.OAuthService;
import com.pg.pickgit.authentication.application.dto.OAuthProfileResponse;
import com.pg.pickgit.authentication.application.dto.TokenDto;
import com.pg.pickgit.authentication.domain.OAuthClient;
import com.pg.pickgit.authentication.domain.user.AppUser;
import com.pg.pickgit.authentication.domain.user.GuestUser;
import com.pg.pickgit.authentication.domain.user.LoginUser;
import com.pg.pickgit.authentication.infrastructure.dao.CollectionOAuthAccessTokenDao;
import com.pg.pickgit.exception.authentication.InvalidTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class OAuthServiceTest {

    private static final String GITHUB_CODE = "oauth authorization code";
    private static final String OAUTH_ACCESS_TOKEN = "oauth.access.token";
    private static final String JWT_TOKEN = "jwt token";

    @Mock
    private OAuthClient oAuthClient;

    @Mock
    private UseRepository userRepository;

    @Mock
    private UserSearchEngine userSearchEngine;

    @Mock
    private CollectionOAuthAccessTokenDao oAuthAccessTokenDao;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private OAuthService oAuthService;

    @DisplayName("Success to return to login URL of Github")
    @Test
    void getGithubAuthorizationUrl_Anonymous_ReturnGithubAuthorizationUrl(){
        // given
        String url = "https://github.com/login..";
        // mock
        given(oAuthClient.getLoginUrl()).willReturn(url);
        // then
        assertThat(oAuthService.getGithubAuthorizationUrl()).isEqualTo(url);
    }

    @DisplayName("Insert Github profile to Database when user Registered(at first login)")
    @Test
    void createToken_Signup_SaveUserProfile(){
        // given
        OAuthProfileResponse githubProfileResponse = OAuthProfileResponse.builder()
                .name("name")
                .image("img.png")
                .description("bio")
                .githubUrl("www.github.com")
                .company("company")
                .location("location")
                .website("website")
                .twitter("twitter")
                .build();

        User user = new User(
                githubProfileResponse.toBasicProfile(),
                githubProfileResponse.toGithubProfile()
        );

        // mock
        given(oAuthClient.getAccessToken(anyString())).willReturn(OAUTH_ACCESS_TOKEN);
        given(oAuthClient.getGithubProfile(anyString())).willReturn(githubProfileResponse);
        given(userRepository.findByBasicProfile_Name(githubProfileResponse.getName())).willReturn(Optional.empty());
        given(jwtTokenProvider.createToken(githubProfileResponse.getName())).willReturn(JWT_TOKEN);

        // when
        TokenDto token = oAuthService.createToken(GITHUB_CODE);

        // then
        assertThat(token.getToken()).isEqualTo(JWT_TOKEN);
        verify(userRepository, times(1)).findByBasicProfile_Name(anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verify(jwtTokenProvider, times(1)).createToken(anyString());

        verify(userRepository, times(1)).findByBasicProfile_Name(githubProfileResponse.getName());
        verify(userRepository, times(1)).save(user);
        verify(userSearchEngine, times(1)).save(any());
        verify(jwtTokenProvider, times(1)).createToken(githubProfileResponse.getName());
        verify(oAuthAccessTokenDao, times(1)).insert(JWT_TOKEN, OAUTH_ACCESS_TOKEN);
    }

    @DisplayName("Update exist Github profile in database when user logon(when it's not the first time)")
    @Test
    void createToken_Signin_updateUserProfile(){
        // given
        OAuthProfileResponse githubProfileResponse = OAuthProfileResponse.builder()
                .name("name")
                .image("image.png")
                .description("bio")
                .githubUrl("www.github.com")
                .company("company")
                .location("location")
                .website("website")
                .twitter("twitter")
                .build();

        User user = new User(
                githubProfileResponse.toBasicProfile(),
                githubProfileResponse.toGithubProfile()
        );

        // mock
        given(oAuthClient.getAccessToken(anyString())).willReturn(OAUTH_ACCESS_TOKEN);
        given(oAuthClient.getGithubProfile(anyString())).willReturn(githubProfileResponse);
        given(userRepository.findByBasicProfile_Name(githubProfileResponse.getName())).willReturn(Optional.of(user));
        given(jwtTokenProvider.createToken(githubProfileResponse.getName())).willReturn(JWT_TOKEN);

        // when
        TokenDto token = oAuthService.createToken(GITHUB_CODE);

        // then
        assertThat(token.getToken()).isEqualTo(JWT_TOKEN);
        verify(userRepository, times(1)).findByBasicProfile_Name(anyString());
        verify(userRepository, never()).save(any(User.class));
        verify(jwtTokenProvider, times(1)).createToken(anyString());
        verify(oAuthAccessTokenDao, times(1)).insert(anyString(), anyString());

        verify(userRepository, times(1)).findBybasicProfile_Name(githubProfileResponse.getName());
        verify(userRepository, never()).save(user);
        verify(jwtTokenProvider, times(1)).createToken(githubProfileResponse.getName());
        verify(oAuthAccessTokenDao, times(1)).insert(JWT_TOKEN, OAUTH_ACCESS_TOKEN);
    }

    @DisplayName("Get information of LoginUser from AccessTokenDB via JWT token")
    @Test
    void findRequestUserByToken_ValidToken_ReturnAppUser(){
        // given
        String username = "pick-git";

        // mock
        given(jwtTokenProvider.getPayloadBykey(JWT_TOKEN, "username")).willReturn(username);
        given(oAuthAccessTokenDao.findByKeyToken(JWT_TOKEN)).willReturn(Optional.ofNullable(OAUTH_ACCESS_TOKEN));

        // when
        AppUser appUser = oAuthService.findRequestUserByToken(JWT_TOKEN);

        // then
        assertThat(appUser).isInstanceOf(LoginUser.class);
        assertThat(appUser.getUsername()).isEqualTo(username);
        assertThat(appUser.getAccessToken()).isEqualTo(OAUTH_ACCESS_TOKEN);
    }

    @DisplayName("Occur exception if the JWT token does not exists in AccessTokenDB")
    @Test
    void findRequestUserByToken_NotFoundToken_ThrowException(){
        //given
        String notSavedToken = "not_saved_jwt_token";
        String username = "pick-git";

        // mock
        given(jwtTokenProvider.getPayloadByKey(notSavedToken, "username")).willReturn(username);
        given(oAuthAccessTokenDao.findByKeyToken(notSavedToken)).willReturn(Optional.empty());

        // then
        assertThatThrownBy(()-> oAuthService.findRequestUserByToken(notSavedToken))
                .isInstanceOf(InvalidTokenException.class)
                .hasFieldOrPropertyWithValue("errorCode", "A0001")
                .hasFieldOrPropertyWithValue("httpStatus", HttpStatus.UNAUTHORIZED)
                .hasMessage("Token Authorization has been failed.");
    }

    @DisplayName("Return GuestUser if the JWT token is empty")
    @Test
    void findRequestUserByToken_EmptyToken_ReturnGuest(){
        // when
        AppUser appUser = oAuthService.findRequestUserByToken(null);

        // then
        assertThat(appUser).isInstanceOf(GuestUser.class);
    }
}
