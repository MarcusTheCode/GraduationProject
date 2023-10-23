package dk.bm.fido.auth.controllers;

import dk.bm.fido.auth.services.FrontEndServiceSupreme;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        FrontEndServiceSupreme.setAuthenticated(authentication, model);

        return "home";
    }

}
