package com.finance.ReconcileAuditSystem.service;

import com.finance.ReconcileAuditSystem.model.Entities.EndUsers;
import com.finance.ReconcileAuditSystem.repository.EndUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EndUserService {

    @Autowired
    private EndUsersRepository endUsersRepository;

    @Autowired
    private PasswordEncoder encoder;

    public EndUsers addEndUsers(EndUsers user){
        if(user==null){
           throw new NullPointerException("End User Object is null");
        }
        try{
            user.setPasswordHash(encoder.encode(user.getPasswordHash()));
            return endUsersRepository.save(user);
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }


    public void saveUser(EndUsers endUsers) {
        try{
            if(endUsers!=null){
                endUsersRepository.save(endUsers);
            }
        } catch (Exception e) {
            throw new RuntimeException(e+"User is Null");
        }
    }
}
