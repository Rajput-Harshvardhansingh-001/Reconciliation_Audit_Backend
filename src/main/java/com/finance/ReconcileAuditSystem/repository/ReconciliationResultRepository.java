package com.finance.ReconcileAuditSystem.repository;

import com.finance.ReconcileAuditSystem.model.Entities.ReconciliationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReconciliationResultRepository
        extends JpaRepository<ReconciliationResult, Long> {

    List<ReconciliationResult> findByBatchId(Long batchId);
}
