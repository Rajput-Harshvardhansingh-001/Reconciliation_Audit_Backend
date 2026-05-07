package com.finance.ReconcileAuditSystem.model.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "system_transactions")
@Data
@NoArgsConstructor
public class SystemTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_holder_name")
    private String accountHolderName;
    @Column(name = "account_id")
    private String accountId;

    private String description;

    private Double deposits;
    private Double withdrawal;
    private Double balance;

    private LocalDate date;
}
