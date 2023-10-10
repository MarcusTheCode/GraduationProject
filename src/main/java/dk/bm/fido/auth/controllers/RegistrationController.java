package dk.bm.fido.auth.controllers;

import dk.bm.fido.auth.external.services.WSO2Service;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    private final WSO2Service wso2Service;

    public RegistrationController(WSO2Service wso2Service) {
        this.wso2Service = wso2Service;
    }

    @PostMapping(value = "/register/start", produces = MediaType.APPLICATION_JSON_VALUE)
    public String registerStart(@RegisteredOAuth2AuthorizedClient("wso2") OAuth2AuthorizedClient authorizedClient) {
        OAuth2AccessToken token = authorizedClient.getAccessToken();
        String bearerToken = token.getTokenType().getValue() + " " + token.getTokenValue();

        return wso2Service.startUserDeviceRegistration(bearerToken);
    }

    @PostMapping(value = "/register/finish", produces = MediaType.APPLICATION_JSON_VALUE)
    public String registerFinish(@RegisteredOAuth2AuthorizedClient("wso2") OAuth2AuthorizedClient authorizedClient, @RequestBody String challengeResponse) {
        OAuth2AccessToken token = authorizedClient.getAccessToken();
        String bearerToken = token.getTokenType().getValue() + " " + token.getTokenValue();

        return wso2Service.finishUserDeviceRegistration(bearerToken, challengeResponse);
    }
}
