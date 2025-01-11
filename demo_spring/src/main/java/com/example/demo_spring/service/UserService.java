package com.example.demo_spring.service;

import com.example.demo_spring.model.User;
import com.example.demo_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Lấy tất cả người dùng
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Lấy người dùng theo ID
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    // Thêm người dùng
    public User addUser(User user) {
        return userRepository.save(user);
    }

    // Cập nhật người dùng theo ID
    public boolean updateUser(int id, User updatedUser) {
        Optional<User> existingUser = getUserById(id);
        if (existingUser.isPresent()) {
            updatedUser.setId(id);
            userRepository.save(updatedUser);
            return true;
        }
        return false;
    }

    // Xóa người dùng theo ID
    public boolean deleteUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
