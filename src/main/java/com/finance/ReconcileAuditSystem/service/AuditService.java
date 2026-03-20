package com.finance.ReconcileAuditSystem.service;

import com.finance.ReconcileAuditSystem.model.Documents.AuditLogs;
import com.finance.ReconcileAuditSystem.repository.AuditLogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuditService {

    @Autowired
    private  AuditLogsRepository auditLogRepository;


    public void logAction(
            String action,
            String entityType,
            String entityId,
            String userId,
            String userName,
            String ipAddress,
            Map<String,Object> oldValue,
            Map<String,Object> newValue,
            String details
    ){

        AuditLogs log = AuditLogs.builder()
                .action(action)
                .entityType(entityType)
                .entityId(entityId)
                .userId(userId)
                .userName(userName)
                .ipAddress(ipAddress)
                .oldValue(oldValue)
                .newValue(newValue)
                .timestamp(Instant.now())
                .details(details)
                .build();

        auditLogRepository.save(log);
    }
}
