package dk.bm.fido.auth.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.ui.Model;

public class FrontEndHelper {
    public static void setAuthenticated(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("authenticated", true);
            model.addAttribute("username", authentication.getName());
        } else {
            model.addAttribute("authenticated", false);
        }
    }

    public static String getBearerToken(OAuth2AuthorizedClient authorizedClient) {
        OAuth2AccessToken token = authorizedClient.getAccessToken();
        return token.getTokenType().getValue() + " " + token.getTokenValue();
    }
}
