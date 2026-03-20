package com.finance.ReconcileAuditSystem.model.Documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Document(collection = "audit_logs")
public class AuditLogs {

    @Id
    private String id;

    private String action;       // CREATE | UPDATE | DELETE | LOGIN | UPLOAD
    private String entityType;   // USER | TRANSACTION | BATCH
    private String entityId;

    private String userId;
    private String userName;

    private String ipAddress;

    private Map<String, Object> oldValue;
    private Map<String, Object> newValue;

    private Instant timestamp;

    private String details;
}