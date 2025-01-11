package com.example.demo_spring.service;

import com.example.demo_spring.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    // Danh sách người dùng giả lập
    private final List<User> users = new ArrayList<>();

    // Constructor để khởi tạo danh sách người dùng
    public UserService() {
        users.add(new User(1, "Tran Ngoc Tien", "ST22D", "0904715983", "tranngoctien10304@gmail.com", "https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg"));
        users.add(new User(2, "Nguyen Van A", "ST21A", "0904123456", "nguyenvana@gmail.com", "https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg"));
        users.add(new User(3, "Le Thi B", "ST20B", "0904234567", "lethib@gmail.com", "https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg"));
        users.add(new User(4, "Pham Van C", "ST19C", "0904345678", "phamvanc@gmail.com", "https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg"));
    }


    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }


    public Optional<User> getUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    public User addUser(User user) {
        int newId = users.isEmpty() ? 1 : users.get(users.size() - 1).getId() + 1;
        user.setId(newId);
        users.add(user);
        return user;
    }

    // 4. Cập nhật thông tin người dùng theo ID (UPDATE)
    public boolean updateUser(int id, User updatedUser) {
        Optional<User> existingUser = getUserById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(updatedUser.getName());
            user.setClassSchool(updatedUser.getClassSchool());
            user.setPhone(updatedUser.getPhone());
            user.setEmail(updatedUser.getEmail());
            user.setImgURL(updatedUser.getImgURL());
            return true;
        }
        return false;
    }

    public boolean deleteUser(long id) {
        return users.removeIf(user -> user.getId() == id);
    }
}
