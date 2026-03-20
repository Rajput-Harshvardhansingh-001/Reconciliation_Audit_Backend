package com.finance.ReconcileAuditSystem.model.Entities;
import com.finance.ReconcileAuditSystem.model.StatusEnum.ReconciliationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reconciliation_results")
public class ReconciliationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private UploadBatches batch;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transactions transactions;

    @Column(name = "uploaded_reference")
    private String uploadedReference;

    @Column(name = "uploaded_amount", precision = 15, scale = 2)
    private BigDecimal uploadedAmount;

    @Enumerated(EnumType.STRING)
    private ReconciliationStatus status;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

}
