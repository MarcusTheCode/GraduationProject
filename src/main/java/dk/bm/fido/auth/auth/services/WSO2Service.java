package dk.bm.fido.auth.auth.services;

import dk.bm.fido.auth.auth.dtos.DeviceDto;
import dk.bm.fido.auth.auth.enums.W2isServerEPType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class WSO2Service {
    @Value("${idc.api.endpoint:https://localhost:9443}") private String idcApiEndpoint;
    @Value("${idc.tenant:carbon.super}") private String idcTenant;

    private final RestTemplate restTemplate;

    public WSO2Service(@Qualifier("sslRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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
                null,
                null);

        return response.getBody();
    }

    public String startUserDeviceRegistration(String authorization) {
        ResponseEntity<String> response = execute(
                W2isServerEPType.START_FIDO_REGISTRATION,
                authorization,
                "appId=" + idcApiEndpoint,
                null);

        return response.getBody();
    }

    public String finishUserDeviceRegistration(String authorization, String challengeResponse) {
        ResponseEntity<String> response = execute(
                W2isServerEPType.FINISH_FIDO_REGISTRATION,
                authorization,
                challengeResponse,
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
    public <R, T> ResponseEntity<R> execute(W2isServerEPType w2isServerEPType, String authorization, T body, Map<String, String> tags) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        headers.set("Content-type", w2isServerEPType.getContentType());
        headers.set("Accept", "*/*");

        if (tags == null) tags = new HashMap<>();
        tags.put("{apiEndpoint}", idcApiEndpoint);
        tags.put("{tenant}", idcTenant);

        String url = replaceChars(w2isServerEPType.getEndpoint(), tags);

        try {
            return restTemplate.exchange(
                    url,
                    HttpMethod.valueOf(w2isServerEPType.getMethod()),
                    new HttpEntity<>(body, headers),
                    new ParameterizedTypeReference<R>() {});
        } catch (HttpStatusCodeException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getStatusCode());
        }
    }

    private String replaceChars(String value, Map<String, String> tags) {
        String replacedValue = value;
        for (Map.Entry<String, String> kv : tags.entrySet())
            replacedValue = replacedValue.replace(kv.getKey(), kv.getValue());

        return replacedValue;
    }
}
