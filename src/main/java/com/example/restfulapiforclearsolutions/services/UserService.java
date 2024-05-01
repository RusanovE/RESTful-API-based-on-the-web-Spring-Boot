package com.example.restfulapiforclearsolutions.services;

import com.example.restfulapiforclearsolutions.DTO.UserDTO;
import com.example.restfulapiforclearsolutions.models.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    List<User> getUsersByBirthDateRange(LocalDate firstDate, LocalDate lastDate);

    List<User> getAllUsers();

    User getUserById(long userId);

    User createUser(UserDTO userDTO);

    boolean updateUser(long userId, UserDTO userDTO);

    boolean updateUserPartly(long userId, UserDTO userDTO);

    boolean deleteUser(long userId);
}
