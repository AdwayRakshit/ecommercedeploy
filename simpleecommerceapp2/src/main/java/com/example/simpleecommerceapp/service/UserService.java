package com.example.simpleecommerceapp.service;

import com.example.simpleecommerceapp.entity.User;
import com.example.simpleecommerceapp.controller.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }
    public User findById(Long id){
        return userRepo.findById(id).orElseThrow(()->new RuntimeException("User with id "+id+" not found"));
    }

    public void updateUser(User user) {

        User existingUser = userRepo.findById(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("User with id " + user.getId() + " not found"));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());

        // DO NOT TOUCH PASSWORD (since form doesn't send it)

        userRepo.save(existingUser);
    }
    public  void deleteUser(Long id){
        userRepo.findById(id).orElseThrow(()->new RuntimeException("User with id "+id+" not found"));
        userRepo.deleteById(id);
    }
    public boolean verifyCredentials(String email, String password){
        User user = userRepo.findByEmail(email);

        if (user == null) {
            return false;
        }

        return user.getPassword() != null &&
                user.getPassword().equals(password);
    }
     public void  createUser(User user){
        userRepo.save(user);
     }

     public User findUserByEmail(String email){
        return userRepo.findByEmail(email);
     }

}
