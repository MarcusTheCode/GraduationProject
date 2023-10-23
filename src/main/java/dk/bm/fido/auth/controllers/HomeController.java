package dk.bm.fido.auth.controllers;

import dk.bm.fido.auth.external.services.WSO2Service;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final WSO2Service wso2Service;

    public HomeController(WSO2Service wso2Service) {
        this.wso2Service = wso2Service;
    }

    @GetMapping("home")
    public String Home(@RegisteredOAuth2AuthorizedClient("wso2") OAuth2AuthorizedClient authorizedClient, Model model) {
        OAuth2AccessToken token = authorizedClient.getAccessToken();
        String bearerToken = token.getTokenType().getValue() + " " + token.getTokenValue();
        model.addAttribute("devices", wso2Service.getUserDevices(bearerToken));
        model.addAttribute("user", null);
        return "home";
    }

    @GetMapping("")
    public String root(/*@RegisteredOAuth2AuthorizedClient("wso2") OAuth2AuthorizedClient authorizedClient*/) {
            return "redirect:home";
    }

}
