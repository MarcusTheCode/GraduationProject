package dk.bm.fido.auth.controllers;

import dk.bm.fido.auth.dtos.WSO2UserAccountDto;
import dk.bm.fido.auth.services.WSO2Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private WSO2UserAccountDto currentUser = null;

    final WSO2Service wso2Service;

    public HomeController(WSO2Service wso2Service) {
        this.wso2Service = wso2Service;
    }

    @GetMapping("home")
    public String Home(Model model) {
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
            return "home";
        }
    }

}
