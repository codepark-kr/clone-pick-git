package com.pg.pickgit.unit;

import com.pg.pickgit.authentication.application.OAuthService;
import com.pg.pickgit.authentication.presentation.OAuthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WebMvcTest({
        OAuthController.class
})
@ActiveProfiles("test")
public abstract class ControllerTest {

    @MockBean
    protected OAuthService oAuthService;

    @Autowired
    protected MockMvc mockMvc;
}
