package com.finance.ReconcileAuditSystem.model.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@Entity
@Table(name = "upload_batches")
public class UploadBatches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "batch_id")
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Column(name = "upload_date", insertable = false, updatable = false)
    private LocalDateTime uploadDate;

    @Column(name = "total_records")
    private Integer totalRecords;

    @Column(name = "matched_count")
    private Integer matchedCount = 0;

    @Column(name = "mismatched_count")
    private Integer mismatchedCount = 0;

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private EndUsers uploadedBy;

    @OneToMany(mappedBy = "batch")
    private List<ReconciliationResult> results;

}
