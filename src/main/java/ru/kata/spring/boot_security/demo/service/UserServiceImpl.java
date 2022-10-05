package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

   private final UserDAO userDAO;

   private final PasswordEncoder passwordEncoder;

   private final RoleService roleService;


    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder, RoleService roleService){
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.saveAndFlush(user);

    }
    @Transactional
    @Override
    public void updateUser(User updateUser) {
        updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        userDAO.saveAndFlush(updateUser);

    }

    @Transactional
    @Override
    public void deleteUser(int id) {
        userDAO.deleteById(id);

    }

    @Override
    public User getUserByLogin(String login) {
        return userDAO.getUserByLogin(login);
    }
}
