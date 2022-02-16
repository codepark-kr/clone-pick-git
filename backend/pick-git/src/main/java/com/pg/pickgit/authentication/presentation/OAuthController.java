package com.pg.pickgit.authentication.presentation;

import com.pg.pickgit.authentication.application.OAuthService;
import com.pg.pickgit.authentication.application.dto.TokenDto;
import com.pg.pickgit.authentication.presentation.dto.OAuthLoginUrlResponse;
import com.pg.pickgit.authentication.presentation.dto.OAuthTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(value = "*")
@RequestMapping("/api")
@RestController
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("/authorization/github")
    public ResponseEntity<OAuthLoginUrlResponse> githubAuthorizationUrl(){
        return ResponseEntity.ok().body(new OAuthLoginUrlResponse(oAuthService.getGithubAuthorizationUrl()));
    }

    @GetMapping("/afterlogin")
    public ResponseEntity<OAuthTokenResponse> afterAuthorizeGithubLogin(@RequestParam String code){
        TokenDto tokenDto = oAuthService.createToken(code);
        return ResponseEntity.ok().body(new OAuthTokenResponse(tokenDto.getToken(), tokenDto.getUsername()));
    }
}
