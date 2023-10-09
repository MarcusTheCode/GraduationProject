package dk.bm.fido.auth.controllers;

import dk.bm.fido.auth.services.WSO2Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {


    final WSO2Service wso2Service;

    public LoginController(WSO2Service wso2Service) {
        this.wso2Service = wso2Service;
    }

    @GetMapping("login")
    public String login () {
        wso2Service.userLoggedIn = true;
        return "redirect:/oauth2/authorization/wso2";
    }

    @RequestMapping("logoutUser")
    public String logout (Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        wso2Service.userLoggedIn = false;
        logoutHandler.logout(request, response, authentication);
        return "redirect:https://localhost:9443/oidc/logout&redirect_uri=http://localhost:8080/login";
    }

    @GetMapping("passwordless")
    public String passwordless (Model model) {
        return "passwordless";
    }

}
