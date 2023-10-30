package dk.bm.fido.auth.IntegrationTest;

import dk.bm.fido.auth.Application;
import dk.bm.fido.auth.controllers.FidoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = Application.class)
public class FidoControllerTest extends BaseTestSetup{

    @Autowired private FidoController fidoController;

    @Test
    void FidoControllerIsLoaded(){
        assertThat(fidoController).isNotNull();
    }

}
