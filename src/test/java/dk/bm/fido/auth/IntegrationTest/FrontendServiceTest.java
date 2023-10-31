package dk.bm.fido.auth.IntegrationTest;

import dk.bm.fido.auth.BaseTestSetup;
import dk.bm.fido.auth.services.FrontEndService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FrontendServiceTest extends BaseTestSetup {

    @Autowired
    FrontEndService frontEndService;

    @Test
    @WithMockUser(username = "admin@carbon.super", password = "admin")
    public void getAccessTokenTest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = frontEndService.getAccessToken(authentication);
        assertThat(token).contains("Basic");
    }

}
