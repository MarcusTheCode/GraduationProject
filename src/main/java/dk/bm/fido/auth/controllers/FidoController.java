package dk.bm.fido.auth.controllers;

import dk.bm.fido.auth.external.dtos.DeviceDto;
import dk.bm.fido.auth.external.services.WSO2Service;
import dk.bm.fido.auth.services.FrontEndServiceSupreme;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FidoController {

    final WSO2Service wso2Service;

    public FidoController(WSO2Service wso2Service) {
        this.wso2Service = wso2Service;
    }

    @GetMapping("fidoDevices")
    public String fidoDevices(
            Model model,
            Authentication authentication,
            @RegisteredOAuth2AuthorizedClient("wso2") OAuth2AuthorizedClient authorizedClient
    ) {
        FrontEndServiceSupreme.setAuthenticated(authentication, model);
        model.addAttribute(
                "devices" ,
                wso2Service.getUserDevices(FrontEndServiceSupreme.getBearerToken(authorizedClient))
        );
        return "fidoDevices";
    }

    @PostMapping(value="fidoDevices/deleteDevice/{credential}") //Delete not supported by forms
    public String deleteFidoDevice(
            @PathVariable String credential,
            @RegisteredOAuth2AuthorizedClient("wso2") OAuth2AuthorizedClient authorizedClient
    ) {
        wso2Service.deleteDeviceCredential(FrontEndServiceSupreme.getBearerToken(authorizedClient), credential);
        return "redirect:/fidoDevices";
    }
    @PostMapping("fidoDevices/editDevice/{credential}/{newName}")
    public String editFidoDevice(
            @PathVariable String credential,
            @PathVariable String newName,
            @RegisteredOAuth2AuthorizedClient("wso2") OAuth2AuthorizedClient authorizedClient
    ) {
        wso2Service.editDeviceName(FrontEndServiceSupreme.getBearerToken(authorizedClient), credential, newName);
        return "redirect:/fidoDevices";
    }
}
