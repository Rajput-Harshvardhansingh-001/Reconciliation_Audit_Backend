package com.finance.ReconcileAuditSystem.repository;

import com.finance.ReconcileAuditSystem.model.Entities.SystemTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SystemTransactionRepo extends JpaRepository<SystemTransaction, Long> {

    List<SystemTransaction> findByAccountId(String account_id);
}