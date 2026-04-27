package com.finance.ReconcileAuditSystem.repository;

import com.finance.ReconcileAuditSystem.model.Entities.FileMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMapRepo extends JpaRepository<FileMappingEntity,Long> {
}
