package dk.bm.fido.auth.services;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

public class FrontEndServiceSupreme {
    public static String getBearerToken(OAuth2AuthorizedClient authorizedClient) {
        OAuth2AccessToken token = authorizedClient.getAccessToken();
        return token.getTokenType().getValue() + " " + token.getTokenValue();
    }
}
