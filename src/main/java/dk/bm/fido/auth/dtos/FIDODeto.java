package dk.bm.fido.auth.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FIDODeto {
    @Getter
    @Setter
    public static class UserIdentityDto {
        private String name;
        private String displayName;
        private String id;
    }

    @Getter
    @Setter
    public static class CredentialDto {
        private String credentialId;
        private String userHandle;
        private String publicKeyCose;
        private long signatureCount;
    }

    private long signatureCount;
    private UserIdentityDto userIdentity;
    private CredentialDto credential;
    private Date registrationTime;
    private boolean isUsernamelessSupported;
    private String displayName;
}
