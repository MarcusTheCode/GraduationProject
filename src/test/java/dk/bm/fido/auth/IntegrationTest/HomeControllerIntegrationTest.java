package dk.bm.fido.auth.IntegrationTest;

import dk.bm.fido.auth.Application;
import dk.bm.fido.auth.BaseTestSetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
public class HomeControllerIntegrationTest extends BaseTestSetup {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getRootTest() throws Exception {
        assertThat(this.mockMvc.perform(get("/").with(oidcLogin())).andReturn().getResponse().getStatus())
                .isEqualTo(200);
    }
}
