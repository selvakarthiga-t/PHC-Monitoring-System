package com.phcsystem.Phc.Monitoring.System.controller;


import com.phcsystem.Phc.Monitoring.System.model.Role;
import com.phcsystem.Phc.Monitoring.System.model.User;
import com.phcsystem.Phc.Monitoring.System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    public UserService userService;

    @PostMapping("user/adduser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saved = userService.createUser(user);
        return ResponseEntity.ok(saved);
    }


    @GetMapping("user/getuser/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) throws Exception {
       User saved = userService.getUserById(id);
       return ResponseEntity.ok(saved);
    }

    @GetMapping("user/getallusers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PutMapping("user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,@RequestBody User user){
        User saved = userService.updateUser(id,user);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("user/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @GetMapping("user/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username){
        User saved = userService.findByUsername(username);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/user/{id}/role")
    public ResponseEntity<User> assignRole(@PathVariable("id") Long userId, @RequestBody Role role) {
        User updatedUser = userService.assignRoleToUser(userId, role);
        return ResponseEntity.ok(updatedUser);
    }

}
