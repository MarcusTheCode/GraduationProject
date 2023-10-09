package dk.bm.fido.auth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationController {

    @GetMapping("registerFidoDevice")
    public String register () {
        return "redirect:/api/users/v2/me/webauthn/start-registration";
    }
}
