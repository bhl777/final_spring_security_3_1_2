package spb.alex.security_3_1_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spb.alex.security_3_1_2.model.Role;
import spb.alex.security_3_1_2.model.User;
import spb.alex.security_3_1_2.repository.RoleRepository;
import spb.alex.security_3_1_2.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserDetailsService,UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
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
            roleSet.add(roleRepository.findRoleById(l));
        }

        return roleSet;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateUser(Long id, User updatedUser, Long[] roles) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с ID '" + id + "' не найден"));

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPhone(updatedUser.getPhone());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        existingUser.setRoles(newRoleSet(roles));

        userRepository.save(existingUser);
    }

    @Override
    public User findByName(String username)
    {
        return userRepository.findUserByUsername(username);
    }
}
