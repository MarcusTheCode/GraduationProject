package dk.bm.fido.auth.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.bm.fido.auth.dtos.DeviceDto;
import dk.bm.fido.auth.dtos.WSO2UserAccountDto;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class WSO2Service {

    final CurrentUserService currentUserService;
    public boolean userLoggedIn = false;

    public WSO2Service(
            CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    public boolean checkUserAuthentication(WSO2UserAccountDto wso2UserAccountDto) {
        return userLoggedIn;
    }

    public List<DeviceDto> getUserDevices(OAuth2AuthorizedClient oAuth2AuthorizedClient) {
        String uri = "http://localhost:9443/";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        ObjectMapper mapper = new ObjectMapper();

        try{
            List<DeviceDto> devices = mapper.readValue(result, new TypeReference<List<DeviceDto>>() {});
            return devices;
        }catch (JsonProcessingException e) {
            System.out.println(e);
            return null;
        }
    }

    public WSO2UserAccountDto registerUser(WSO2UserAccountDto wso2UserAccountDto) {
        return wso2UserAccountDto;
    }

    public WSO2UserAccountDto registerFidoDevice(WSO2UserAccountDto wso2UserAccountDto) {
        return wso2UserAccountDto;
    }



}
