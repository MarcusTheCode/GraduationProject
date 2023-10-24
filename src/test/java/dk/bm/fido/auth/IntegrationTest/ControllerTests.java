package dk.bm.fido.auth.IntegrationTest;

import dk.bm.fido.auth.controllers.FidoController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("magby")
public class ControllerTests {

    private final FidoController fidoController;

    public ControllerTests(FidoController fidoController) {
        this.fidoController = fidoController;
    }

    @Test
    void FidoControllerIsLoaded(){
        assertThat(fidoController).isNotNull();
    }
    @Test
    void HomeControllerIsLoaded(){

    }
    @Test
    void LoginControllerIsLoaded(){

    }

@Test
    void RegistrationControllerIsLoaded(){

    }


}
