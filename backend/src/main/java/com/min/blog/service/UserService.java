package com.min.blog.service;

import com.min.blog.model.User;
import com.min.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByAccessToken(String accessToken){return userRepository.findByAccessToken(accessToken);}

    public User save(User user){
        return userRepository.save(user);
    }

}
