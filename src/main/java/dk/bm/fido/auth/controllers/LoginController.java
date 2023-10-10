package dk.bm.fido.auth.controllers;

import dk.bm.fido.auth.auth.services.WSO2Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    private final WSO2Service wso2Service;
    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    public LoginController(WSO2Service wso2Service) {
        this.wso2Service = wso2Service;
    }

    @GetMapping("login")
    public String login () {
        return "redirect:/oauth2/authorization/wso2";
    }

    @RequestMapping("logoutUser")
    public String logout (Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        logoutHandler.logout(request, response, authentication);
        return "redirect:https://localhost:9443/oidc/logout?post_logout_redirect_uri=http://localhost:8080/login";
    }

}
