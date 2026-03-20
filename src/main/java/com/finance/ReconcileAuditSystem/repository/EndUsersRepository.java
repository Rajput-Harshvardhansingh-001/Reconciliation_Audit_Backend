package com.finance.ReconcileAuditSystem.repository;

import com.finance.ReconcileAuditSystem.model.Entities.EndUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndUsersRepository extends JpaRepository<EndUsers, Long> {
    EndUsers findByUsername(String username);

    boolean existsByEmail(String email);
}
