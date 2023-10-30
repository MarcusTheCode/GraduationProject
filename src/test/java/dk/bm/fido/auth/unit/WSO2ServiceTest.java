package dk.bm.fido.auth.unit;

import dk.bm.fido.auth.Application;
import dk.bm.fido.auth.external.dtos.DeviceDto;
import dk.bm.fido.auth.external.services.WSO2Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("cbr")
public class WSO2ServiceTest {
    @Autowired private WSO2Service wso2Service;

    @Value("${test.user.name:john}")
    private String username;

    @Value("${test.user.password:Bacon}")
    private String password;

    private String getBasicToken() {
        String userpass = username + ":" + password;
        return  "Basic " + jakarta.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void getUserDevicesTest() {
        List<DeviceDto> devices = wso2Service.getUserDevices(getBasicToken());

        assertThat(devices).isNotNull();
        assertThat(devices).isNotEmpty();
    }
}
