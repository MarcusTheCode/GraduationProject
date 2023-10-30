package dk.bm.fido.auth.IntegrationTest;

import dk.bm.fido.auth.Application;
import dk.bm.fido.auth.controllers.FidoController;
import dk.bm.fido.auth.controllers.HomeController;
import dk.bm.fido.auth.controllers.LoginController;
import dk.bm.fido.auth.controllers.RegistrationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = Application.class)
public class LoginControllerTest extends BaseTestSetup{

    @Autowired private LoginController loginController;

    @Test
    void LoginControllerIsLoaded(){
        assertThat(loginController).isNotNull();
    }

}
