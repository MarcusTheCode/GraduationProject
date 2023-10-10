package dk.bm.fido.auth.services;

import dk.bm.fido.auth.dtos.DeviceDto;
import dk.bm.fido.auth.dtos.WSO2UserAccountDto;
import dk.bm.fido.auth.enums.W2isServerEPType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class WSO2Service {
    @Value("${idc.api.endpoint:https://localhost:9443}") private String idcApiEndpoint;
    @Value("${idc.tenant:carbon.super}") private String idcTenant;

    @Autowired private RestTemplate restTemplate;

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
     * @param authorization The token to query for FIDO devices eg. "Bearer 0fd3..."
     * @return A list of FIDO devices
     */
    public List<DeviceDto> getUserDevices(String authorization) {
        ResponseEntity<List<DeviceDto>> response = execute(
                W2isServerEPType.GET_FIDO_DEVICES,
                authorization,
                null);

        return response.getBody();
    }

    /**
     * Executes an API call based on the values passed into the method
     * @param w2isServerEPType The endpoint configuration that needs to be called
     * @param authorization This is the authorization that will be used as part of the API request
     * @param tags Tags contain different values that the w2isServerEPType uses to replace part of the request
     * @return Returns the response from the server
     */
    public <T> ResponseEntity<T> execute(W2isServerEPType w2isServerEPType, String authorization, Map<String, String> tags) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        headers.set("Content-type", w2isServerEPType.getContentType());
        headers.set("Accept", "*/*");

        if (tags == null) tags = new HashMap<>();
        tags.put("{apiEndpoint}", idcApiEndpoint);
        tags.put("{tenant}", idcTenant);

        String url = w2isServerEPType.getEndpoint();
        for (Map.Entry<String, String> kv : tags.entrySet())
            url = url.replace(kv.getKey(), kv.getValue());

        try {
            return restTemplate.exchange(
                    url,
                    HttpMethod.valueOf(w2isServerEPType.getMethod()),
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<T>() {
                    });
        } catch (HttpStatusCodeException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getStatusCode());
        }
    }

    public boolean checkUserAuthentication(WSO2UserAccountDto wso2UserAccountDto) {
        return userLoggedIn;
    }

    public WSO2UserAccountDto registerFidoDevice(WSO2UserAccountDto wso2UserAccountDto) {
        return wso2UserAccountDto;
    }



}
