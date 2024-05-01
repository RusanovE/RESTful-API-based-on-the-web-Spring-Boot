package com.example.restfulapiforclearsolutions.services.impl;

import com.example.restfulapiforclearsolutions.DTO.UserDTO;
import com.example.restfulapiforclearsolutions.controllers.exceptionHendlers.exceptions.ValidationException;
import com.example.restfulapiforclearsolutions.models.User;
import com.example.restfulapiforclearsolutions.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    /*** Имитация хранилища данных  **/
    private final Map<Long, User> userDatabase = new HashMap<>();
    private long userIdCounter = 1;

    @Value("${user.minAge}")
    int userMinAge;

    @Override
    public List<User> getUsersByBirthDateRange(LocalDate firstDate, LocalDate lastDate) {
       return userDatabase.values().stream()
               .filter(user -> user.getBirthDate().isAfter(firstDate) && user.getBirthDate().isBefore(lastDate))
               .collect(Collectors.toList());

    }

    @Override
    public List<User> getAllUsers() {
        return userDatabase.values().stream().toList();

    }

    @Override
    public User getUserById(long userId) {
        return userDatabase.get(userId);
    }

    @Override
    public User createUser(UserDTO userDTO) {

        LocalDate currentDate = LocalDate.now();
        int age = Period.between(userDTO.getBirthDate(), currentDate).getYears();

        if (age >= userMinAge) {
            User newUser = new User(userIdCounter++, userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getBirthDate(), userDTO.getAddress(), userDTO.getPhone());
            userDatabase.put(newUser.getId(), newUser);
            return newUser;
        } else {
            throw new ValidationException("The person's age must be greater than or equal to 18 years");
        }

    }

    @Override
    public boolean updateUser(long userId, UserDTO userDTO) {
        if (userDatabase.containsKey(userId)) {
            User userToUpdate = userDatabase.get(userId);
            userToUpdate.setFirstName(userDTO.getFirstName());
            userToUpdate.setLastName(userDTO.getLastName());
            userToUpdate.setEmail(userDTO.getEmail());
            userToUpdate.setBirthDate(userDTO.getBirthDate());
            userToUpdate.setAddress(userDTO.getAddress());
            userToUpdate.setPhone(userDTO.getPhone());
            userDatabase.put(userId, userToUpdate);
            return true;

        }else return false;
    }

    @Override
    public boolean updateUserPartly(long userId, UserDTO userDTO) {
        if (userDatabase.containsKey(userId)) {
            User userToUpdate = userDatabase.get(userId);
            if (userDTO.getFirstName() != null) {
                userToUpdate.setFirstName(userDTO.getFirstName());
            }
            if (userDTO.getLastName() != null) {
                userToUpdate.setLastName(userDTO.getLastName());
            }
            if (userDTO.getEmail() != null) {
                userToUpdate.setEmail(userDTO.getEmail());
            }
            if (userDTO.getBirthDate() != null) {
                userToUpdate.setBirthDate(userDTO.getBirthDate());
            }
            if (userDTO.getAddress() != null) {
                userToUpdate.setAddress(userDTO.getAddress());
            }
            if (userDTO.getPhone() != 0) {
                userToUpdate.setPhone(userDTO.getPhone());
            }
            userDatabase.put(userId, userToUpdate);
            return true;

        }else return false;
    }

    @Override
    public boolean deleteUser(long userId) {
        return userDatabase.remove(userId) != null;
    }
}
