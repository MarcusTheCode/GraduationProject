package dk.idconnect.backend.shared.fido.dtos;

import lombok.Data;

@Data
public class CredentialResponseDto {
    String requestId;
    CredentialDto credential;
}