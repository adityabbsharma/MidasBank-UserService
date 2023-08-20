package com.example.midasapp.service;

import com.example.midasapp.entity.User;
import com.example.midasapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordCryptographyService passwordCryptographyService;

    public void addUser(User user) throws Exception {
        String encryptedPassword = passwordCryptographyService.encrypt(user.getPassword(),
                passwordCryptographyService.generateSecretKey(user.getPassword()));
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }



}
