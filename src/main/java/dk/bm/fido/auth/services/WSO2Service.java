package dk.bm.fido.auth.services;

import dk.bm.fido.auth.dtos.DeviceDto;
import dk.bm.fido.auth.dtos.FIDODeto;
import dk.bm.fido.auth.dtos.WSO2UserAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WSO2Service {
    @Value("${idc.tenant:}")
    private String idcTenant;

    @Autowired
    private RestTemplate restTemplate;

    final CurrentUserService currentUserService;
    public WSO2Service(
            CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    public FIDODeto[] getFidoDevices(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        // TODO: Move URL out to enum
        ResponseEntity<FIDODeto[]> response = restTemplate.exchange(
                "https://localhost:9443/t/carbon.super/api/users/v2/me/webauthn",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                FIDODeto[].class);

        return response.getBody();
    }

    public WSO2UserAccountDto authenticateUser(WSO2UserAccountDto wso2UserAccountDto) {

        WSO2UserAccountDto currentUser = new WSO2UserAccountDto();

        currentUserService.setWso2UserAccountDto(currentUser);

        return currentUser;
    }

    public boolean checkUserAuthentication(WSO2UserAccountDto wso2UserAccountDto) {
        return true;
    }

    public List<DeviceDto> getUserDevices(WSO2UserAccountDto wso2UserAccountDto) {
        return null;
    }

    public WSO2UserAccountDto registerUser(WSO2UserAccountDto wso2UserAccountDto) {
        authenticateUser(wso2UserAccountDto);
        return wso2UserAccountDto;
    }



}
