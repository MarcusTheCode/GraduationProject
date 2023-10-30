package dk.bm.fido.auth;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("magby")
public abstract class BaseTestSetup {
}
