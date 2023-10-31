package dk.bm.fido.auth.IntegrationTest;

import dk.bm.fido.auth.BaseTestSetup;
import dk.bm.fido.auth.WithTestUser;
import dk.bm.fido.auth.external.dtos.DeviceDto;
import dk.bm.fido.auth.external.services.WSO2Service;
import dk.bm.fido.auth.services.FrontEndService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FidoControllerIntegrationTest extends BaseTestSetup {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WSO2Service wso2Service;
    @Autowired
    private FrontEndService frontEndService;

    @Test
    @WithTestUser
    public void getFidoDevicesTest() throws Exception {
        mockMvc.perform(
                get("/fidoDevices"))
                .andExpect(status().isOk());
    }

    @Test
    @WithTestUser
    public void getEditFidoDeviceTest() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<DeviceDto> devices = wso2Service.getUserDevices(frontEndService.getAccessToken(authentication));
        assertThat(devices).asList().isNotEmpty();
        Random random = new Random();
        String updatedName = "editTest" + random.nextInt();
        mockMvc.perform(
                post(String.format("/fidoDevices/editDevice/%s/%s", devices.get(0).getCredentialId(), updatedName)));

        String newName = wso2Service.getUserDevices(frontEndService.getAccessToken(authentication)).get(0).getDisplayName();
        assertThat(newName).isEqualTo(updatedName);
    }
}