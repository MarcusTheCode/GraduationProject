package dk.bm.fido.auth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("login")
    public String login (Model model) {
        return "redirect:/oauth2/authorization/wso2";
    }

    @GetMapping("passwordless")
    public String passwordless (Model model) {
        return "passwordless";
    }

}
