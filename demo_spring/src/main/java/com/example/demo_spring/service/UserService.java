package com.example.demo_spring.service;

import com.example.demo_spring.model.Company;
import com.example.demo_spring.model.User;
import com.example.demo_spring.repository.CompanyRepository;
import com.example.demo_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    // Lấy danh sách tất cả người dùng
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Thêm mới user
    public User addUser(User user) {
        return userRepository.save(user);
    }

    // Lấy user theo ID
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    // Cập nhật thông tin user
    public boolean updateUser(int id, User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setClassSchool(updatedUser.getClassSchool());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setImgURL(updatedUser.getImgURL());
            userRepository.save(existingUser);
            return true;
        }
        return false;
    }

    // Xóa user
    // Xóa user và cập nhật company liên quan
    public boolean deleteUser(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Xóa liên kết với công ty trước khi xóa user
            if (user.getCompany() != null) {
                Company company = user.getCompany();
                company.getUsers().remove(user);
                companyRepository.save(company);
            }

            // Xóa user khỏi database
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }


    // Lưu user vào một công ty
    public void saveUser(User user, Integer companyId) {
        if (companyId != null) {
            Optional<Company> companyOptional = companyRepository.findById(companyId);
            companyOptional.ifPresent(user::setCompany);
        }
        userRepository.save(user);
    }
    // Xóa User khỏi công ty


}
