package spb.alex.security_3_1_2.service;

import spb.alex.security_3_1_2.model.User;

import java.util.List;

public interface UserService {
    User findByName(String username);

    List<User> findAllUsers();

    void createUser(User user, Long[] selectedIds);

    void deleteUser(Long id);

    void updateUser(Long id, User user, Long[] selectedIds);
}
