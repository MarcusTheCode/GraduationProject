package dk.bm.fido.auth.external.services;

import dk.bm.fido.auth.external.dtos.DeviceDto;
import dk.bm.fido.auth.external.enums.W2isServerEPType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
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
        return execute(
                W2isServerEPType.GET_FIDO_DEVICES,
                authorization,
                new ParameterizedTypeReference<List<DeviceDto>>() {},
                null).getBody();
    }

    public String startUserDeviceRegistration(String authorization) {
        return execute(
                W2isServerEPType.START_FIDO_REGISTRATION,
                authorization,
                new ParameterizedTypeReference<String>() {},
                "appId=" + "http://localhost:8080" /*idcApiEndpoint*/).getBody();
    }

    public String finishUserDeviceRegistration(String authorization, String challengeResponse) {
        return execute(
                W2isServerEPType.FINISH_FIDO_REGISTRATION,
                authorization,
                new ParameterizedTypeReference<String>() {},
                challengeResponse).getBody();
    }
    public String deleteDeviceCredential(String authorization, String credential) {
             return execute(
                W2isServerEPType.DELETE_FIDO_DEVICE,
                authorization,
                new ParameterizedTypeReference<String>() {},
                null, new HashMap<>(){{put("{credential}", credential);}}).getBody();
    }

    public <R, T> ResponseEntity<R> execute(
            W2isServerEPType w2isServerEPType,
            String authorization,
            ParameterizedTypeReference<R> clazz,
            T body
    ) {
        return execute(w2isServerEPType, authorization, clazz, body, null);
    }

    /**
     * Executes an API call based on the values passed into the method
     * @param w2isServerEPType The endpoint configuration that needs to be called
     * @param authorization This is the authorization that will be used as part of the API request
     * @param tags Tags contain different values that the w2isServerEPType uses to replace part of the request
     * @return Returns the response from the server
     */
    public <R, T> ResponseEntity<R> execute(
            W2isServerEPType w2isServerEPType,
            String authorization,
            ParameterizedTypeReference<R> clazz,
            T body,
            Map<String, String> tags
    ) {
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
                    clazz);
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
