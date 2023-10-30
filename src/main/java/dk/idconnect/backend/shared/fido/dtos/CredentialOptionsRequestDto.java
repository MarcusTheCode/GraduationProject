package dk.idconnect.backend.shared.fido.dtos;

import lombok.Data;

@Data
public class CredentialOptionsRequestDto {
    String requestId;
    CredentialOptionsDto publicKeyCredentialCreationOptions;
}
