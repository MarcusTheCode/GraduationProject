package dk.bm.fido.auth.controllers;

import dk.bm.fido.auth.auth.services.WSO2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RegistrationController {

    @Autowired WSO2Service wso2Service;

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
