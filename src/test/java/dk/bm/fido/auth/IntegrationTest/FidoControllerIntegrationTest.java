package dk.bm.fido.auth.IntegrationTest;

import dk.bm.fido.auth.BaseTestSetup;
import dk.bm.fido.auth.config.SecurityConfiguration;
import org.apache.catalina.core.ApplicationContext;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.security.PublicKey;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WithMockUser(username = "admin", password = "admin")
public class FidoControllerIntegrationTest extends BaseTestSetup {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin@carbon.super", password = "admin")
    public void getFidoDevicesTest() throws Exception {
        mockMvc.perform(
                get("/fidoDevices"))
                .andExpect(status().isOk());
    }
}
