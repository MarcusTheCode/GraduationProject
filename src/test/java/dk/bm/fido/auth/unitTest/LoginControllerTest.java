package dk.bm.fido.auth.unitTest;

import dk.bm.fido.auth.BaseTestSetup;
import dk.bm.fido.auth.controllers.LoginController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LoginControllerTest extends BaseTestSetup {

    @Autowired private LoginController loginController;

    @Test
    void LoginControllerIsLoaded(){
        assertThat(loginController).isNotNull();
    }

}
