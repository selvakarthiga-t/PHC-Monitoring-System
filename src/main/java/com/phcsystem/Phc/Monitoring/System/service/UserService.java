package com.phcsystem.Phc.Monitoring.System.service;


import com.phcsystem.Phc.Monitoring.System.model.Phc;
import com.phcsystem.Phc.Monitoring.System.model.Role;
import com.phcsystem.Phc.Monitoring.System.model.User;
import com.phcsystem.Phc.Monitoring.System.repository.PhcRepo;
import com.phcsystem.Phc.Monitoring.System.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    public UserRepo userRepo;

    @Autowired
    public PhcRepo phcRepo;

    public User createUser(User user) {
       return userRepo.save(user);
    }

    public User getUserById(Long id) throws Exception {
        return userRepo.findById(id)
                .orElseThrow(() -> new Exception("User not found with id " + id));
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User updateUser(Long id, User user) {
        User Exuser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        if (user.getName() != null) {
            Exuser.setName(user.getName());
        }
        if (user.getUsername() != null) {
            Exuser.setUsername(user.getUsername());
        }
        if (user.getRole() != null) {
            Exuser.setRole(user.getRole());
        }
        if (user.getPhc() != null) {
           Exuser.setPhc(user.getPhc());
        }
        return userRepo.save(Exuser);
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public User assignRoleToUser(Long userId, Role role) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not Found"));
        user.setRole(role);
        return userRepo.save(user);
    }
}