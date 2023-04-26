package com.nandivaleamol.services;

import com.nandivaleamol.entities.User;
import java.util.List;

public interface UserService {

    User createNewUser(User user);

    User getUserByUserId(Integer userId);

    List<User> getAllUsers();

    User updateUserByUserId(User user, Integer userId);

    void deleteUserByUserId(Integer userId);
}
