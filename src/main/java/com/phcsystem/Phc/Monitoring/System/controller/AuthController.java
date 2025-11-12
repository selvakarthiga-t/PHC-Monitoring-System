package com.phcsystem.Phc.Monitoring.System.controller;

import com.phcsystem.Phc.Monitoring.System.model.User;
import com.phcsystem.Phc.Monitoring.System.repository.UserRepo;
import com.phcsystem.Phc.Monitoring.System.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPasswordHash())
        );

        User dbUser = userRepo.findByUsername(user.getUsername());

        String token = jwtUtil.generateToken(dbUser.getUsername(), dbUser.getRole().name());
        return ResponseEntity.ok(token);
    }
}
