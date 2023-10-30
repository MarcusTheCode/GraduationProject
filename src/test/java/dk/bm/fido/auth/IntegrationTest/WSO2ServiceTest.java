package dk.bm.fido.auth.IntegrationTest;

import dk.bm.fido.auth.Application;
import dk.idconnect.backend.shared.fido.dtos.CredentialOptionsRequestDto;
import dk.idconnect.backend.shared.fido.dtos.DeviceDto;
import dk.idconnect.backend.shared.fido.services.WSO2Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class)
public class WSO2ServiceTest extends BaseTestSetup {
    @Autowired private WSO2Service wso2Service;

    @Value("${test.user.credentialId:}") private String credentialId;
    @Value("${test.user.name:}") private String username;
    @Value("${test.user.password:}") private String password;

    private String getBasicToken() {
        String userpass = username + ":" + password;
        return  "Basic " + jakarta.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void getDevicesTest() {
        List<DeviceDto> devices = wso2Service.getUserDevices(getBasicToken());

        assertThat(devices).isNotNull();
        assertThat(devices).isNotEmpty();
    }

    @Test
    public void editDeviceNameTest() {
        final String newName = "Test";

        wso2Service.editDeviceName(getBasicToken(), credentialId, newName);

        // Assert that the name has updated
        List<DeviceDto> devices = wso2Service.getUserDevices(getBasicToken());

        assertThat(devices.stream()).anyMatch(x ->
                x.getCredentialId().equals(credentialId) &&
                x.getDisplayName().equals(newName));
    }

    @Test
    public void startDeviceRegistrationTest() {
        CredentialOptionsRequestDto credentialOptions = wso2Service.startUserDeviceRegistration(getBasicToken());

        assertThat(credentialOptions).isNotNull();
        assertThat(credentialOptions.getRequestId()).isNotNull().isNotEmpty();

        assertThat(credentialOptions.getPublicKeyCredentialCreationOptions()).isNotNull();

        assertThat(credentialOptions.getPublicKeyCredentialCreationOptions().getRp()).isNotNull();
        assertThat(credentialOptions.getPublicKeyCredentialCreationOptions().getRp().getName()).isEqualTo("WSO2 Identity Server");

        assertThat(credentialOptions.getPublicKeyCredentialCreationOptions().getUser()).isNotNull();
        assertThat(credentialOptions.getPublicKeyCredentialCreationOptions().getUser().getDisplayName()).isEqualTo(username);

        assertThat(credentialOptions.getPublicKeyCredentialCreationOptions().getChallenge()).isNotNull().isNotEmpty();

        assertThat(credentialOptions.getPublicKeyCredentialCreationOptions().getPubKeyCredParams()).isNotNull().isNotEmpty();

        assertThat(credentialOptions.getPublicKeyCredentialCreationOptions().getExcludeCredentials()).isNotNull();

        assertThat(credentialOptions.getPublicKeyCredentialCreationOptions().getAttestation()).isNotNull().isNotEmpty();
    }
}
