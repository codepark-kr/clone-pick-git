package com.pg.pickgit.unit.user.presentation;

import com.pg.pickgit.authentication.domain.user.LoginUser;
import com.pg.pickgit.unit.ControllerTest;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.pg.pickgit.docs.ApiDocumentUtils.getDocumentRequest;
import static com.pg.pickgit.docs.ApiDocumentUtils.getDocumentResponse;
import static java.sql.Types.NULL;
import static javax.management.openmbean.SimpleType.STRING;
import static javax.swing.text.html.parser.DTDConstants.NUMBER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;


public class UserControllerTest extends ControllerTest {

    @DisplayName("when logon")
    @Nested
    class Describe_UnderLoginCondition {

        @DisplayName("The user can look up the their profile")
        @Test
        void getAuthenticatedUserProfile_LoginUser_Success() throws Exception {

            // given
            UserProfileResponseDto responseDto = UserFactory.mockLoginUserProfileResponseDto();

            given(oAuthService.validateToken("testToken"))
                    .willReturn(true);
            given(oAuthService.findRequestUserByToken("testToken"))
                    .willReturn(new LoginUser("loginUser", "testToken"));
            given(userService.getMyUserProfile(any(AuthUserForUserReqeustDto.class)))
                    .willReturn(responseDto);

            // when
            ResultActions perform = mockMvc.perform(get("/api/profiles/me")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer testToken")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.ALL));

            // then
            String body = perform
                    .addExpect(status().isOK())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            assertThat(body).isEqualTo(objectMapper.writeValueAsString(responseDto));

            verify(oAuthService, times(1))
                    .validateToken("testToken");
            verify(oAuthService, times(1))
                    .findRequestUserByToken("testToken");
            verify(userService, times(1))
                    .getMyUserProfile(any(AuthUserForUserRequestDto.class));

            perform.andDo(document("profile-me",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestHeaders(
                            headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer token")
                    ),
                    responseFields(
                            fieldWithPath("name").type(STRING).description("user name"),
                            fieldWithPath("imageUrl").type(STRING).description("url of profile image"),
                            fieldWithPath("description").type(STRING).description("bio"),
                            fieldWithPath("followerCount").type(NUMBER).description("follower count"),
                            fieldWithPath("followingCount").type(NUMBER).description("following count"),
                            fieldWithPath("postCount").type(NUMBER).description("post count"),
                            fieldWithPath("githubUrl").type(STRING).description("github url"),
                            fieldWithPath("company").type(STRING).description("company"),
                            fieldWithPath("location").type(STRING).description("location"),
                            fieldWithPath("website").type(STRING).description("website"),
                            fieldWithPath("twitter").type(STRING).description("twitter"),
                            fieldWithPath("following").type(NULL).description("is following")
                    )
            ));
        }
    }
}
