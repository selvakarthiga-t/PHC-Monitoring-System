package com.phcsystem.Phc.Monitoring.System.repository;


import com.phcsystem.Phc.Monitoring.System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
