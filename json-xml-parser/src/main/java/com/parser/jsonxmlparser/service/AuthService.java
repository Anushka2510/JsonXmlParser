package com.parser.jsonxmlparser.service;

import com.parser.jsonxmlparser.model.User;
import com.parser.jsonxmlparser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public boolean authenticateUser(String username, String password) {
        User user = userRepository.findByEmailId(username);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
