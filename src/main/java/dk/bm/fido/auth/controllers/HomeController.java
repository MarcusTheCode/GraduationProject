package dk.bm.fido.auth.controllers;

import dk.bm.fido.auth.dtos.WSO2UserAccountDto;
import dk.bm.fido.auth.services.WSO2Service;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class HomeController {
    private WSO2UserAccountDto currentUser = null;

    final WSO2Service wso2Service;

    public HomeController(WSO2Service wso2Service) {
        this.wso2Service = wso2Service;
    }

    @GetMapping("home")
    public String Home(@RegisteredOAuth2AuthorizedClient("wso2") OAuth2AuthorizedClient authorizedClient, Model model) {
        //String t = wso2Service.getFidoDevices(authorizedClient);
        var token = authorizedClient.getAccessToken();
        var r = wso2Service.getFidoDevices(token.getTokenType().getValue() + " " + token.getTokenValue());
        model.addAttribute("devices", wso2Service.getUserDevices(currentUser));
        model.addAttribute("user", currentUser);
        return "home";
    }

    @GetMapping("")
    public String root(Model model) {
        if (!wso2Service.checkUserAuthentication(currentUser)) {
            return "redirect:login";
        } else {
            model.addAttribute("devices", wso2Service.getUserDevices(currentUser));
            model.addAttribute("user", currentUser);
            return "redirect:home";
        }
    }

}
