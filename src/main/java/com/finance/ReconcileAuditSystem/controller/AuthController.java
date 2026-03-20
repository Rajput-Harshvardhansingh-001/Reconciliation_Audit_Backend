package com.finance.ReconcileAuditSystem.controller;

import com.finance.ReconcileAuditSystem.model.DTO.SignInRequestDTO;
import com.finance.ReconcileAuditSystem.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);

    @GetMapping("/test")
    public String check(){
        return "check";
    }

    @PostMapping("/signin")
    public ResponseEntity<?> SignIn(@RequestBody SignInRequestDTO requestDTO,HttpServletRequest httpServletRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getUsername(),
                        requestDTO.getPassword()
                )
        );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context
        );

        return new ResponseEntity<>("SignIn Successfully",HttpStatus.OK);
    }

    @PostMapping("/signout")
    public void signout(HttpServletRequest request, HttpServletResponse response) throws Exception{
        new SecurityContextLogoutHandler().logout(request,response,null);
    }

    @GetMapping("/me")
    public ResponseEntity<?> currentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication.getName().equals("anonymousUser")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Signed in");
            }
        return ResponseEntity.ok(authentication.getName());
    }

}
