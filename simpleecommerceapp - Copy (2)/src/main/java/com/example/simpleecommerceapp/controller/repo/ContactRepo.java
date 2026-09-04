package com.example.simpleecommerceapp.controller.repo;

import com.example.simpleecommerceapp.entity.Message;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContactRepo extends JpaRepository<Message, Long> {

}
