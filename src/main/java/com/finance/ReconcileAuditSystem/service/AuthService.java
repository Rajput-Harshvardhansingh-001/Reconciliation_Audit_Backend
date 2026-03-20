package com.finance.ReconcileAuditSystem.service;

import com.finance.ReconcileAuditSystem.model.Entities.EndUsers;
import com.finance.ReconcileAuditSystem.repository.EndUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private EndUsersRepository usersRepository;

    public EndUsers RegisterUser(EndUsers user){
        return usersRepository.save(user);
    }

}
