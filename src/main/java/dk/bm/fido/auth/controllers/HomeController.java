package dk.bm.fido.auth.controllers;

import dk.bm.fido.auth.services.FrontEndService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    /**
     * Set authentication when accessing the root page
     * @param authentication the authorized client pulled from the authorization header
     * @param model container for attributes between backend and frontend
     * @return homepage html with the model attributes defined by the setAuthenticated method
     */
    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        FrontEndService.setAuthenticated(authentication, model);
        return "home";
    }

}
