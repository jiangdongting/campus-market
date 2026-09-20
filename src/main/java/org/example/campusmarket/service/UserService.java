package org.example.campusmarket.service;

import org.example.campusmarket.entity.User;
import org.example.campusmarket.mapper.UserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public int register(User user) {
        String hashedPassword = encoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return userMapper.insert(user);
    }

    public User login(String username, String password) {
        User dbUser = userMapper.findByUsername(username);
        if (dbUser == null) {
            return null;
        }
        if (!encoder.matches(password, dbUser.getPassword())){
            return null;
        }
        return dbUser;
    }

}
