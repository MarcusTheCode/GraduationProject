package dk.bm.fido.auth.IntegrationTest;

import dk.bm.fido.auth.BaseTestSetup;
import dk.bm.fido.auth.config.SecurityConfiguration;
import dk.bm.fido.auth.external.dtos.DeviceDto;
import dk.bm.fido.auth.external.services.WSO2Service;
import dk.bm.fido.auth.services.FrontEndService;
import org.apache.catalina.core.ApplicationContext;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.security.PublicKey;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WithMockUser(username = "admin", password = "admin")
public class FidoControllerIntegrationTest extends BaseTestSetup {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WSO2Service wso2Service;
    @Autowired
    private FrontEndService frontEndService;


    @Test
    @WithMockUser(username = "admin@carbon.super", password = "admin")
    public void getFidoDevicesTest() throws Exception {
        mockMvc.perform(
                get("/fidoDevices"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@carbon.super", password = "admin")
    public void getEditFidoDeviceTest() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<DeviceDto> devices = wso2Service.getUserDevices(frontEndService.getAccessToken(authentication));
        if (devices.isEmpty()) {
            throw new Exception("No devices to edit");
        }
        Random random = new Random();
        String updatedName = "editTest" + random.nextInt();

        mockMvc.perform(
                post(String.format("/fidoDevices/editDevice/%s/%s", devices.get(0).getCredentialId(), updatedName)));

        String newName = wso2Service.getUserDevices(frontEndService.getAccessToken(authentication)).get(0).getDisplayName();
        if (!newName.equals(updatedName)) {
            throw new Exception("Name not updated:" + newName + " is different from " + updatedName);
        }
    }


}
