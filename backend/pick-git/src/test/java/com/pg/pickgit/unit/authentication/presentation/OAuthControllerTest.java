package com.pg.pickgit.unit.authentication.presentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static javax.management.openmbean.SimpleType.STRING;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OAuthControllerTest extends ControllerTest {

    @DisplayName("Return Authorization URL of Github when user requested signin")
    @Test
    void authorizationGithubUrl_InvalidAccount_GithubUrl(){
        // given
        String githubAuthorizationGithubUrl = "http://github/authorization.url";
        given(oAuthService.getGithubAuthorizationUrl()).willReturn(githubAuthorizationGithubUrl);

        // when, then
        ResultActions perform = mockMvc
                .perform(get("/api/authorization/github")
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("url").value(githubAuthorizationGithubUrl));

        perform.andDo(document("authorization - githubLogin",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                        fieldWithPath("url").type(STRING).description("Github login url")
                )
        ));
    }

    @DisplayName("Generate and return Token after authorized Github signin")
    @Test
    void afterAuthorizeGithubLogin_ValidAccount_JWTToken() throws Exception {
        // given
        String githubAuthorizationCode = "random";
        given(oAuthService.createToken(githubAuthorizationCode)).willReturn(new TokenDto("jwt token", "username"));

        // when, then
        ResultActions perform = mockMvc
                .perform(get("/api/afterlogin?code=" + githubAuthorizationCode))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("token").value("jwt token"));

        perform.andDo(document("authorization - afterlogin",
                getDocumentRequest(),
                getDocuemntResponse(),
                responseFields(
                        fieldWithPath("token").type(STRING).description("JWT token"),
                        fieldWithPath("username").type(STRING).description("User name")
                )
        ));
    }
}
