package com.finance.ReconcileAuditSystem.repository;

import com.finance.ReconcileAuditSystem.model.StatusEnum.TransactionStatus;
import com.finance.ReconcileAuditSystem.model.Entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@Document
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

    Optional<Transactions> findByReferenceNumber(String referenceNumber);

    List<Transactions> findByStatus(TransactionStatus status);
}
