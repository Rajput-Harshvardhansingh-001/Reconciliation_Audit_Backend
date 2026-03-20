package com.finance.ReconcileAuditSystem.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class SignUpRequestDTO {
    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String password;
}
