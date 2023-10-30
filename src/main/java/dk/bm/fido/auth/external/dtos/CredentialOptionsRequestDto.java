package dk.bm.fido.auth.external.dtos;

import lombok.Data;

@Data
public class CredentialOptionsRequestDto {
    String requestId;
    CredentialOptionsDto publicKeyCredentialCreationOptions;
}
