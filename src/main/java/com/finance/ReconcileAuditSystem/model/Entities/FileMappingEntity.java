package com.finance.ReconcileAuditSystem.model.Entities;

import com.finance.ReconcileAuditSystem.model.StatusEnum.ReconciliationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "e_statement")
public class FileMappingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "e_id")
    private long statement_id;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Column(name = "account_id")
    private String accountId;

    private String description;
    private Double deposits;
    private Double withdrawal;
    private Double balance;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private ReconciliationStatus status;  // MATCHED / MISMATCH / DUPLICATE
}
