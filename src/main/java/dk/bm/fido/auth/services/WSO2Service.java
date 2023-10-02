package dk.bm.fido.auth.services;

import dk.bm.fido.auth.dtos.DeviceDto;
import dk.bm.fido.auth.dtos.WSO2UserAccountDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WSO2Service {

    final CurrentUserService currentUserService;
    public WSO2Service(
            CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
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
