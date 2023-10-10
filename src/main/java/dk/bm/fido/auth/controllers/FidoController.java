package dk.bm.fido.auth.controllers;

import dk.bm.fido.auth.external.services.WSO2Service;
import dk.bm.fido.auth.services.FrontEndServiceSupreme;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FidoController {

    final WSO2Service wso2Service;

    public FidoController(WSO2Service wso2Service) {
        this.wso2Service = wso2Service;
    }

    @GetMapping("fidoDevices")
    public String fidoDevices(
            Model model,
            @RegisteredOAuth2AuthorizedClient("wso2") OAuth2AuthorizedClient authorizedClient
    ) {
        model.addAttribute(
                "devices" ,
                wso2Service.getUserDevices(FrontEndServiceSupreme.getBearerToken(authorizedClient))
        );
        return "fidoDevices";
    }

    @GetMapping("fidoDevices/delete")
    public void deleteFidoDevice() {
        return;
    }
    @GetMapping("fidoDevices/edit")
    public void editFidoDevice() {
        return;
    }
}
