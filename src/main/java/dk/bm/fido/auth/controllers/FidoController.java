package dk.bm.fido.auth.controllers;

import dk.bm.fido.auth.external.services.WSO2Service;
import dk.bm.fido.auth.services.FrontEndService;
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

    /**
     * Function for retrieving the users FIDO2 devices and returning them with the html page
     * @param model container for attributes between backend and frontend
     * @param authentication the http authentication header giving the identification of the current user
     * @param authorizedClient the authorized client pulled from the authorization header; it is pulled out separately for simplicity
     * @return the FIDO2 devices list html, with the FIDO2 devices added to the model
     */
    @GetMapping("fidoDevices")
    public String fidoDevices(
            Model model,
            Authentication authentication,
            @RegisteredOAuth2AuthorizedClient("wso2") OAuth2AuthorizedClient authorizedClient
    ) {
        FrontEndService.setAuthenticated(authentication, model);
        model.addAttribute(
                "devices" ,
                wso2Service.getUserDevices(FrontEndService.getBearerToken(authorizedClient))
        );
        return "fidoDevices";
    }

    /**
     * Function used to delete a FIDO2 device/login from the users account
     * @param credential the id of the FIDO2 credential to be deleted
     * @param authorizedClient the authorized client pulled from the authorization header
     * @return redirect to the fidoDevices html page
     */
    @PostMapping(value="fidoDevices/deleteDevice/{credential}") //Delete not supported by forms
    public String deleteFidoDevice(
            @PathVariable String credential,
            @RegisteredOAuth2AuthorizedClient("wso2") OAuth2AuthorizedClient authorizedClient
    ) {
        wso2Service.deleteDeviceCredential(FrontEndService.getBearerToken(authorizedClient), credential);
        return "redirect:/fidoDevices";
    }

    /**
     * Mapping for updating the name of the FIDO2 device
     * @param credential the id of the FIDO2 credential to be updated
     * @param newName the new name of the device
     * @param authorizedClient the authorized client pulled from the authorization header
     * @return redirect to the fidoDevices html page
     */
    @PostMapping("fidoDevices/editDevice/{credential}/{newName}")
    public String editFidoDevice(
            @PathVariable String credential,
            @PathVariable String newName,
            @RegisteredOAuth2AuthorizedClient("wso2") OAuth2AuthorizedClient authorizedClient
    ) {
        wso2Service.editDeviceName(FrontEndService.getBearerToken(authorizedClient), credential, newName);
        return "redirect:/fidoDevices";
    }
}
