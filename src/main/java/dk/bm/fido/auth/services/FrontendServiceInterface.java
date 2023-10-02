package dk.bm.fido.auth.services;

import dk.bm.fido.auth.dtos.UserAccountDto;
import dk.bm.fido.auth.dtos.WSO2UserAccountDto;
import dk.bm.fido.auth.models.UserAccount;
import org.springframework.stereotype.Component;

@Component
public interface FrontendServiceInterface {
    UserAccountDto authenticateUser(UserAccountDto uSerAccountDto);
    WSO2UserAccountDto checkUserAuthentication(WSO2UserAccountDto wso2UserAccountDto);
    UserAccountDto checkUserAuthentication(UserAccountDto userAccountDto);
}
