package com.example.simpleecommerceapp.service;

import com.example.simpleecommerceapp.entity.Admin;
import com.example.simpleecommerceapp.controller.repo.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    // Get all admins
    public List<Admin> getAllAdmin() {
        return adminRepo.findAll();
    }

    // Get admin by ID
    public Admin getAdminById(Long id) {
        return adminRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin with id " + id + " not found"));
    }

    // Create admin
    public void createUser(Admin admin) {
        adminRepo.save(admin);
    }

    // Update admin safely (password will not reset)
    public void updateAdmin(Admin admin) {

        Admin existingAdmin = adminRepo.findById(admin.getId())
                .orElseThrow(() -> new RuntimeException("Admin with id " + admin.getId() + " not found"));

        existingAdmin.setName(admin.getName());
        existingAdmin.setEmail(admin.getEmail());

        // Only update password if provided
        if (admin.getPassword() != null && !admin.getPassword().isEmpty()) {
            existingAdmin.setPassword(admin.getPassword());
        }

        adminRepo.save(existingAdmin);
    }

    // Delete admin
    public void deleteAdmin(Long id) {
        Admin admin = adminRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin with id " + id + " not found"));

        adminRepo.delete(admin);
    }

    // Safe credential verification
    public boolean verifyCredentials(String email, String password) {

        Admin admin = adminRepo.findByEmail(email);

        if (admin == null) {
            return false;
        }

        if (admin.getPassword() == null) {
            return false;
        }

        return admin.getPassword().equals(password);
    }
}