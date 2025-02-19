package dk.bm.fido.auth.IntegrationTest;

import dk.bm.fido.auth.BaseTestSetup;

import dk.bm.fido.auth.external.dtos.CredentialOptionsRequestDto;
import dk.bm.fido.auth.external.dtos.DeviceDto;
import dk.bm.fido.auth.external.services.WSO2Service;
import dk.bm.fido.auth.services.FrontEndService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WSO2ServiceTest extends BaseTestSetup {
    @Autowired private WSO2Service wso2Service;

    @Value("${test.user.name:}") private String username;
    @Value("${test.user.password:}") private String password;

    @Test
    public void getDevicesTest() {
        List<DeviceDto> devices = wso2Service.getUserDevices(FrontEndService.getBasicToken(username, password));

        assertThat(devices).isNotNull();
        assertThat(devices).isNotEmpty();
    }

    @Test
    public void editDeviceNameTest() {
        final String newName = "Test";

        List<DeviceDto> devices = wso2Service.getUserDevices(FrontEndService.getBasicToken(username, password));

        //  Assert that the user has FIDO2 devices available
        assertThat(devices).asList().isNotEmpty();

        //  Edit name of first device
        String credentialId = devices.get(0).getCredentialId();
        wso2Service.editDeviceName(FrontEndService.getBasicToken(username, password), credentialId, newName);


        // Assert that the name has updated
        devices = wso2Service.getUserDevices(FrontEndService.getBasicToken(username, password));
        assertThat(devices.get(0).getDisplayName()).isEqualTo(newName);
    }

    @Test
    public void startDeviceRegistrationTest() {
        CredentialOptionsRequestDto credentialOptions = wso2Service.startUserDeviceRegistration(FrontEndService.getBasicToken(username, password));

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
