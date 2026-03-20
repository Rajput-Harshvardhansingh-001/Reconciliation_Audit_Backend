package com.finance.ReconcileAuditSystem.service;

import com.finance.ReconcileAuditSystem.model.Entities.EndUsers;
import com.finance.ReconcileAuditSystem.repository.EndUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    EndUsersRepository endUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EndUsers endUser = endUserRepo.findByUsername(username);
        if(endUser != null){
            return new org.springframework.security.core.userdetails.User(
                    endUser.getUsername(),
                    endUser.getPasswordHash(),
                    List.of(new SimpleGrantedAuthority("ROLE_"+endUser.getRole()))
            );
        }
        throw new UsernameNotFoundException("user not found");
    }
}
