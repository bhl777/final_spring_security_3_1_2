package spb.alex.security_3_1_2.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spb.alex.security_3_1_2.model.Role;
import spb.alex.security_3_1_2.model.User;
import spb.alex.security_3_1_2.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

    public void createUser(User user, Long[] roles) {

        User existingUser = findByName(user.getUsername());
        if (existingUser != null) {
            throw new IllegalArgumentException("Пользователь с именем '" + user.getUsername() + "' уже существует");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // Кодируем пароль
        user.setRoles(newRoleSet(roles));
        userRepository.save(user);
    }

    private Set<Role> newRoleSet(Long[] roles) {
        Set<Role> roleSet = new HashSet<>();

        for (Long l : roles) {
            roleSet.add(new Role(l));
        }

        return roleSet;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void updateUser(Long id, User updatedUser, Long[] roles) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с ID '" + id + "' не найден"));

        existingUser.setUsername(updatedUser.getUsername());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        existingUser.setRoles(newRoleSet(roles));

        userRepository.save(existingUser);
    }

    public User findByName(String username)
    {
        return userRepository.findUserByUsername(username);
    }
}
