package com.kdu.smarthome.services;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.kdu.smarthome.domain.user.User;
import com.kdu.smarthome.domain.user.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User addUser(String username, String password) {

        User user = new User();

        String hashedPassword = passwordEncoder.encode(password);
        
        user.setUsername(username); 
        user.setPassword(hashedPassword);

        return userRepository.save(user);

    }
}
