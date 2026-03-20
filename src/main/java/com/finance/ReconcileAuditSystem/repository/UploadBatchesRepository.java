package com.finance.ReconcileAuditSystem.repository;

import com.finance.ReconcileAuditSystem.model.Entities.UploadBatches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadBatchesRepository extends JpaRepository<UploadBatches, Long> {
}
