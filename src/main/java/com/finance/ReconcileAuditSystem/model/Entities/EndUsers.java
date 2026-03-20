package com.finance.ReconcileAuditSystem.model.Entities;

import com.finance.ReconcileAuditSystem.model.StatusEnum.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class EndUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.OPERATOR;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "createdBy")
    private List<Transactions> transactions;

    @OneToMany(mappedBy = "uploadedBy")
    private List<UploadBatches> uploadBatches;

}
