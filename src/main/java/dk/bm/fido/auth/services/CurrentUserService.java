package dk.bm.fido.auth.services;

import dk.bm.fido.auth.dtos.UserAccountDto;
import dk.bm.fido.auth.dtos.WSO2UserAccountDto;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    private WSO2UserAccountDto wso2UserAccountDto;
    private UserAccountDto userAccountDto;

    public WSO2UserAccountDto getWso2UserAccountDto() {
        return wso2UserAccountDto;
    }

    protected void setWso2UserAccountDto(WSO2UserAccountDto wso2UserAccountDto) {
        this.wso2UserAccountDto = wso2UserAccountDto;
    }

    public UserAccountDto getUserAccountDto() {
        return userAccountDto;
    }

    protected void setUserAccountDto(UserAccountDto userAccountDto) {
        this.userAccountDto = userAccountDto;
    }
}
