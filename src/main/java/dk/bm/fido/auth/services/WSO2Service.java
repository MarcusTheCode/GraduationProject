package dk.bm.fido.auth.services;

import dk.bm.fido.auth.dtos.DeviceDto;
import dk.bm.fido.auth.dtos.WSO2UserAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Service
public class WSO2Service {
    @Value("${idc.tenant:}")
    private String idcTenant;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WebClient webClient;

    final CurrentUserService currentUserService;
    public WSO2Service(
            CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    public String getTokenFromClientCredentials(String clientId, String clientSecret) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String encoded = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", encoded);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://localhost:9443/oauth2/token",
                HttpMethod.POST,
                new HttpEntity<>("grant_type=client_credentials", headers),
                String.class);

        return response.getBody();
    }

    public String getFidoDevices(OAuth2AuthorizedClient authorizedClient) {
        Mono<String> r = webClient.get()
                .uri("https://localhost:9443/t/carbon.super/api/users/v2/me/webauthn")
                .retrieve()
                .bodyToMono(String.class);

        return r.block();
    }

    public String getFidoDevices(String token, String commonAuth) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.set("Cookie", "commonAuthId=" + commonAuth);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://localhost:9443/t/carbon.super/api/users/v2/me/webauthn?username=john",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class);

        return response.getBody();
    }

//    private String getBasicAuth (String user, String pwd) {
//        String userpass = user + "@" + idcTenant + ":" + pwd;
//        return  "Basic " + Base64.getEncoder().encodeToString(userpass.getBytes(StandardCharsets.UTF_8));
//    }
//
//    public String getClientSecret () {
//        String clientId = HttpServletHelper.getClientId();
//
//        if (clientId == null)
//            clientId = applicationService.getAllActiveClientIdsByApplicationType(applicationService.getApplicationTypeBasedOnAPImatch(idcApiMatch)).get(0);
//
//        String clientSecret = applicationService.getClientSecretFromClientId(clientId);
//
//        return encodeClientSecretForBasicAuth(clientId,clientSecret);
//    }

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
