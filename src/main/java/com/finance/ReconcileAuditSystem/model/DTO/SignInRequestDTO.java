package com.finance.ReconcileAuditSystem.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class SignInRequestDTO {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
