package com.nandivaleamol.services.impl;

import com.nandivaleamol.config.AppConstant;
import com.nandivaleamol.entities.Role;
import com.nandivaleamol.entities.User;
import com.nandivaleamol.exceptions.ResourceNotFoundException;
import com.nandivaleamol.repositories.RoleRepository;
import com.nandivaleamol.repositories.UserRepository;
import com.nandivaleamol.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createNewUser(User user) {
        //encoded password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //roles
        Role role = this.roleRepository.findById(AppConstant.NORMAL_USER).get();
        user.getRoles().add(role);
        return this.userRepository.save(user);
    }

    @Override
    public User getUserByUserId(Integer userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User updateUserByUserId(User user, Integer userId) {
        User user1 = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        user1.setName(user.getName().trim());
        user1.setEmail(user.getEmail().strip());
        user1.setAbout(user.getAbout().strip());
        user1.setState(user.getState().strip());
        user1.setCity(user.getCity().strip());

        user1.setPassword(passwordEncoder.encode(user.getPassword().strip()));

        var id = user1.getId();

//        user1.setAddress(user.getAddress());
        //-----------------Role----------------
        var role = this.roleRepository.findById(AppConstant.NORMAL_USER).get();
        user1.getRoles().add(role);

        this.userRepository.save(user1);
        return user1;
    }

    @Override
    public void deleteUserByUserId(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        this.userRepository.delete(user);
    }
}
