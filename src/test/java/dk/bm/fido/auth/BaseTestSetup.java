package dk.bm.fido.auth;

import dk.bm.fido.auth.config.SecurityConfiguration;
import dk.bm.fido.auth.config.TrustConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {Application.class, SecurityConfiguration.class, TrustConfig.class})
@AutoConfigureMockMvc
@ActiveProfiles("cbr")
public abstract class BaseTestSetup {
}
