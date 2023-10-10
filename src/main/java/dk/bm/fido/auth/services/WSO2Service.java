package dk.bm.fido.auth.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.bm.fido.auth.dtos.DeviceDto;
import dk.bm.fido.auth.dtos.FIDODeto;
import dk.bm.fido.auth.dtos.WSO2UserAccountDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class WSO2Service {
    @Autowired
    private RestTemplate restTemplate;

    final CurrentUserService currentUserService;
    public boolean userLoggedIn = false;

    public WSO2Service(
            CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    /**
     * Retrieves FIDO devices linked to the account with the given token.
     * The token MUST be an authorization_code grant token (Browser login).
     * Otherwise, it will throw a 403.
     * @param token The token to query for FIDO devices
     * @return A list of FIDO devices
     */
    public List<FIDODeto> getUserDevices(String token) {
        // TODO: Move URL out to enum
        final String url = "https://localhost:9443/t/carbon.super/api/users/v2/me/webauthn";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        try {
            ResponseEntity<List<FIDODeto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<List<FIDODeto>>() {});

            return response.getBody();
        } catch (RestClientException e) {
            log.error(e.getMessage());
        }

        return new ArrayList<>();
    }


    public boolean checkUserAuthentication(WSO2UserAccountDto wso2UserAccountDto) {
        return userLoggedIn;
    }

    public WSO2UserAccountDto registerFidoDevice(WSO2UserAccountDto wso2UserAccountDto) {
        return wso2UserAccountDto;
    }



}
