package dk.bm.fido.auth.controllers;

import dk.bm.fido.auth.dtos.WSO2UserAccountDto;
import dk.bm.fido.auth.services.WSO2Service;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {
    private WSO2UserAccountDto currentUser = null;

    final WSO2Service wso2Service;

    public HomeController(WSO2Service wso2Service) {
        this.wso2Service = wso2Service;
    }

    public String getHome() {
        if (currentUser == null || !wso2Service.checkUserAuthentication(currentUser)) {
            return "templates/login";
        } else {
            return "templates/home";
        }
    }

}
