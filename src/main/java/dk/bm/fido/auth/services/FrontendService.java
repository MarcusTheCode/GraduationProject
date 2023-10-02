package dk.bm.fido.auth.services;

import dk.bm.fido.auth.dtos.UserAccountDto;
import dk.bm.fido.auth.dtos.WSO2UserAccountDto;
import org.springframework.stereotype.Service;

@Service
public class FrontendService implements FrontendServiceInterface{

    final WSO2Service wso2Service;
    final RepositoryService repositoryService;

    public FrontendService(WSO2Service wso2Service, RepositoryService repositoryService) {
        this.wso2Service = wso2Service;
        this.repositoryService = repositoryService;
    }


    @Override
    public UserAccountDto authenticateUser(UserAccountDto uSerAccountDto) {
        return null;
    }

    @Override
    public WSO2UserAccountDto checkUserAuthentication(WSO2UserAccountDto wso2UserAccountDto) {
        return null;
    }

    @Override
    public UserAccountDto checkUserAuthentication(UserAccountDto userAccountDto) {
        return null;
    }
}
