package dk.bm.fido.auth.unitTest;

import dk.bm.fido.auth.BaseTestSetup;

import dk.idconnect.backend.shared.fido.enums.W2isServerEPType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class W2isServerEpTypeTest extends BaseTestSetup {

    @Test
    void containsIntrospectEnum() {
        assertThat(W2isServerEPType.INTROSPECT).isNotNull();
    }

    @Test
    void containsGetFidoDevicesEnum() {
        assertThat(W2isServerEPType.GET_FIDO_DEVICES).isNotNull();
    }

    @Test
    void containsStartFidoRegistrationEnum() {
        assertThat(W2isServerEPType.START_FIDO_REGISTRATION).isNotNull();
    }

    @Test
    void containsFinishFidoRegistrationEnum() {
        assertThat(W2isServerEPType.FINISH_FIDO_REGISTRATION).isNotNull();
    }

    @Test
    void containsDeleteFidoDeviceEnum() {
        assertThat(W2isServerEPType.DELETE_FIDO_DEVICE).isNotNull();
    }

    @Test
    void containsEditFidoDeviceEnum() {
        assertThat(W2isServerEPType.EDIT_FIDO_DEVICE).isNotNull();
    }

}
