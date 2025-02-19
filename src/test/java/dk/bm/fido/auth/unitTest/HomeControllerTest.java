package dk.bm.fido.auth.IntegrationTest;

import dk.bm.fido.auth.Application;
import dk.bm.fido.auth.BaseTestSetup;
import dk.bm.fido.auth.controllers.HomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = Application.class)
public class HomeControllerTest extends BaseTestSetup {

    @Autowired private HomeController homeController;

    @Test
    void HomeControllerIsLoaded(){
        assertThat(homeController).isNotNull();
    }


}
