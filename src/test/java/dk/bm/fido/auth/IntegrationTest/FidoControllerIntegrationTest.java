package dk.bm.fido.auth.IntegrationTest;

import dk.bm.fido.auth.BaseTestSetup;
import org.apache.catalina.core.ApplicationContext;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.security.PublicKey;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

//@WithMockUser(username = "admin", password = "admin")
public class FidoControllerIntegrationTest extends BaseTestSetup {

    @Autowired
    MockMvc mockMvc;


    @Before()
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup().build();
    }

    @Test
    public void getFidoDevicesTest() throws Exception {
        assertThat(
                mockMvc.perform(
                        get("/fidodevices")
                                .with(oidcLogin()))
                        .andReturn()
                        .getResponse()
                        .getStatus()
        ).isEqualTo(200);
    }
}
