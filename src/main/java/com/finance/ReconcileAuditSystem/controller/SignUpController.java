package com.finance.ReconcileAuditSystem.controller;

import com.finance.ReconcileAuditSystem.model.DTO.SignUpRequestDTO;
import com.finance.ReconcileAuditSystem.model.Entities.EndUsers;
import com.finance.ReconcileAuditSystem.model.StatusEnum.Role;
import com.finance.ReconcileAuditSystem.service.EndUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/signup")
public class SignUpController {

    @Autowired
    private EndUserService endUserService;

    @Autowired
    private PasswordEncoder encoder;

@GetMapping("/test")
public String test(){
    return "Testing....";
}

//    @PostMapping("/oldsignup")
//    public ResponseEntity<?> addEndUser(@RequestBody EndUsers user){
//        EndUsers savedUser = endUserService.addEndUsers(user);
//        if(savedUser!=null){
//            return new ResponseEntity<EndUsers>(savedUser, HttpStatus.ACCEPTED);
//        }
//        return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }

    @PostMapping("/up")
    public ResponseEntity<?> signup(@RequestBody SignUpRequestDTO signUpRequestDTO){

        if(signUpRequestDTO!=null && !signUpRequestDTO.getUsername().isBlank() && !signUpRequestDTO.getEmail().isBlank() && !signUpRequestDTO.getPassword().isBlank() ){
            EndUsers endUsers = new EndUsers();
            endUsers.setUsername(signUpRequestDTO.getUsername());
            endUsers.setEmail(signUpRequestDTO.getEmail());
            endUsers.setPasswordHash(encoder.encode(signUpRequestDTO.getPassword()));
            endUsers.setRole(Role.OPERATOR);
            endUsers.setIsActive(true);
            endUserService.saveUser(endUsers);
            return new ResponseEntity<>("Account has been created",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong",HttpStatus.BAD_REQUEST);
    }

}
