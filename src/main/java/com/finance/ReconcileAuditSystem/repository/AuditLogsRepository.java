package com.finance.ReconcileAuditSystem.repository;

import com.finance.ReconcileAuditSystem.model.Documents.AuditLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuditLogsRepository extends MongoRepository<AuditLogs, String> {

    List<AuditLogs> findByUserId(String userId);

    List<AuditLogs> findByEntityType(String entityType);

    List<AuditLogs> findByAction(String action);
}
