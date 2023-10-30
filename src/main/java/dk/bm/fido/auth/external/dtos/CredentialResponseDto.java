package dk.bm.fido.auth.external.dtos;

import lombok.Data;

@Data
public class CredentialResponseDto {
    String requestId;
    CredentialDto credential;
}