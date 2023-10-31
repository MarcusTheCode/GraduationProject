package dk.bm.fido.auth.IntegrationTest;

import dk.bm.fido.auth.BaseTestSetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrationControllerIntegrationTest extends BaseTestSetup {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin@carbon.super", password = "admin")
    public void startRegistrationTest() throws Exception {
        mockMvc.perform(post("/register/start")).andExpect(status().isOk());
    }
}
