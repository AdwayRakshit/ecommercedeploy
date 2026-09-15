package com.example.simpleecommerceapp.controller.repo;

import com.example.simpleecommerceapp.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    public User findByEmail(String email);
    @Transactional
    @Query(value = "SELECT setval(users_id_seq, (SELECT MAX(id) FROM users))", nativeQuery = true)
    void resetUserSequence();
}
