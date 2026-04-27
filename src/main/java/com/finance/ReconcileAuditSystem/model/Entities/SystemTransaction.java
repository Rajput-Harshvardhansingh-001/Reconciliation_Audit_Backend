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

    @Column(name = "reference_no")
    private String referenceNo;
    @Column(name = "account_id")
    private String accountId;

    private Double amount;   // deposits OR withdrawal

    private String type;     // CREDIT / DEBIT

    private LocalDate date;

    private String description;
}
